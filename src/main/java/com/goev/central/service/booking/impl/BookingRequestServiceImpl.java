package com.goev.central.service.booking.impl;

import com.goev.central.dto.booking.BookingRequestDto;
import com.goev.central.repository.booking.BookingRequestRepository;
import com.goev.central.service.booking.BookingRequestService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class BookingRequestServiceImpl implements BookingRequestService {
    private final BookingRequestRepository bookingRequestRepository;
    @Override
    public BookingRequestDto createBookingRequest(BookingRequestDto bookingRequest) {
        return null;
    }

    @Override
    public BookingRequestDto updateBookingRequest(String bookingRequestUUID, BookingRequestDto bookingRequest) {
        return null;
    }
}
