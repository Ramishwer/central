package com.goev.central.dto.partner.document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
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
    @Builder.Default
    private Boolean isMandatory = false;
    @Builder.Default
    private Boolean needsVerification = false;

    public static PartnerDocumentTypeDto fromDao(PartnerDocumentTypeDao partnerDocumentTypeDao) {
        return PartnerDocumentTypeDto.builder()
                .s3Key(partnerDocumentTypeDao.getS3Key())
                .label(partnerDocumentTypeDao.getLabel())
                .name(partnerDocumentTypeDao.getName())
                .uuid(partnerDocumentTypeDao.getUuid())
                .groupKey(partnerDocumentTypeDao.getGroupKey())
                .groupDescription(partnerDocumentTypeDao.getGroupDescription())
                .isMandatory(partnerDocumentTypeDao.getIsMandatory())
                .needsVerification(partnerDocumentTypeDao.getNeedsVerification())
                .build();
    }

}
