package com.goev.central.dao.partner.app;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerAppPropertyDao extends BaseDao {
    private String propertyName;
    private String propertyDescription;
    private String propertyType;
    private String propertyValue;
}


