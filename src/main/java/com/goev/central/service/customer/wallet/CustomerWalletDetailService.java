package com.goev.central.service.customer.wallet;


import com.goev.central.dto.customer.wallet.CustomerWalletDetailDto;

public interface CustomerWalletDetailService {

    CustomerWalletDetailDto getCustomerWalletDetails(String customerUUID);

}
