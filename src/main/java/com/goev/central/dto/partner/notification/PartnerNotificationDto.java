package com.goev.central.dto.partner.notification;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerNotificationDto {

    private String uuid;
    private String status;
    private PartnerNotificationTemplateDto notificationTemplate;
    private String requestMetadata;
    private String responseMetadata;
}
