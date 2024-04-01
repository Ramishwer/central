package com.goev.central.dao.partner.detail;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartnerAccountDetailDao extends BaseDao {
    private Integer partnerId;
    private String accountNumber;
    private String accountHolderName;
    private String ifscCode;
}
