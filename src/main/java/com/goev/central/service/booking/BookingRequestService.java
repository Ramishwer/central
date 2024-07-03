package com.goev.central.service.booking;

import com.goev.central.dto.booking.BookingRequestDto;

public interface BookingRequestService {

    BookingRequestDto createBookingRequest(BookingRequestDto bookingRequest);

    BookingRequestDto updateBookingRequest(String bookingRequestUUID, BookingRequestDto bookingRequest);

}
