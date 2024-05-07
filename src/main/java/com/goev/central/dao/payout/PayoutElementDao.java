package com.goev.central.dao.payout;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PayoutElementDao extends BaseDao {
    private String name;
    private String type;
    private String description;
    private String rules;
    private String title;
    private String subtitle;
    private String message;
    private String category;
    private Integer sortingOrder;
}


