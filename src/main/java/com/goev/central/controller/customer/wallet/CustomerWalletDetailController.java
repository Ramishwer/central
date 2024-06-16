package com.goev.central.controller.customer.wallet;

import com.goev.central.dto.customer.wallet.CustomerWalletDetailDto;
import com.goev.central.service.customer.wallet.CustomerWalletDetailService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/customer-management")
@AllArgsConstructor
public class CustomerWalletDetailController {

    private final CustomerWalletDetailService customerWalletDetailService;


    @GetMapping("/customers/{customer-uuid}/wallets")
    public ResponseDto<CustomerWalletDetailDto> getCustomerWalletDetails(@PathVariable(value = "customer-uuid") String customerUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, customerWalletDetailService.getCustomerWalletDetails(customerUUID));
    }

}
