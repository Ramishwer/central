package com.goev.central.controller.partner.duty;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.service.partner.duty.PartnerShiftService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerShiftController {
    private final PartnerShiftService partnerShiftService;

    @GetMapping("/partners/{partner-uuid}/shifts")
    public ResponseDto<PaginatedResponseDto<PartnerShiftDto>> getShifts(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerShiftService.getShifts(partnerUUID));
    }

    @PostMapping("/partners/{partner-uuid}/shifts")
    public ResponseDto<PartnerShiftDto> createShift(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody PartnerShiftDto partnerShiftDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerShiftService.createShift(partnerUUID, partnerShiftDto));
    }

    @PutMapping("/partners/{partner-uuid}/shifts/{shift-uuid}")
    public ResponseDto<PartnerShiftDto> updateShift(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "shift-uuid") String shiftUUID, @RequestBody PartnerShiftDto partnerShiftDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerShiftService.updateShift(partnerUUID, shiftUUID, partnerShiftDto));
    }

    @GetMapping("/partners/{partner-uuid}/shifts/{shift-uuid}")
    public ResponseDto<PartnerShiftDto> getShiftDetails(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "shift-uuid") String shiftUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerShiftService.getShiftDetails(partnerUUID, shiftUUID));
    }

    @DeleteMapping("/partners/{partner-uuid}/shifts/{shift-uuid}")
    public ResponseDto<Boolean> deleteShift(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "shift-uuid") String shiftUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerShiftService.deleteShift(partnerUUID, shiftUUID));
    }
}
