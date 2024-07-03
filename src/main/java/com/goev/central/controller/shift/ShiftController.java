package com.goev.central.controller.shift;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.shift.ShiftDto;
import com.goev.central.service.shift.ShiftService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/shift-management")
@AllArgsConstructor
public class ShiftController {

    private final ShiftService shiftService;

    @GetMapping("/shifts")
    public ResponseDto<PaginatedResponseDto<ShiftDto>> getShifts(@RequestParam(value = "count",required = false) Integer count,
                                                                 @RequestParam(value = "start",required = false) Integer start,
                                                                 @RequestParam(value = "from",required = false) Long from,
                                                                 @RequestParam(value = "to",required = false) Long to,
                                                                 @RequestParam(value = "lastUUID",required = false) String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftService.getShifts());
    }


    @GetMapping("/shifts/{shift-uuid}")
    public ResponseDto<ShiftDto> getShiftDetails(@PathVariable(value = "shift-uuid") String shiftUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftService.getShiftDetails(shiftUUID));
    }


    @PostMapping("/shifts")
    public ResponseDto<ShiftDto> createShift(@RequestBody ShiftDto shiftDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftService.createShift(shiftDto));
    }

    @PutMapping("/shifts/{shift-uuid}")
    public ResponseDto<ShiftDto> updateShift(@PathVariable(value = "shift-uuid") String shiftUUID, @RequestBody ShiftDto shiftDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftService.updateShift(shiftUUID, shiftDto));
    }

    @DeleteMapping("/shifts/{shift-uuid}")
    public ResponseDto<Boolean> deleteShift(@PathVariable(value = "shift-uuid") String shiftUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftService.deleteShift(shiftUUID));
    }
}
