package com.goev.central.dao.payment;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PaymentDetailDao extends BaseDao {
    private Integer paymentId;
    private String type;
    private String status;
    private String request;
    private String response;
}




