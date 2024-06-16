package com.goev.central.controller.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerDeviceDto;
import com.goev.central.service.partner.detail.PartnerDeviceService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerDeviceController {

    private final PartnerDeviceService partnerDeviceService;

    @GetMapping("/partners/{partner-uuid}/devices")
    public ResponseDto<PaginatedResponseDto<PartnerDeviceDto>> getPartnerDevices(@PathVariable(value = "partner-uuid") String partnerUUID,
                                                                                 @RequestParam("count") Integer count,
                                                                                 @RequestParam("start") Integer start,
                                                                                 @RequestParam("from") Long from,
                                                                                 @RequestParam("to") Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDeviceService.getPartnerDevices(partnerUUID));
    }


    @GetMapping("/partners/{partner-uuid}/devices/{device-uuid}")
    public ResponseDto<PartnerDeviceDto> getPartnerDeviceDetails(@PathVariable(value = "partner-uuid") String partnerUUID,
                                                                 @PathVariable(value = "device-uuid") String deviceUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDeviceService.getPartnerDeviceDetails(partnerUUID, deviceUUID));
    }

    @DeleteMapping("/partners/{partner-uuid}/devices/{device-uuid}")
    public ResponseDto<Boolean> deletePartnerDevice(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "device-uuid") String deviceUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerDeviceService.deletePartnerDevice(partnerUUID, deviceUUID));
    }
}
