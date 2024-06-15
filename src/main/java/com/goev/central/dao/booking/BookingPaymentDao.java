package com.goev.central.dao.booking;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BookingPaymentDao extends BaseDao {
    private Integer bookingId;
    private Integer paymentId;
    private String status;
    private Integer amount;
    private String paymentMode;
    private String transactionPurpose;
    private DateTime transactionTime;
}
