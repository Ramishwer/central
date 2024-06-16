package com.goev.central.controller.booking;

import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.service.booking.BookingService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/booking-management")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/bookings")
    public ResponseDto<PaginatedResponseDto<BookingDto>> getBookings(@RequestParam(value = "count", required = false) Integer count,
                                                                     @RequestParam(value = "start", required = false) Integer start,
                                                                     @RequestParam(value = "from", required = false) Long from,
                                                                     @RequestParam(value = "to", required = false) Long to,
                                                                     @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.getBookings());
    }


    @GetMapping("/bookings/{booking-uuid}")
    public ResponseDto<BookingDto> getBookingDetails(@PathVariable(value = "booking-uuid") String bookingUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.getBookingDetails(bookingUUID));
    }


    @PostMapping("/bookings")
    public ResponseDto<BookingDto> createBooking(@RequestBody BookingDto bookingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.createBooking(bookingDto));
    }

    @PutMapping("/bookings/{booking-uuid}")
    public ResponseDto<BookingDto> updateBooking(@PathVariable(value = "booking-uuid") String bookingUUID, @RequestBody BookingDto bookingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.updateBooking(bookingUUID, bookingDto));
    }
}
