package com.goev.central.dto.partner.notification;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.customer.detail.CustomerDto;
import com.goev.central.dto.partner.PartnerViewDto;
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
