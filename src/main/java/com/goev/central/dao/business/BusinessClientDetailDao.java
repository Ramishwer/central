package com.goev.central.dao.business;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BusinessClientDetailDao extends BaseDao {
    private String businessAddressDetails;
    private String gstNumber;
    private String contactPersonDetails;
    private String appType;
    private Integer businessClientId;
    private Integer businessSegmentId;
}
