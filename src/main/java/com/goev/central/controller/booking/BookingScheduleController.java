package com.goev.central.controller.booking;

import com.goev.central.dto.booking.BookingDto;
import com.goev.central.dto.booking.BookingScheduleDto;
import com.goev.central.dto.booking.BookingViewDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.service.booking.BookingScheduleService;
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
public class BookingScheduleController {

    private final BookingScheduleService bookingScheduleService;

    @GetMapping("/booking-schedules")
    public ResponseDto<PaginatedResponseDto<BookingScheduleDto>> getBookingSchedules(@RequestParam(value = "count", required = false) Integer count,
                                                                                     @RequestParam(value = "start", required = false) Integer start,
                                                                                     @RequestParam(value = "from", required = false) Long from,
                                                                                     @RequestParam(value = "to", required = false) Long to,
                                                                                     @RequestParam(value = "lastUUID", required = false) String lastElementUUID,
                                                                                     @RequestParam("status")String status,
                                                                                     @RequestParam(value = "subStatus", required = false)String subStatus) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingScheduleService.getBookingSchedules(status,subStatus));
    }


    @GetMapping("/booking-schedules/{booking-uuid}")
    public ResponseDto<BookingScheduleDto> getBookingScheduleDetails(@PathVariable(value = "booking-uuid") String bookingUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingScheduleService.getBookingScheduleDetails(bookingUUID));
    }

    @PutMapping("/booking-schedules/{booking-uuid}")
    public ResponseDto<BookingScheduleDto> updateBooking(@PathVariable(value = "booking-uuid") String bookingUUID, @RequestBody BookingScheduleDto bookingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingScheduleService.updateBookingSchedule(bookingUUID, bookingDto));
    }
}
