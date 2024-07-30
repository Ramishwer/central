package com.goev.central.service.booking;

import com.goev.central.dto.booking.BookingRequestDto;
import com.goev.central.dto.booking.BookingScheduleDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface BookingScheduleService {
    PaginatedResponseDto<BookingScheduleDto> getBookingSchedules(String status, String subStatus);

    BookingScheduleDto createBookingSchedule(BookingRequestDto bookingRequestDto);

    BookingScheduleDto updateBookingSchedule(String bookingScheduleUUID, BookingScheduleDto bookingScheduleDto);

    BookingScheduleDto getBookingScheduleDetails(String bookingScheduleUUID);

    Boolean deleteBookingSchedule(String bookingScheduleUUID);
}
