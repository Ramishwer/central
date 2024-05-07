package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingPricingBreakDetailDao extends BaseDao {
    private String bookingPricingDetailsId;
    private String elementName;
    private String elementType;
    private String elementAmount;
    private String bookingId;
    private String triggerType;
    private String pricingElementId;
}
