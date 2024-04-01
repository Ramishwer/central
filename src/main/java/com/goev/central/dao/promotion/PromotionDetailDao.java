package com.goev.central.dao.promotion;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PromotionDetailDao extends BaseDao {
    private String name;
    private String description;
    private String terms;
    private String restrictions;
    private Integer amount;
    private String amountType;
    private String isExclusive;
}




