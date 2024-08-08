package com.goev.central.dao.business;

import com.goev.lib.dao.BaseDao;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BusinessSegmentMappingDao extends BaseDao {
    private Integer businessSegmentId;
    private Integer vehicleSegmentId;
    private Integer partnerSegmentId;
    private String status;
}
