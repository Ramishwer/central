package com.goev.central.dao.customer.promotion;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerPromotionDao extends BaseDao {
    private Integer customerId;
    private Integer promotionId;
    private Integer totalPromotionAmount;
    private Integer totalNoOfActivation;
    private DateTime expiryTime;
    private String promotionDetails;
}


