package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingPricingElementDao extends BaseDao {
    private String label;
    private String name;
    private String type;
    private String description;

}
