package com.goev.central.service.earning.impl;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.earning.PartnerEarningDao;
import com.goev.central.dao.earning.PartnerFixedEarningTransactionDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dto.earning.PartnerEarningDto;
import com.goev.central.dto.earning.PartnerFixedEarningTransactionDto;
import com.goev.central.repository.earning.PartnerEarningRepository;
import com.goev.central.repository.earning.PartnerFixedEarningRepository;
import com.goev.central.repository.earning.PartnerTotalEarningRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.service.earning.PartnerEarningService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PartnerEarningSreviceImpl implements PartnerEarningService {

    private final PartnerRepository partnerRepository;
    private final PartnerEarningRepository partnerEarningRepository;
    private final PartnerFixedEarningRepository partnerFixedEarningRepository;

    @Override
    public PartnerEarningDto getPartnerEarningDetails(String partnerUuid){

        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUuid);
        if(partnerDao==null){
            throw new ResponseException("No Partner found for Uuid :" + partnerUuid);
        }
        DateTime executionTime= DateTime.now();
        DateTime monthStartDate = executionTime.withZone(ApplicationConstants.TIME_ZONE)
                .withDayOfMonth(1)
                .withTimeAtStartOfDay();

        DateTime monthEndDate = monthStartDate.plusMonths(1).minus(1L);

        PartnerEarningDao partnerEarningDao = partnerEarningRepository.getPartnerEarningDetails(partnerDao.getId(), monthStartDate, monthEndDate);
        if(partnerEarningDao==null){
            partnerEarningDao = new PartnerEarningDao();
            partnerEarningDao.setStartDate(monthStartDate);
            partnerEarningDao.setEndDate(monthEndDate);
            partnerEarningDao.setTotalEarning(0f);
            return PartnerEarningDto.fromDao(partnerEarningDao);

        }

        return PartnerEarningDto.fromDao(partnerEarningDao);

    }

    @Override
    public List<PartnerFixedEarningTransactionDto> getPartnerEarningTransactions(String partnerUuid){
        PartnerDao partnerDao = partnerRepository.findByUUID(partnerUuid);
        if(partnerDao==null){
            throw new ResponseException("No Partner found for Uuid :" + partnerUuid);
        }
        DateTime executionTime= DateTime.now();
        DateTime monthStartDate = executionTime.withZone(ApplicationConstants.TIME_ZONE)
                .withDayOfMonth(1)
                .withTimeAtStartOfDay();

        DateTime monthEndDate = monthStartDate.plusMonths(1).minus(1L);

        List<PartnerFixedEarningTransactionDao> partnerFixedEarningTransactionDaos = partnerFixedEarningRepository.getPartnerFixedEarningTransactionDeatils(partnerDao.getId(), monthStartDate, monthEndDate);
        List<PartnerFixedEarningTransactionDto> result = new ArrayList<>();
        if(partnerFixedEarningTransactionDaos==null){
            return result;
        }

        for(PartnerFixedEarningTransactionDao partnerFixedEarningTransactionDao : partnerFixedEarningTransactionDaos){
            result.add(PartnerFixedEarningTransactionDto.fromDao(partnerFixedEarningTransactionDao));
        }

        return result;

    }


}
