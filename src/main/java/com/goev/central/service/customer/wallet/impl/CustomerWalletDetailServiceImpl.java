package com.goev.central.service.customer.wallet.impl;

import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.dao.customer.wallet.CustomerWalletDetailDao;
import com.goev.central.dto.customer.wallet.CustomerWalletDetailDto;
import com.goev.central.repository.customer.detail.CustomerRepository;
import com.goev.central.repository.customer.wallet.CustomerWalletDetailRepository;
import com.goev.central.service.customer.wallet.CustomerWalletDetailService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerWalletDetailServiceImpl implements CustomerWalletDetailService {

    private final CustomerWalletDetailRepository customerWalletDetailRepository;
    private final CustomerRepository customerRepository;

    @Override
    public CustomerWalletDetailDto getCustomerWalletDetails(String customerUUID) {
        CustomerDao customerDao  = customerRepository.findByUUID(customerUUID);
        if(customerDao == null)
            throw new ResponseException("No customer  found for Id :" + customerUUID);

        CustomerWalletDetailDao customerWalletDetailDao = customerWalletDetailRepository.findByCustomerId(customerDao.getId());
        if (customerWalletDetailDao == null)
            throw new ResponseException("No customerWalletDetail  found for Id :" + customerUUID);
        return CustomerWalletDetailDto.builder()
                .uuid(customerWalletDetailDao.getUuid()).build();
    }

}
