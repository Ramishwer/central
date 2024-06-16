package com.goev.central.dto.customer.notification;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerNotificationTemplateDto {

    private String uuid;
    private String title;
    private String body;
    private String templateMetadata;
    private String informationType;
    private String actionType;
    private String notificationType;
    private String templateKey;
}
