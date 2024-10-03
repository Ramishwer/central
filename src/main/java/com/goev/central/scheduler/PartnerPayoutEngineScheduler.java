package com.goev.central.scheduler;


import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerShiftMappingDao;
import com.goev.central.dao.partner.payout.PartnerPayoutDao;
import com.goev.central.dao.partner.payout.PartnerPayoutMappingDao;
import com.goev.central.dto.payout.PayoutModelDto;
import com.goev.central.enums.partner.PartnerPayoutStatus;
import com.goev.central.repository.partner.duty.PartnerShiftMappingRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutMappingRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutRepository;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@AllArgsConstructor
public class PartnerPayoutEngineScheduler {

    private final PartnerPayoutMappingRepository partnerPayoutMappingRepository;
    private final PartnerPayoutRepository partnerPayoutRepository;

    @Scheduled(cron = "0 0 0/6 * * ?" , zone = "Asia/Calcutta")
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        List<PartnerPayoutMappingDao> allPartnerMappings = partnerPayoutMappingRepository.findAllActive();
        Type t = new TypeToken<Map<String, PayoutModelDto>>(){}.getType();
        DateTime monthStart = DateTime.now().withZone(ApplicationConstants.TIME_ZONE).withDayOfMonth(1).withTimeAtStartOfDay();
        DateTime monthEnd = monthStart.plusMonths(1).minus(1L);

        for (PartnerPayoutMappingDao partnerPayoutMappingDao : allPartnerMappings) {
            Map<String,PayoutModelDto> mappingConfig = ApplicationConstants.GSON.fromJson(partnerPayoutMappingDao.getConfig(),t);
            if(mappingConfig == null || mappingConfig.isEmpty())
                continue;
            int totalWorkingDays = 0;
            for(DateTime temp = monthStart;temp.isBefore(monthEnd);temp= temp.plusDays(1)){
                if(mappingConfig.containsKey(""+temp.withZone(ApplicationConstants.TIME_ZONE).getDayOfWeek()))
                    totalWorkingDays++;
            }

            PartnerPayoutDao payoutDao = partnerPayoutRepository.findByPartnerIdAndStartAndEndTime(partnerPayoutMappingDao.getPartnerId(),monthStart,monthEnd);
            if(payoutDao==null){
                payoutDao = new PartnerPayoutDao();
                payoutDao.setPartnerId(partnerPayoutMappingDao.getPartnerId());
                payoutDao.setPayoutStartDate(monthStart);
                payoutDao.setPayoutStartDate(monthEnd);
                payoutDao.setTotalWorkingDays(totalWorkingDays);
                payoutDao.setPayoutTotalAmount(0);
                payoutDao.setPayoutTotalDebitAmount(0);
                payoutDao.setPayoutTotalCreditAmount(0);
                payoutDao.setPayoutTotalBookingAmount(0);
                payoutDao.setPayoutTotalDeductionAmount(0);
                payoutDao.setFinalizationDate(monthEnd.plusDays(2));
                payoutDao.setStatus(PartnerPayoutStatus.IN_PROGRESS.name());
                payoutDao = partnerPayoutRepository.save(payoutDao);
            }

        }


    }
}
