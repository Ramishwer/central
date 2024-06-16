package com.goev.central.controller.partner.app;


import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.app.PartnerAppEventDto;
import com.goev.central.service.partner.app.PartnerAppEventService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerAppEventController {

    private final PartnerAppEventService partnerAppEventService;

    @GetMapping("/partners/{partner-uuid}/app-events")
    public ResponseDto<PaginatedResponseDto<PartnerAppEventDto>> getPartnerAppEvents(@PathVariable("partner-uuid") String partnerUUID,
                                                                                     @RequestParam("count") Integer count,
                                                                                     @RequestParam("start") Integer start,
                                                                                     @RequestParam("from") Long from,
                                                                                     @RequestParam("to") Long to,
                                                                                     @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAppEventService.getPartnerAppEvents(partnerUUID));
    }


    @GetMapping("/partners/{partner-uuid}/app-events/{app-event-uuid}")
    public ResponseDto<PartnerAppEventDto> getPartnerAppEventDetails(@PathVariable("partner-uuid") String partnerUUID, @PathVariable("app-event-uuid") String appEventUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerAppEventService.getPartnerAppEventDetails(partnerUUID, appEventUUID));
    }


}
