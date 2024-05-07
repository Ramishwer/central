package com.goev.central.controller.partner.notification;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.notification.PartnerNotificationTemplateDto;
import com.goev.central.service.partner.notification.PartnerNotificationTemplateService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/partner-management")
@AllArgsConstructor
public class PartnerNotificationTemplateController {

    private final PartnerNotificationTemplateService partnerNotificationTemplateService;

    @GetMapping("/notification-templates")
    public ResponseDto<PaginatedResponseDto<PartnerNotificationTemplateDto>> getPartnerNotificationTemplates(@RequestParam("count") Integer count,
                                                                                                             @RequestParam("start") Integer start,
                                                                                                             @RequestParam("from") Long from,
                                                                                                             @RequestParam("to") Long to,
                                                                                                             @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerNotificationTemplateService.getPartnerNotificationTemplates());
    }


    @GetMapping("/notification-templates/{template-uuid}")
    public ResponseDto<PartnerNotificationTemplateDto> getPartnerNotificationTemplateDetails(@PathVariable(value = "template-uuid") String notificationTemplateUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerNotificationTemplateService.getPartnerNotificationTemplateDetails(notificationTemplateUUID));
    }


    @PostMapping("/notification-templates")
    public ResponseDto<PartnerNotificationTemplateDto> createPartnerNotificationTemplate(@RequestBody PartnerNotificationTemplateDto partnerNotificationTemplateDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerNotificationTemplateService.createPartnerNotificationTemplate(partnerNotificationTemplateDto));
    }

    @PutMapping("/notification-templates/{template-uuid}")
    public ResponseDto<PartnerNotificationTemplateDto> updatePartnerNotificationTemplate(@PathVariable(value = "template-uuid") String notificationTemplateUUID, @RequestBody PartnerNotificationTemplateDto partnerNotificationTemplateDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerNotificationTemplateService.updatePartnerNotificationTemplate(notificationTemplateUUID, partnerNotificationTemplateDto));
    }

    @DeleteMapping("/notification-templates/{template-uuid}")
    public ResponseDto<Boolean> deletePartnerNotificationTemplate(@PathVariable(value = "template-uuid") String notificationTemplateUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, partnerNotificationTemplateService.deletePartnerNotificationTemplate(notificationTemplateUUID));
    }
}
