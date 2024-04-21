package com.goev.central.dto.partner.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PartnerDocumentTypeDto {
    private String uuid;
    private String name;
    private String s3Key;
    private String label;
    private String groupKey;
    private String groupDescription;
}
