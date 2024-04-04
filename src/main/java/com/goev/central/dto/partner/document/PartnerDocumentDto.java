package com.goev.central.dto.partner.document;

import lombok.*;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerDocumentDto {
    private String uuid;
    private String url;
    private PartnerDocumentTypeDto type;
    private String fileName;
    private String description;
    private String status;
    private Map<String,Object> data;
}
