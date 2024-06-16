package com.goev.central.dto.partner.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDocumentDto {
    private String uuid;
    private String url;
    private PartnerDocumentTypeDto type;
    private String fileName;
    private String description;
    private String status;
    private Map<String, Object> data;
}
