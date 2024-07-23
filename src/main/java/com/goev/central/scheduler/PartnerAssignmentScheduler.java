package com.goev.central.scheduler;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.enums.booking.BookingStatus;
import com.goev.central.enums.booking.BookingSubStatus;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.enums.partner.PartnerSubStatus;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.lib.dto.LatLongDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class PartnerAssignmentScheduler {

    private final BookingRepository bookingRepository;

    private final PartnerRepository partnerRepository;

    @Scheduled(fixedRate = 60 * 1000)
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        List<BookingDao> allBooking = bookingRepository.findAllActive(BookingStatus.IN_PROGRESS.name(), BookingSubStatus.UNASSIGNED.name());

        for (BookingDao bookingDao : allBooking) {
            List<PartnerDao> partners = partnerRepository.findAllByStatus(Collections.singletonList("ONLINE"));

            if (CollectionUtils.isEmpty(partners))
                return;
            PartnerDao partnerDao = partners.get(0);
            if (bookingDao.getPartnerId() != null) {
                partnerDao = partnerRepository.findById(bookingDao.getPartnerId());

                if (partnerDao == null)
                    continue;
            }


            if (partnerDao != null && PartnerStatus.ONLINE.name().equals(partnerDao.getStatus()) && PartnerSubStatus.NO_BOOKING.name().equals(partnerDao.getSubStatus())) {
                bookingDao.setStatus(BookingStatus.IN_PROGRESS.name());
                bookingDao.setSubStatus(BookingSubStatus.ASSIGNED.name());
                bookingDao.setPartnerId(partnerDao.getId());
                bookingDao.setPartnerDetails(partnerDao.getViewInfo());
                bookingDao.setVehicleDetails(partnerDao.getVehicleDetails());


                BookingViewDto viewDto = BookingViewDto.builder()
                        .uuid(bookingDao.getUuid())
                        .partnerDetails(ApplicationConstants.GSON.fromJson(bookingDao.getPartnerDetails(), PartnerViewDto.class))
                        .vehicleDetails(ApplicationConstants.GSON.fromJson(bookingDao.getVehicleDetails(), VehicleViewDto.class))
                        .status(bookingDao.getStatus())
                        .subStatus(bookingDao.getSubStatus())
                        .customerDetails(ApplicationConstants.GSON.fromJson(bookingDao.getCustomerDetails(), CustomerViewDto.class))
                        .startLocationDetails(ApplicationConstants.GSON.fromJson(bookingDao.getStartLocationDetails(), LatLongDto.class))
                        .endLocationDetails(ApplicationConstants.GSON.fromJson(bookingDao.getEndLocationDetails(), LatLongDto.class))
                        .plannedStartTime(bookingDao.getPlannedStartTime())
                        .displayCode(bookingDao.getDisplayCode())
                        .build();
                partnerDao.setStatus(PartnerStatus.ON_BOOKING.name());
                partnerDao.setBookingId(bookingDao.getId());
                partnerDao.setSubStatus(PartnerSubStatus.ASSIGNED.name());
                partnerDao.setBookingDetails(ApplicationConstants.GSON.toJson(viewDto));
                partnerRepository.update(partnerDao);
                bookingDao.setViewInfo(ApplicationConstants.GSON.toJson(viewDto));
                bookingRepository.update(bookingDao);
            }

        }

    }
}
