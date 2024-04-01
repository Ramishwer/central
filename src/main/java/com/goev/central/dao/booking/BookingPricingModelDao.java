package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingPricingModelDao extends BaseDao {
    private String name;
    private String description;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
    private Integer bookingPricingModelId;
}
