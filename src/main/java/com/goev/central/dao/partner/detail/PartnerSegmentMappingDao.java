package com.goev.central.dao.partner.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartnerSegmentMappingDao extends BaseDao {
    private Integer partnerId;
    private Integer partnerSegmentId;
    private String status;
}
