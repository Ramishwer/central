package com.goev.central.service.booking;

import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface BookingService {
    PaginatedResponseDto<BookingDto> getBookings();

    BookingDto createBooking(BookingDto bookingDto);

    BookingDto updateBooking(String bookingUUID, BookingDto bookingDto);

    BookingDto getBookingDetails(String bookingUUID);

    Boolean deleteBooking(String bookingUUID);
}
