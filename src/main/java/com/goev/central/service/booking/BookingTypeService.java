package com.goev.central.service.booking;

import com.goev.central.dto.booking.BookingTypeDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface BookingTypeService {
    PaginatedResponseDto<BookingTypeDto> getBookingTypes();

    BookingTypeDto createBookingType(BookingTypeDto bookingTypeDto);

    BookingTypeDto updateBookingType(String bookingTypeUUID, BookingTypeDto bookingTypeDto);

    BookingTypeDto getBookingTypeDetails(String bookingTypeUUID);

    Boolean deleteBookingType(String bookingTypeUUID);
}
