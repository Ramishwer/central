package com.goev.central.dao.pricing;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PricingElementDao extends BaseDao {
    private Integer pricingModelId;
    private String label;
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
