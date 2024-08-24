package com.goev.central.service.booking;

import com.goev.central.dto.booking.BookingActionDto;
import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.booking.BookingRequestDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import org.joda.time.DateTime;

import java.util.List;

public interface BookingService {
    PaginatedResponseDto<BookingViewDto> getBookings(List<String> status, String subStatus, DateTime from, DateTime to);

    BookingDto createBooking(BookingRequestDto bookingRequestDto);

    BookingDto updateBooking(String bookingUUID, BookingActionDto bookingActionDto);

    BookingDto getBookingDetails(String bookingUUID);

    Boolean deleteBooking(String bookingUUID);
}
