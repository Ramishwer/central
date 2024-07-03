package com.goev.central.service.booking.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import com.goev.central.repository.booking.BookingRepository;
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

    @Override
    public PaginatedResponseDto<BookingViewDto> getBookings(String status, String subStatus) {
        PaginatedResponseDto<BookingViewDto> result = PaginatedResponseDto.<BookingViewDto>builder().elements(new ArrayList<>()).build();
        List<BookingDao> bookingDaos = bookingRepository.findAllActive(status, subStatus);
        if (CollectionUtils.isEmpty(bookingDaos))
            return result;

        for (BookingDao bookingDao : bookingDaos) {
            result.getElements().add(BookingViewDto.builder()
                    .uuid(bookingDao.getUuid())
                    .partnerDetails(ApplicationConstants.GSON.fromJson(bookingDao.getPartnerDetails(), PartnerViewDto.class))
                    .vehicleDetails(ApplicationConstants.GSON.fromJson(bookingDao.getVehicleDetails(), VehicleViewDto.class))
                    .status(bookingDao.getStatus())
                    .subStatus(bookingDao.getSubStatus())
                    .startLocationDetails(ApplicationConstants.GSON.fromJson(bookingDao.getStartLocationDetails(), LatLongDto.class))
                    .endLocationDetails(ApplicationConstants.GSON.fromJson(bookingDao.getEndLocationDetails(), LatLongDto.class))
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
    public BookingDto updateBooking(String bookingUUID, BookingDto bookingDto) {
        BookingDao bookingDao = bookingRepository.findByUUID(bookingUUID);
        if (bookingDao == null)
            throw new ResponseException("No booking  found for Id :" + bookingUUID);
        BookingDao newBookingDao = getBookingDao(bookingDto);

        newBookingDao.setId(bookingDao.getId());
        newBookingDao.setUuid(bookingDao.getUuid());
        bookingDao = bookingRepository.update(newBookingDao);
        if (bookingDao == null)
            throw new ResponseException("Error in updating details booking ");
        return getBookingDto(bookingDao);
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
