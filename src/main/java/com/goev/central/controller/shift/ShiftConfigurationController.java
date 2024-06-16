package com.goev.central.controller.shift;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.shift.ShiftConfigurationDto;
import com.goev.central.service.shift.ShiftConfigurationService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/shift-management")
@AllArgsConstructor
public class ShiftConfigurationController {

    private final ShiftConfigurationService shiftConfigurationService;

    @GetMapping("/shifts/{shift-uuid}/configurations")
    public ResponseDto<PaginatedResponseDto<ShiftConfigurationDto>> getShiftConfigurations(@PathVariable(value = "shift-uuid") String shiftUUID, @RequestParam("count") Integer count,
                                                                                           @RequestParam("start") Integer start,
                                                                                           @RequestParam("from") Long from,
                                                                                           @RequestParam("to") Long to,
                                                                                           @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftConfigurationService.getShiftConfigurations(shiftUUID));
    }


    @GetMapping("/shifts/{shift-uuid}/configurations/{configuration-uuid}")
    public ResponseDto<ShiftConfigurationDto> getShiftConfigurationDetails(@PathVariable(value = "shift-uuid") String shiftUUID, @PathVariable(value = "configuration-uuid") String configurationUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftConfigurationService.getShiftConfigurationDetails(shiftUUID, configurationUUID));
    }


    @PostMapping("/shifts/{shift-uuid}/configurations")
    public ResponseDto<ShiftConfigurationDto> createShiftConfiguration(@PathVariable(value = "shift-uuid") String shiftUUID, @RequestBody ShiftConfigurationDto shiftConfigurationDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftConfigurationService.createShiftConfiguration(shiftUUID, shiftConfigurationDto));
    }

    @PutMapping("/shifts/{shift-uuid}/configurations/{configuration-uuid}")
    public ResponseDto<ShiftConfigurationDto> updateShiftConfiguration(@PathVariable(value = "shift-uuid") String shiftUUID, @PathVariable(value = "configuration-uuid") String configurationUUID, @RequestBody ShiftConfigurationDto shiftConfigurationDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftConfigurationService.updateShiftConfiguration(shiftUUID, configurationUUID, shiftConfigurationDto));
    }

    @DeleteMapping("/shifts/{shift-uuid}/configurations/{configuration-uuid}")
    public ResponseDto<Boolean> deleteShiftConfiguration(@PathVariable(value = "shift-uuid") String shiftUUID, @PathVariable(value = "configuration-uuid") String configurationUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftConfigurationService.deleteShiftConfiguration(shiftUUID, configurationUUID));
    }
}
