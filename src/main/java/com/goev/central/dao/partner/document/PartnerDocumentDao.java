


package com.goev.central.dao.partner.document;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerDocumentDao extends BaseDao {
    private Integer partnerId;
    private Integer partnerDocumentTypeId;
    private String url;
    private String fileName;
    private String description;
    private String extension;
    private String status;
}


