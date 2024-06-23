package com.goev.central.service.customer.wallet.impl;

import com.goev.central.dao.customer.wallet.CustomerWalletTransactionDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.wallet.CustomerWalletTransactionDto;
import com.goev.central.repository.customer.wallet.CustomerWalletTransactionRepository;
import com.goev.central.service.customer.wallet.CustomerWalletTransactionService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerWalletTransactionServiceImpl implements CustomerWalletTransactionService {

    private final CustomerWalletTransactionRepository customerWalletTransactionRepository;

    @Override
    public PaginatedResponseDto<CustomerWalletTransactionDto> getCustomerWalletTransactions(String customerUUID) {
        PaginatedResponseDto<CustomerWalletTransactionDto> result = PaginatedResponseDto.<CustomerWalletTransactionDto>builder().elements(new ArrayList<>()).build();
        List<CustomerWalletTransactionDao> customerWalletTransactionDaos = customerWalletTransactionRepository.findAllActive();
        if (CollectionUtils.isEmpty(customerWalletTransactionDaos))
            return result;

        for (CustomerWalletTransactionDao customerWalletTransactionDao : customerWalletTransactionDaos) {
            result.getElements().add(CustomerWalletTransactionDto.builder()
                    .uuid(customerWalletTransactionDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public CustomerWalletTransactionDto getCustomerWalletTransactionDetails(String customerUUID, String customerWalletTransactionUUID) {
        CustomerWalletTransactionDao customerWalletTransactionDao = customerWalletTransactionRepository.findByUUID(customerWalletTransactionUUID);
        if (customerWalletTransactionDao == null)
            throw new ResponseException("No customerWalletTransaction  found for Id :" + customerWalletTransactionUUID);
        return CustomerWalletTransactionDto.builder()
                .uuid(customerWalletTransactionDao.getUuid()).build();
    }

}
