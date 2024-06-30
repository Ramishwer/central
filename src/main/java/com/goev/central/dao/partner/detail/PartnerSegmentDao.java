package com.goev.central.dao.partner.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartnerSegmentDao extends BaseDao {
    private String name;
    private String description;
}
