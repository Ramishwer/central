package com.goev.central.service.booking.impl;

import com.goev.central.dao.booking.BookingDao;
import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.booking.BookingRepository;
import com.goev.central.service.booking.BookingService;
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
    public PaginatedResponseDto<BookingDto> getBookings() {
        PaginatedResponseDto<BookingDto> result = PaginatedResponseDto.<BookingDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<BookingDao> bookingDaos = bookingRepository.findAll();
        if (CollectionUtils.isEmpty(bookingDaos))
            return result;

        for (BookingDao bookingDao : bookingDaos) {
            result.getElements().add(BookingDto.builder()
                    .uuid(bookingDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public BookingDto createBooking(BookingDto bookingDto) {

        BookingDao bookingDao = new BookingDao();
        bookingDao = bookingRepository.save(bookingDao);
        if (bookingDao == null)
            throw new ResponseException("Error in saving booking booking");
        return BookingDto.builder()
                .uuid(bookingDao.getUuid()).build();
    }

    @Override
    public BookingDto updateBooking(String bookingUUID, BookingDto bookingDto) {
        BookingDao bookingDao = bookingRepository.findByUUID(bookingUUID);
        if (bookingDao == null)
            throw new ResponseException("No booking  found for Id :" + bookingUUID);
        BookingDao newBookingDao = new BookingDao();
       

        newBookingDao.setId(bookingDao.getId());
        newBookingDao.setUuid(bookingDao.getUuid());
        bookingDao = bookingRepository.update(newBookingDao);
        if (bookingDao == null)
            throw new ResponseException("Error in updating details booking ");
        return BookingDto.builder()
                .uuid(bookingDao.getUuid()).build();
    }

    @Override
    public BookingDto getBookingDetails(String bookingUUID) {
        BookingDao bookingDao = bookingRepository.findByUUID(bookingUUID);
        if (bookingDao == null)
            throw new ResponseException("No booking  found for Id :" + bookingUUID);
        return BookingDto.builder()
                .uuid(bookingDao.getUuid()).build();
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
