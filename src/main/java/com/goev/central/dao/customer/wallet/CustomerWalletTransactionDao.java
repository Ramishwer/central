


package com.goev.central.dao.customer.wallet;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerWalletTransactionDao extends BaseDao {
    private Integer customerId;
    private Integer openingWalletBalance;
    private Integer closingWalletBalance;
    private Integer bookingId;
    private Integer customerPaymentId;
    private String message;
    private String subtitle;
    private String remark;
    private String status;
    private String transactionType;
    private String entryType;
    private Integer amount;
}


