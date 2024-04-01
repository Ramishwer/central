package com.goev.central.dao.partner.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartnerAttributeDao extends BaseDao {
    private Integer partnerId;
    private String attributeKey;
    private String attributeValue;
}