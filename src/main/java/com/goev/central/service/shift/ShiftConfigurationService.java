package com.goev.central.service.shift;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.shift.ShiftConfigurationDto;

public interface ShiftConfigurationService {
    PaginatedResponseDto<ShiftConfigurationDto> getShiftConfigurations(String modelUUID);

    ShiftConfigurationDto createShiftConfiguration(String modelUUID, ShiftConfigurationDto shiftConfigurationDto);

    ShiftConfigurationDto updateShiftConfiguration(String modelUUID, String shiftConfigurationUUID, ShiftConfigurationDto shiftConfigurationDto);

    ShiftConfigurationDto getShiftConfigurationDetails(String modelUUID, String shiftConfigurationUUID);

    Boolean deleteShiftConfiguration(String modelUUID, String shiftConfigurationUUID);
}
