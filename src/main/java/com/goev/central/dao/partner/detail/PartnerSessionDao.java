package com.goev.central.dao.partner.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartnerSessionDao extends BaseDao {
    private Integer partnerId;
    private DateTime lastActiveTime;
    private String status;
    private String authSessionUuid;
}