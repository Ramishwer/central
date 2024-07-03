package com.goev.central.controller.booking;

import com.goev.central.dto.booking.BookingRequestDto;
import com.goev.central.dto.booking.BookingTypeDto;
import com.goev.central.service.booking.BookingRequestService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/booking-management")
@AllArgsConstructor
public class BookingRequestController {

    private final BookingRequestService bookingRequestService;


    @PostMapping("/booking-requests")
    public ResponseDto<BookingRequestDto> createBookingRequest(@RequestBody BookingRequestDto bookingRequestDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingRequestService.createBookingRequest(bookingRequestDto));
    }

    @PutMapping("/booking-requests/{booking-request-uuid}")
    public ResponseDto<BookingRequestDto> updateBookingRequest(@PathVariable(value = "booking-request-uuid") String bookingRequestUUID, @RequestBody BookingRequestDto bookingRequestDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingRequestService.updateBookingRequest(bookingRequestUUID, bookingRequestDto));
    }
}
