


package com.goev.central.dao.partner.document;

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
}


