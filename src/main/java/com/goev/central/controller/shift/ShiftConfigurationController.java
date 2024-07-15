package com.goev.central.controller.shift;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.shift.ShiftConfigurationDto;
import com.goev.central.service.shift.ShiftConfigurationService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/shift-management")
@AllArgsConstructor
public class ShiftConfigurationController {

    private final ShiftConfigurationService shiftConfigurationService;

    @GetMapping("/shifts/{shift-uuid}/configurations")
    public ResponseDto<Map<String,ShiftConfigurationDto>> getShiftConfigurations(@PathVariable(value = "shift-uuid") String shiftUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftConfigurationService.getShiftConfigurations(shiftUUID));
    }

    @PostMapping("/shifts/{shift-uuid}/configurations")
    public ResponseDto<Map<String,ShiftConfigurationDto>> createShiftConfiguration(@PathVariable(value = "shift-uuid") String shiftUUID, @RequestBody Map<String,ShiftConfigurationDto> shiftConfiguration) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, shiftConfigurationService.createShiftConfiguration(shiftUUID, shiftConfiguration));
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
