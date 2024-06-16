package com.goev.central.controller.partner.duty;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerLeaveDto;
import com.goev.central.service.partner.duty.PartnerLeaveService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerLeaveController {
    private final PartnerLeaveService partnerLeaveService;

    @GetMapping("/partners/{partner-uuid}/leaves")
    public ResponseDto<PaginatedResponseDto<PartnerLeaveDto>> getLeaves(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerLeaveService.getLeaves(partnerUUID));
    }

    @PostMapping("/partners/{partner-uuid}/leaves")
    public ResponseDto<PartnerLeaveDto> createLeave(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody PartnerLeaveDto partnerLeaveDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerLeaveService.createLeave(partnerUUID, partnerLeaveDto));
    }

    @PutMapping("/partners/{partner-uuid}/leaves/{leave-uuid}")
    public ResponseDto<PartnerLeaveDto> updateLeave(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "leave-uuid") String leaveUUID, @RequestBody PartnerLeaveDto partnerLeaveDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerLeaveService.updateLeave(partnerUUID, leaveUUID, partnerLeaveDto));
    }

    @GetMapping("/partners/{partner-uuid}/leaves/{leave-uuid}")
    public ResponseDto<PartnerLeaveDto> getLeaveDetails(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "leave-uuid") String leaveUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerLeaveService.getLeaveDetails(partnerUUID, leaveUUID));
    }

    @DeleteMapping("/partners/{partner-uuid}/leaves/{leave-uuid}")
    public ResponseDto<Boolean> deleteLeave(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "leave-uuid") String leaveUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerLeaveService.deleteLeave(partnerUUID, leaveUUID));
    }
}
