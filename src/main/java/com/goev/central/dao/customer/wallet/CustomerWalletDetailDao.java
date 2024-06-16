package com.goev.central.dao.customer.wallet;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class CustomerWalletDetailDao extends BaseDao {
    private Integer customerId;
    private Integer walletBalance;
}


