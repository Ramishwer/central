package com.goev.central.service.booking.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dto.booking.BookingActionDto;
import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.enums.booking.BookingStatus;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.enums.partner.PartnerSubStatus;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.booking.BookingService;
import com.goev.lib.dto.LatLongDto;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final PartnerRepository partnerRepository;

    @Override
    public PaginatedResponseDto<BookingViewDto> getBookings(List<String> status, String subStatus) {
        PaginatedResponseDto<BookingViewDto> result = PaginatedResponseDto.<BookingViewDto>builder().elements(new ArrayList<>()).build();
        List<BookingDao> bookingDaos = bookingRepository.findAllActive(status, subStatus);
        if (CollectionUtils.isEmpty(bookingDaos))
            return result;

        for (BookingDao bookingDao : bookingDaos) {
            result.getElements().add(BookingViewDto.builder()
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
                    .build());
        }
        return result;
    }

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {

        BookingDao bookingDao = getBookingDao(bookingDto);
        bookingDao = bookingRepository.save(bookingDao);
        if (bookingDao == null)
            throw new ResponseException("Error in saving booking");
        return getBookingDto(bookingDao);
    }

    private BookingDao getBookingDao(BookingDto bookingDto) {
        return BookingDao.fromDto(bookingDto);
    }

    private BookingDto getBookingDto(BookingDao bookingDao) {
        return BookingDto.fromDao(bookingDao);
    }

    @Override
    public BookingDto updateBooking(String bookingUUID, BookingActionDto bookingActionDto) {
        BookingDao bookingDao = bookingRepository.findByUUID(bookingUUID);
        if (bookingDao == null)
            throw new ResponseException("No booking  found for Id :" + bookingUUID);
        switch (bookingActionDto.getAction()){
//            case RESCHEDULE -> {
//                bookingDao = rescheduleBooking();
//            }
            case CANCEL -> {
                bookingDao =  cancelBooking(bookingDao,bookingActionDto);
            }
//            case ADD_STOP -> {
//            }
//            case DELETE_STOP -> {
//            }
        }

        return getBookingDto(bookingDao);
    }

    private BookingDao cancelBooking(BookingDao bookingDao, BookingActionDto bookingActionDto) {

        if(BookingStatus.COMPLETED.name().equals(bookingDao.getStatus()))
            throw new ResponseException("Booking already completed");

        bookingDao.setStatus(BookingStatus.CANCELLED.name());
        bookingDao.setSubStatus(BookingStatus.CANCELLED.name());

        bookingDao = bookingRepository.update(bookingDao);

        if(bookingDao.getPartnerId()!=null){
            PartnerDao partnerDao = partnerRepository.findById(bookingDao.getPartnerId());
            if(partnerDao.getBookingId()!=null && partnerDao.getBookingId().equals(bookingDao.getId()) && PartnerStatus.ON_BOOKING.name().equals(partnerDao.getStatus())){
                partnerDao.setBookingDetails(null);
                partnerDao.setBookingId(null);
                partnerDao.setStatus(PartnerStatus.ONLINE.name());
                partnerDao.setSubStatus(PartnerSubStatus.NO_BOOKING.name());
                partnerRepository.save(partnerDao);
            }
        }
        return bookingDao;
    }

    @Override
    public BookingDto getBookingDetails(String bookingUUID) {
        BookingDao bookingDao = bookingRepository.findByUUID(bookingUUID);
        if (bookingDao == null)
            throw new ResponseException("No booking  found for Id :" + bookingUUID);
        return getBookingDto(bookingDao);
    }

    @Override
    public Boolean deleteBooking(String bookingUUID) {
        BookingDao bookingDao = bookingRepository.findByUUID(bookingUUID);
        if (bookingDao == null)
            throw new ResponseException("No booking  found for Id :" + bookingUUID);
        bookingRepository.delete(bookingDao.getId());
        return true;
    }
}
