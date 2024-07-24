package com.goev.central.service.booking.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dto.booking.BookingPaymentDto;
import com.goev.central.dto.booking.BookingRequestDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.enums.booking.BookingStatus;
import com.goev.central.enums.booking.BookingSubStatus;
import com.goev.central.enums.booking.SchedulingTypes;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.central.repository.booking.BookingRequestRepository;
import com.goev.central.service.booking.BookingRequestService;
import com.goev.central.utilities.SecretGenerationUtils;
import com.goev.lib.dto.LatLongDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BookingRequestServiceImpl implements BookingRequestService {
    private final BookingRequestRepository bookingRequestRepository;
    private final BookingRepository bookingRepository;

    @Override
    public BookingRequestDto createBookingRequest(BookingRequestDto bookingRequest) {

        DateTime startTime = DateTime.now();

        if (bookingRequest.getScheduleDetails() != null && SchedulingTypes.SCHEDULED.equals(bookingRequest.getScheduleDetails().getType()))
            startTime = bookingRequest.getScheduleDetails().getStartTime() == null ? DateTime.now() : bookingRequest.getScheduleDetails().getStartTime();

        BookingDao bookingDao = new BookingDao();
        bookingDao.setCustomerDetails(ApplicationConstants.GSON.toJson(bookingRequest.getCustomerDetails()));
        bookingDao.setStartLocationDetails(ApplicationConstants.GSON.toJson(bookingRequest.getStartLocationDetails(), LatLongDto.class));
        bookingDao.setEndLocationDetails(ApplicationConstants.GSON.toJson(bookingRequest.getEndLocationDetails(), LatLongDto.class));
        bookingDao.setStatus(BookingStatus.CONFIRMED.name());
        bookingDao.setSubStatus(BookingSubStatus.UNASSIGNED.name());
        bookingDao.setPlannedStartTime(startTime);
        bookingDao.setDisplayCode("BRN-" + SecretGenerationUtils.getCode());
        bookingDao = bookingRepository.save(bookingDao);


        BookingViewDto viewDto = BookingViewDto.builder()
                .uuid(bookingDao.getUuid())
                .customerDetails(ApplicationConstants.GSON.fromJson(bookingDao.getCustomerDetails(), CustomerViewDto.class))
                .partnerDetails(ApplicationConstants.GSON.fromJson(bookingDao.getPartnerDetails(), PartnerViewDto.class))
                .vehicleDetails(ApplicationConstants.GSON.fromJson(bookingDao.getVehicleDetails(), VehicleViewDto.class))
                .status(bookingDao.getStatus())
                .subStatus(bookingDao.getSubStatus())
                .startLocationDetails(ApplicationConstants.GSON.fromJson(bookingDao.getStartLocationDetails(), LatLongDto.class))
                .endLocationDetails(ApplicationConstants.GSON.fromJson(bookingDao.getEndLocationDetails(), LatLongDto.class))
                .plannedStartTime(bookingDao.getPlannedStartTime())
                .displayCode(bookingDao.getDisplayCode())
                .payment(BookingPaymentDto.builder()
                        .paymentMode(bookingRequest.getPaymentDetails().getType()).build())
                .build();


        bookingDao.setViewInfo(ApplicationConstants.GSON.toJson(viewDto));
        bookingDao = bookingRepository.update(bookingDao);
        bookingRequest.setUuid(bookingDao.getUuid());
        return bookingRequest;
    }

    @Override
    public BookingRequestDto updateBookingRequest(String bookingRequestUUID, BookingRequestDto bookingRequest) {
        return null;
    }
}
