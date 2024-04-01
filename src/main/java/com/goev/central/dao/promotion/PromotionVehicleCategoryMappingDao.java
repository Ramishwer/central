package com.goev.central.dao.promotion;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PromotionVehicleCategoryMappingDao extends BaseDao {
    private Integer promotionId;
    private Integer vehicleCategoryId;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
    private DateTime validFromTime;
    private DateTime validToTime;
}




