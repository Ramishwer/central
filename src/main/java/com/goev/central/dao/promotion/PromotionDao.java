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
public class PromotionDao extends BaseDao {
    private String name;
    private Integer promotionDetailsId;
    private String status;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
    private DateTime validFromTime;
    private DateTime validToTime;
    private String type;
    private String platform;
    private String activationType;
}




