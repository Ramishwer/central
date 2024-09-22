package com.goev.central.controller.partner.duty;

import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.dto.partner.duty.PartnerShiftMappingDto;
import com.goev.central.service.partner.duty.PartnerShiftService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerShiftController {
    private final PartnerShiftService partnerShiftService;

    @GetMapping("/partners/shifts")
    public ResponseDto<PaginatedResponseDto<PartnerShiftDto>> getShifts(@RequestParam("status")String status, PageDto page,FilterDto filter) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerShiftService.getShifts(status,page,filter));
    }


    @GetMapping("/partners/{partner-uuid}/shift-mappings")
    public ResponseDto<List<PartnerShiftMappingDto>> getShiftMappings(@PathVariable(value = "partner-uuid") String partnerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerShiftService.getShiftMappings(partnerUUID));
    }
    @PostMapping("/partners/{partner-uuid}/shift-mappings")
    public ResponseDto<PartnerShiftMappingDto> createShift(@PathVariable(value = "partner-uuid") String partnerUUID, @RequestBody PartnerShiftMappingDto partnerShiftMappingDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerShiftService.createShiftMapping(partnerUUID, partnerShiftMappingDto));
    }

    @DeleteMapping("/partners/{partner-uuid}/shift-mappings/{partner-shift-mapping-uuid}")
    public ResponseDto<Boolean> deleteShiftMapping(@PathVariable(value = "partner-uuid") String partnerUUID,@PathVariable(value = "partner-shift-mapping-uuid") String partnerShiftMappingUUID)  {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerShiftService.deleteShiftMapping(partnerUUID, partnerShiftMappingUUID));
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
