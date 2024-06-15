package com.goev.central.dao.customer.payment;

import com.goev.lib.dao.BaseDao;
import lombok.*;
import org.joda.time.DateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomerPaymentDao extends BaseDao {
    private Integer customerId;
    private Integer paymentId;
    private String status;
    private Integer amount;
    private String paymentMode;
    private String transactionPurpose;
    private DateTime transactionTime;
}
