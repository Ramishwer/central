package com.goev.central.service.payment.impl;

import com.goev.central.dao.payment.PaymentDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payment.PaymentDto;
import com.goev.central.repository.payment.PaymentRepository;
import com.goev.central.service.payment.PaymentService;
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
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaginatedResponseDto<PaymentDto> getPayments() {
        PaginatedResponseDto<PaymentDto> result = PaginatedResponseDto.<PaymentDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PaymentDao> paymentDaos = paymentRepository.findAll();
        if (CollectionUtils.isEmpty(paymentDaos))
            return result;

        for (PaymentDao paymentDao : paymentDaos) {
            result.getElements().add(PaymentDto.builder()
                    .uuid(paymentDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PaymentDto getPaymentDetails(String paymentUUID) {
        PaymentDao paymentDao = paymentRepository.findByUUID(paymentUUID);
        if (paymentDao == null)
            throw new ResponseException("No payment  found for Id :" + paymentUUID);
        return PaymentDto.builder()
                .uuid(paymentDao.getUuid()).build();
    }

}
