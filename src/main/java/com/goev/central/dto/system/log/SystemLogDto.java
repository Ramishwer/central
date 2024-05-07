package com.goev.central.dto.system.log;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemLogDto {
    private String uuid;
    private Integer deviceId;
    private Integer entityId;
    private String api;
    private String requestData;
    private String queryParams;
    private String applicationSource;
    private String locationDetails;
    private String requestHeaders;
    private String responseStatus;
    private String log;
}
