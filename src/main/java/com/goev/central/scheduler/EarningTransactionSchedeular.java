package com.goev.central.scheduler;


import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;
import com.goev.central.enums.partner.PartnerShiftSubStatus;
import com.goev.central.repository.earning.PartnerFixedEarningRepository;
import com.goev.central.repository.earning.EarningRuleRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.lib.exceptions.ResponseException;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class EarningTransactionSchedeular {
    private final PartnerRepository partnerRepository;
    private final EarningRuleRepository earningRuleRepository;
    private final PartnerShiftRepository partnerShiftRepository;
    private final PartnerFixedEarningRepository earningRepository;

    @Scheduled(cron = "1 1 1 * * ?", zone = "Asia/Calcutta")
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        calculateEarningPerDay(DateTime.now());
    }

    public void calculateEarningPerDay(DateTime executionTime){
        DateTime start = executionTime.withZone(ApplicationConstants.TIME_ZONE).minusDays(1).withTimeAtStartOfDay();

        List<PartnerDao> OnBoardedPartners = partnerRepository.findAllByOnboardingStatus("ONBOARDED");
        for(PartnerDao partner : OnBoardedPartners) {
            String segmentString = partner.getSegments();
            Gson gson = new Gson();
            Type segmentListType = new TypeToken<List<PartnerSegmentDto>>(){}.getType();
            List<PartnerSegmentDto> segments = gson.fromJson(segmentString, segmentListType);
            if (segments!=null) {
                String segmentName = segments.get(0).getName();
                EarningRuleDao earningRuleDao = earningRuleRepository.findAllByClientName(segmentName);
                if(earningRuleDao!=null) {
                    float fixedEarningPerDay= 0;
                    PartnerShiftDao partnerShiftDao = partnerShiftRepository.findPartnerShiftDetailsByPartnerIdAndDutyDate(partner.getId(),start);
                    if(partnerShiftDao !=null){
                        if(PartnerShiftSubStatus.PRESENT.name().equals(partnerShiftDao.getSubStatus())){
                            int fixedIncome = earningRuleDao.getFixedIncome();
                            int checkValue = earningRuleDao.getCheckValue();
                            fixedEarningPerDay = (float) fixedIncome/checkValue;
                        }

                    }
                    earningRepository.saveEarningTransaction(partner,earningRuleDao,fixedEarningPerDay,start);
                }



            }
        }

    }
}
