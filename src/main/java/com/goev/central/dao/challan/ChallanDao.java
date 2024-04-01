package com.goev.central.dao.challan;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ChallanDao extends BaseDao {
    private Integer partnerId;
    private Integer vehicleId;
    private String challanType;
    private String remark;
    private String challanUrl;
    private String status;
}
