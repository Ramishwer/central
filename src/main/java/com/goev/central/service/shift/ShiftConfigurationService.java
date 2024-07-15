package com.goev.central.service.shift;

import com.goev.central.dto.shift.ShiftConfigurationDto;

import java.util.Map;

public interface ShiftConfigurationService {
    Map<String,ShiftConfigurationDto> getShiftConfigurations(String shiftUUID);

    Map<String,ShiftConfigurationDto> createShiftConfiguration(String shiftUUID,  Map<String,ShiftConfigurationDto> shiftConfiguration);

    ShiftConfigurationDto updateShiftConfiguration(String shiftUUID, String shiftConfigurationUUID, ShiftConfigurationDto shiftConfigurationDto);

    Boolean deleteShiftConfiguration(String shiftUUID, String shiftConfigurationUUID);
}
