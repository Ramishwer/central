package com.goev.central.dao.partner.payout;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PartnerPayoutElementDao extends BaseDao {
    private String label;
    private String name;
    private String type;
    private String description;
}


