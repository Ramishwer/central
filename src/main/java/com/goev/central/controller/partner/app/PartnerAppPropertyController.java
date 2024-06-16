package com.goev.central.controller.partner.app;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppPropertyDto;
import com.goev.central.service.partner.app.PartnerAppPropertyService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerAppPropertyController {

    private final PartnerAppPropertyService partnerAppPropertyService;

    @GetMapping("/app/properties")
    public ResponseDto<PaginatedResponseDto<PartnerAppPropertyDto>> getPartnerAppProperties(@RequestParam("count") Integer count,
                                                                                            @RequestParam("start") Integer start,
                                                                                            @RequestParam("from") Long from,
                                                                                            @RequestParam("to") Long to,
                                                                                            @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAppPropertyService.getPartnerAppProperties());
    }


    @GetMapping("/app/properties/{app-property-uuid}")
    public ResponseDto<PartnerAppPropertyDto> getPartnerAppPropertyDetails(@PathVariable(value = "app-property-uuid") String appPropertyUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAppPropertyService.getPartnerAppPropertyDetails(appPropertyUUID));
    }


    @PostMapping("/app/properties")
    public ResponseDto<PartnerAppPropertyDto> createPartnerAppProperty(@RequestBody PartnerAppPropertyDto partnerAppPropertyDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAppPropertyService.createPartnerAppProperty(partnerAppPropertyDto));
    }

    @PutMapping("/app/properties/{app-property-uuid}")
    public ResponseDto<PartnerAppPropertyDto> updatePartnerAppProperty(@PathVariable(value = "app-property-uuid") String appPropertyUUID, @RequestBody PartnerAppPropertyDto partnerAppPropertyDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAppPropertyService.updatePartnerAppProperty(appPropertyUUID, partnerAppPropertyDto));
    }

    @DeleteMapping("/app/properties/{app-property-uuid}")
    public ResponseDto<Boolean> deletePartnerAppProperty(@PathVariable(value = "app-property-uuid") String appPropertyUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAppPropertyService.deletePartnerAppProperty(appPropertyUUID));
    }
}
