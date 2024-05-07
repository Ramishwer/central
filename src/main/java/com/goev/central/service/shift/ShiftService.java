package com.goev.central.service.shift;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.shift.ShiftDto;

public interface ShiftService {
    PaginatedResponseDto<ShiftDto> getShifts();

    ShiftDto createShift(ShiftDto shiftDto);

    ShiftDto updateShift(String shiftUUID, ShiftDto shiftDto);

    ShiftDto getShiftDetails(String shiftUUID);

    Boolean deleteShift(String shiftUUID);
}
