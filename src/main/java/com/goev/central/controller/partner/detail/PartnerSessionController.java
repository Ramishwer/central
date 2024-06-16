package com.goev.central.controller.partner.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.detail.PartnerSessionDto;
import com.goev.central.service.partner.detail.PartnerSessionService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerSessionController {

    private final PartnerSessionService partnerSessionService;

    @GetMapping("/partners/{partner-uuid}/sessions")
    public ResponseDto<PaginatedResponseDto<PartnerSessionDto>> getPartnerSessions(@PathVariable(value = "partner-uuid") String partnerUUID,
                                                                                   @RequestParam("count") Integer count,
                                                                                   @RequestParam("start") Integer start,
                                                                                   @RequestParam("from") Long from,
                                                                                   @RequestParam("to") Long to,
                                                                                   @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSessionService.getPartnerSessions(partnerUUID));
    }


    @GetMapping("/partners/{partner-uuid}/sessions/{session-uuid}")
    public ResponseDto<PartnerSessionDto> getPartnerSessionDetails(@PathVariable(value = "partner-uuid") String partnerUUID,
                                                                   @PathVariable(value = "session-uuid") String sessionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSessionService.getPartnerSessionDetails(partnerUUID, sessionUUID));
    }

    @DeleteMapping("/partners/{partner-uuid}/sessions/{session-uuid}")
    public ResponseDto<Boolean> deletePartnerSession(@PathVariable(value = "partner-uuid") String partnerUUID, @PathVariable(value = "session-uuid") String sessionUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerSessionService.deletePartnerSession(partnerUUID, sessionUUID));
    }
}
