package com.goev.central.controller.booking;

import com.goev.central.dto.booking.BookingTypeDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.service.booking.BookingTypeService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/booking-management")
@AllArgsConstructor
public class BookingTypeController {

    private final BookingTypeService bookingService;

    @GetMapping("/booking-types")
    public ResponseDto<PaginatedResponseDto<BookingTypeDto>> getBookingTypes(@RequestParam(value = "count", required = false) Integer count,
                                                                             @RequestParam(value = "start", required = false) Integer start,
                                                                             @RequestParam(value = "from", required = false) Long from,
                                                                             @RequestParam(value = "to", required = false) Long to,
                                                                             @RequestParam(value = "lastUUID", required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.getBookingTypes());
    }


    @GetMapping("/booking-types/{booking-type-uuid}")
    public ResponseDto<BookingTypeDto> getBookingTypeDetails(@PathVariable(value = "booking-type-uuid") String bookingUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.getBookingTypeDetails(bookingUUID));
    }


    @PostMapping("/booking-types")
    public ResponseDto<BookingTypeDto> createBookingType(@RequestBody BookingTypeDto bookingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.createBookingType(bookingDto));
    }

    @PutMapping("/booking-types/{booking-type-uuid}")
    public ResponseDto<BookingTypeDto> updateBookingType(@PathVariable(value = "booking-type-uuid") String bookingUUID, @RequestBody BookingTypeDto bookingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.updateBookingType(bookingUUID, bookingDto));
    }

    @DeleteMapping("/booking-types/{booking-type-uuid}")
    public ResponseDto<Boolean> deleteBookingType(@PathVariable(value = "booking-type-uuid") String bookingUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, bookingService.deleteBookingType(bookingUUID));
    }
}
