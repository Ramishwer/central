package com.goev.central.dao.partner.app;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerAppStringDao extends BaseDao {
    private String key;
    private String language;
    private String value;
    private Integer parentId;
}


