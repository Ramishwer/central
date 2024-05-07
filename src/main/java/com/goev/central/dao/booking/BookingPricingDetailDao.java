package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingPricingDetailDao extends BaseDao {
    private Integer bookingId;
    private Integer plannedTotalAmount;
    private Integer plannedTotalAmountExcludingTax;
    private Integer plannedTaxAmount;
    private Integer plannedTaxPercentage;
    private Integer actualTotalAmount;
    private Integer actualTotalAmountExcludingTax;
    private Integer actualTaxAmount;
    private Integer actualTaxPercentage;
    private String status;
    private Integer pricingModelId;
    private Integer pricingElementId;
}
