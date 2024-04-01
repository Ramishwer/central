package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingInvoicingDetailDao extends BaseDao {
    private Integer bookingId;
    private String invoiceNumber;
    private String invoiceUrl;
    private String receiptUrl;
    private String receiptDetails;
}
