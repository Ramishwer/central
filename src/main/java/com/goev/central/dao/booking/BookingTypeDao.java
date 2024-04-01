package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingTypeDao extends BaseDao {
    private String name;
    private String label;
    private DateTime applicableFromTime;
    private DateTime applicableToTime;
    private String pricingDescriptionDetails;
}
