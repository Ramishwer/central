package com.goev.central.dao.payment;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class PaymentDao extends BaseDao {
    private String payingEntityType;
    private Integer payingEntityId;
    private String payingFor;
    private String paymentUuid;
    private String type;
    private String status;
    private Integer bookingId;
    private Integer paymentDetailId;
}




