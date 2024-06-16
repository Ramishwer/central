package com.goev.central.dao.partner.document;

import com.goev.central.dto.partner.document.PartnerDocumentTypeDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerDocumentTypeDao extends BaseDao {
    private String name;
    private String s3Key;
    private String label;
    private String groupKey;
    private String groupDescription;
    private Boolean isMandatory;
    private Boolean needsVerification;

    public static PartnerDocumentTypeDao fromDto(PartnerDocumentTypeDto partnerDocumentTypeDto) {
        PartnerDocumentTypeDao partnerDocumentTypeDao = new PartnerDocumentTypeDao();

        partnerDocumentTypeDao.setName(partnerDocumentTypeDto.getName());
        partnerDocumentTypeDao.setS3Key(partnerDocumentTypeDto.getS3Key());
        partnerDocumentTypeDao.setLabel(partnerDocumentTypeDto.getLabel());
        partnerDocumentTypeDao.setUuid(partnerDocumentTypeDto.getUuid());

        partnerDocumentTypeDao.setGroupKey(partnerDocumentTypeDto.getGroupKey());
        partnerDocumentTypeDao.setGroupDescription(partnerDocumentTypeDto.getGroupDescription());
        partnerDocumentTypeDao.setIsMandatory(partnerDocumentTypeDto.getIsMandatory());
        partnerDocumentTypeDao.setNeedsVerification(partnerDocumentTypeDto.getNeedsVerification());

        return partnerDocumentTypeDao;
    }
}


