package com.goev.central.dao.partner.passbook;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerPassbookDetailDao extends BaseDao {
    private Integer partnerId;
    private Integer currentBalance;
}


