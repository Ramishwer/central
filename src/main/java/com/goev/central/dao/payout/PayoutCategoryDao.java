package com.goev.central.dao.payout;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PayoutCategoryDao extends BaseDao {
    private String name;
    private String type;
    private String description;
    private String label;
    private Boolean isVisible;
}


