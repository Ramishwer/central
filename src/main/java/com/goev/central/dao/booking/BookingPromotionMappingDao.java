package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingPromotionMappingDao extends BaseDao {
    private String bookingId;
    private String promotionId;
    private String discountAmount;
    private String discountType;
}
