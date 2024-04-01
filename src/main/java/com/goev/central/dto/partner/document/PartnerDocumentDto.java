package com.goev.central.dto.partner.document;

import lombok.*;

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
}
