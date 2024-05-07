package com.goev.central.dto.customer.notification;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dto.customer.CustomerViewDto;
import com.goev.central.dto.customer.detail.CustomerDto;
import com.goev.central.dto.partner.detail.PartnerDto;
import com.goev.central.dto.vehicle.detail.VehicleDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerNotificationDto {

    private String uuid;
    private String status;
    private CustomerNotificationTemplateDto notificationTemplate;
    private String requestMetadata;
    private String responseMetadata;
}
