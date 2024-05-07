package com.goev.central.service.payment.impl;

import com.goev.central.dao.payment.PaymentDetailDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payment.PaymentDetailDto;
import com.goev.central.repository.payment.PaymentDetailRepository;
import com.goev.central.service.payment.PaymentDetailService;
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
public class PaymentDetailServiceImpl implements PaymentDetailService {

    private final PaymentDetailRepository paymentDetailRepository;

    @Override
    public PaginatedResponseDto<PaymentDetailDto> getPaymentDetails(String paymentUUID) {
        PaginatedResponseDto<PaymentDetailDto> result = PaginatedResponseDto.<PaymentDetailDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PaymentDetailDao> paymentDetailDaos = paymentDetailRepository.findAll();
        if (CollectionUtils.isEmpty(paymentDetailDaos))
            return result;

        for (PaymentDetailDao paymentDetailDao : paymentDetailDaos) {
            result.getElements().add(PaymentDetailDto.builder()
                    .uuid(paymentDetailDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PaymentDetailDto getPaymentDetailDetails(String paymentUUID, String paymentDetailUUID) {
        PaymentDetailDao paymentDetailDao = paymentDetailRepository.findByUUID(paymentDetailUUID);
        if (paymentDetailDao == null)
            throw new ResponseException("No paymentDetail  found for Id :" + paymentDetailUUID);
        return PaymentDetailDto.builder()
                .uuid(paymentDetailDao.getUuid()).build();
    }

}
