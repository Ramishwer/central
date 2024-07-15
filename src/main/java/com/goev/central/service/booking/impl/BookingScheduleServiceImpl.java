package com.goev.central.service.booking.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.booking.BookingScheduleDao;
import com.goev.central.dto.booking.BookingScheduleDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.booking.BookingScheduleRepository;
import com.goev.central.service.booking.BookingScheduleService;
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
public class BookingScheduleServiceImpl implements BookingScheduleService {

    private final BookingScheduleRepository bookingScheduleRepository;

    @Override
    public PaginatedResponseDto<BookingScheduleDto> getBookingSchedules(String status, String subStatus) {
        PaginatedResponseDto<BookingScheduleDto> result = PaginatedResponseDto.<BookingScheduleDto>builder().elements(new ArrayList<>()).build();
        List<BookingScheduleDao> bookingScheduleDaos = bookingScheduleRepository.findAllActive(status, subStatus);
        if (CollectionUtils.isEmpty(bookingScheduleDaos))
            return result;

        for (BookingScheduleDao bookingScheduleDao : bookingScheduleDaos) {
            result.getElements().add(BookingScheduleDto.builder()
                    .uuid(bookingScheduleDao.getUuid())
                    .status(bookingScheduleDao.getStatus())
                    .subStatus(bookingScheduleDao.getSubStatus())
                    .startLocationDetails(ApplicationConstants.GSON.fromJson(bookingScheduleDao.getStartLocationDetails(), LatLongDto.class))
                    .endLocationDetails(ApplicationConstants.GSON.fromJson(bookingScheduleDao.getEndLocationDetails(), LatLongDto.class))
                    .build());
        }
        return result;
    }

    @Override
    public BookingScheduleDto createBookingSchedule(BookingScheduleDto bookingScheduleDto) {

        BookingScheduleDao bookingScheduleDao = getBookingScheduleDao(bookingScheduleDto);
        bookingScheduleDao = bookingScheduleRepository.save(bookingScheduleDao);
        if (bookingScheduleDao == null)
            throw new ResponseException("Error in saving bookingSchedule");
        return getBookingScheduleDto(bookingScheduleDao);
    }

    private BookingScheduleDao getBookingScheduleDao(BookingScheduleDto bookingScheduleDto) {
        return BookingScheduleDao.fromDto(bookingScheduleDto);
    }

    private BookingScheduleDto getBookingScheduleDto(BookingScheduleDao bookingScheduleDao) {
        return BookingScheduleDto.fromDao(bookingScheduleDao);
    }

    @Override
    public BookingScheduleDto updateBookingSchedule(String bookingScheduleUUID, BookingScheduleDto bookingScheduleDto) {
        BookingScheduleDao bookingScheduleDao = bookingScheduleRepository.findByUUID(bookingScheduleUUID);
        if (bookingScheduleDao == null)
            throw new ResponseException("No bookingSchedule  found for Id :" + bookingScheduleUUID);
        BookingScheduleDao newBookingScheduleDao = getBookingScheduleDao(bookingScheduleDto);

        newBookingScheduleDao.setId(bookingScheduleDao.getId());
        newBookingScheduleDao.setUuid(bookingScheduleDao.getUuid());
        bookingScheduleDao = bookingScheduleRepository.update(newBookingScheduleDao);
        if (bookingScheduleDao == null)
            throw new ResponseException("Error in updating details bookingSchedule ");
        return getBookingScheduleDto(bookingScheduleDao);
    }

    @Override
    public BookingScheduleDto getBookingScheduleDetails(String bookingScheduleUUID) {
        BookingScheduleDao bookingScheduleDao = bookingScheduleRepository.findByUUID(bookingScheduleUUID);
        if (bookingScheduleDao == null)
            throw new ResponseException("No bookingSchedule  found for Id :" + bookingScheduleUUID);
        return getBookingScheduleDto(bookingScheduleDao);
    }

    @Override
    public Boolean deleteBookingSchedule(String bookingScheduleUUID) {
        BookingScheduleDao bookingScheduleDao = bookingScheduleRepository.findByUUID(bookingScheduleUUID);
        if (bookingScheduleDao == null)
            throw new ResponseException("No bookingSchedule  found for Id :" + bookingScheduleUUID);
        bookingScheduleRepository.delete(bookingScheduleDao.getId());
        return true;
    }
}
