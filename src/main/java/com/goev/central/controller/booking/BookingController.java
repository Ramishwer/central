package com.goev.central.controller.booking;

import com.goev.central.dto.booking.BookingActionDto;
import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.service.booking.BookingService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/booking-management")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/bookings")
    public ResponseDto<PaginatedResponseDto<BookingViewDto>> getBookings(@RequestParam(value = "count", required = false) Integer count,
                                                                         @RequestParam(value = "start", required = false) Integer start,
                                                                         @RequestParam(value = "from", required = false) Long from,
                                                                         @RequestParam(value = "to", required = false) Long to,
                                                                         @RequestParam(value = "lastUUID", required = false) String lastElementUUID,
                                                                         @RequestParam("status") List<String> status,
                                                                         @RequestParam(value = "subStatus", required = false)String subStatus) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.getBookings(status,subStatus));
    }


    @GetMapping("/bookings/{booking-uuid}")
    public ResponseDto<BookingDto> getBookingDetails(@PathVariable(value = "booking-uuid") String bookingUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.getBookingDetails(bookingUUID));
    }

    @PutMapping("/bookings/{booking-uuid}")
    public ResponseDto<BookingDto> updateBooking(@PathVariable(value = "booking-uuid") String bookingUUID, @RequestBody BookingActionDto bookingActionDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.updateBooking(bookingUUID, bookingActionDto));
    }
}
