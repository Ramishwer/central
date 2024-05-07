package com.goev.central.controller.partner.notification;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.notification.PartnerNotificationDto;
import com.goev.central.service.partner.notification.PartnerNotificationService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerNotificationController {

    private final PartnerNotificationService partnerNotificationService;

    @GetMapping("/partners/{partner-uuid}/notifications")
    public ResponseDto<PaginatedResponseDto<PartnerNotificationDto>> getPartnerNotifications(@PathVariable(value = "partner-uuid") String partnerUUID,
                                                                                             @RequestParam("count") Integer count,
                                                                                             @RequestParam("start") Integer start,
                                                                                             @RequestParam("from") Long from,
                                                                                             @RequestParam("to") Long to,
                                                                                             @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerNotificationService.getPartnerNotifications(partnerUUID));
    }


    @GetMapping("/partners/{partner-uuid}/notifications/{notification-uuid}")
    public ResponseDto<PartnerNotificationDto> getPartnerNotificationDetails(@PathVariable(value = "partner-uuid") String partnerUUID,
                                                                             @PathVariable(value = "notification-uuid") String notificationUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerNotificationService.getPartnerNotificationDetails(partnerUUID, notificationUUID));
    }

}
