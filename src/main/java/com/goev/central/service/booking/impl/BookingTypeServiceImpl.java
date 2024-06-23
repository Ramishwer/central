package com.goev.central.service.booking.impl;

import com.goev.central.dao.booking.BookingTypeDao;
import com.goev.central.dto.booking.BookingTypeDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.repository.booking.BookingTypeRepository;
import com.goev.central.service.booking.BookingTypeService;
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
public class BookingTypeServiceImpl implements BookingTypeService {

    private final BookingTypeRepository bookingTypeRepository;

    @Override
    public PaginatedResponseDto<BookingTypeDto> getBookingTypes() {
        PaginatedResponseDto<BookingTypeDto> result = PaginatedResponseDto.<BookingTypeDto>builder().elements(new ArrayList<>()).build();
        List<BookingTypeDao> bookingTypeDaos = bookingTypeRepository.findAllActive();
        if (CollectionUtils.isEmpty(bookingTypeDaos))
            return result;

        for (BookingTypeDao bookingTypeDao : bookingTypeDaos) {
            result.getElements().add(BookingTypeDto.builder()
                    .uuid(bookingTypeDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public BookingTypeDto createBookingType(BookingTypeDto bookingTypeDto) {

        BookingTypeDao bookingTypeDao = new BookingTypeDao();
        bookingTypeDao = bookingTypeRepository.save(bookingTypeDao);
        if (bookingTypeDao == null)
            throw new ResponseException("Error in saving bookingType bookingType");
        return BookingTypeDto.builder()
                .uuid(bookingTypeDao.getUuid()).build();
    }

    @Override
    public BookingTypeDto updateBookingType(String bookingTypeUUID, BookingTypeDto bookingTypeDto) {
        BookingTypeDao bookingTypeDao = bookingTypeRepository.findByUUID(bookingTypeUUID);
        if (bookingTypeDao == null)
            throw new ResponseException("No bookingType  found for Id :" + bookingTypeUUID);
        BookingTypeDao newBookingTypeDao = new BookingTypeDao();


        newBookingTypeDao.setId(bookingTypeDao.getId());
        newBookingTypeDao.setUuid(bookingTypeDao.getUuid());
        bookingTypeDao = bookingTypeRepository.update(newBookingTypeDao);
        if (bookingTypeDao == null)
            throw new ResponseException("Error in updating details bookingType ");
        return BookingTypeDto.builder()
                .uuid(bookingTypeDao.getUuid()).build();
    }

    @Override
    public BookingTypeDto getBookingTypeDetails(String bookingTypeUUID) {
        BookingTypeDao bookingTypeDao = bookingTypeRepository.findByUUID(bookingTypeUUID);
        if (bookingTypeDao == null)
            throw new ResponseException("No bookingType  found for Id :" + bookingTypeUUID);
        return BookingTypeDto.builder()
                .uuid(bookingTypeDao.getUuid()).build();
    }

    @Override
    public Boolean deleteBookingType(String bookingTypeUUID) {
        BookingTypeDao bookingTypeDao = bookingTypeRepository.findByUUID(bookingTypeUUID);
        if (bookingTypeDao == null)
            throw new ResponseException("No bookingType  found for Id :" + bookingTypeUUID);
        bookingTypeRepository.delete(bookingTypeDao.getId());
        return true;
    }
}
