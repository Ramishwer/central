package com.goev.central.scheduler;


import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.payout.PartnerPayoutDao;
import com.goev.central.dao.partner.payout.PartnerPayoutMappingDao;
import com.goev.central.dao.partner.payout.PartnerPayoutTransactionDao;
import com.goev.central.dto.partner.payout.PartnerPayoutDto;
import com.goev.central.dto.partner.payout.PartnerPayoutSummaryDto;
import com.goev.central.dto.payout.PayoutElementDto;
import com.goev.central.dto.payout.PayoutModelDto;
import com.goev.central.enums.partner.PartnerPayoutStatus;
import com.goev.central.repository.partner.payout.PartnerPayoutMappingRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutTransactionRepository;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PartnerPayoutCreationScheduler {

    private final PartnerPayoutMappingRepository partnerPayoutMappingRepository;
    private final PartnerPayoutRepository partnerPayoutRepository;
    private final PartnerPayoutTransactionRepository partnerPayoutTransactionRepository;

    @Scheduled(cron = "0 0/5 * * * ?", zone = "Asia/Calcutta")
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        List<PartnerPayoutMappingDao> allPartnerMappings = partnerPayoutMappingRepository.findAllActive();
        Type t = new TypeToken<Map<String, PayoutModelDto>>() {
        }.getType();
        Type t1 = new TypeToken<List<PayoutElementDto>>() {
        }.getType();
        DateTime monthStart = DateTime.now().withZone(ApplicationConstants.TIME_ZONE).withDayOfMonth(1).withTimeAtStartOfDay();
        DateTime monthEnd = monthStart.plusMonths(1).minus(1L);

        for (PartnerPayoutMappingDao partnerPayoutMappingDao : allPartnerMappings) {
            Map<String, PayoutModelDto> mappingConfig = ApplicationConstants.GSON.fromJson(partnerPayoutMappingDao.getConfig(), t);
            if (mappingConfig == null || mappingConfig.isEmpty())
                continue;
            int totalWorkingDays = 0;
            for (DateTime temp = monthStart; temp.isBefore(monthEnd); temp = temp.plusDays(1)) {
                if (mappingConfig.containsKey("" + temp.withZone(ApplicationConstants.TIME_ZONE).getDayOfWeek()))
                    totalWorkingDays++;
            }

            PartnerPayoutDao payoutDao = partnerPayoutRepository.findByPartnerIdAndStartAndEndTime(partnerPayoutMappingDao.getPartnerId(), monthStart, monthEnd);
            if (payoutDao == null) {
                payoutDao = new PartnerPayoutDao();
                payoutDao.setPartnerId(partnerPayoutMappingDao.getPartnerId());
                payoutDao.setPayoutStartDate(monthStart);
                payoutDao.setPayoutEndDate(monthEnd);
                payoutDao.setTotalWorkingDays(totalWorkingDays);
                payoutDao.setPayoutTotalAmount(0);
                payoutDao.setFinalizationDate(monthEnd.plusDays(2));
                payoutDao.setStatus(PartnerPayoutStatus.IN_PROGRESS.name());
                payoutDao.setPayoutConfig(partnerPayoutMappingDao.getPayoutConfig());
                payoutDao = partnerPayoutRepository.save(payoutDao);
            }

            List<PartnerPayoutTransactionDao> allPayoutTransactions = partnerPayoutTransactionRepository.findAllByPartnerPayoutId(payoutDao.getId());

            if (!CollectionUtils.isEmpty(allPayoutTransactions)) {
                payoutDao.setPayoutTotalAmount(allPayoutTransactions.stream().map(PartnerPayoutTransactionDao::getAmount).reduce(0, Integer::sum));

                List<PayoutElementDto> allPayoutElements = new ArrayList<>();

                allPayoutTransactions.stream().filter(x -> x.getCalculatedPayoutElements() != null).forEach(x -> {
                    allPayoutElements.addAll(ApplicationConstants.GSON.fromJson(x.getCalculatedPayoutElements(), t1));
                });

                Map<String, List<PayoutElementDto>> nameWisePayoutModel = allPayoutElements.stream().collect(Collectors.groupingBy(PayoutElementDto::getName));

                Map<String, PayoutElementDto> mergedElements = new HashMap<>();

                for (Map.Entry<String, List<PayoutElementDto>> entry : nameWisePayoutModel.entrySet()) {
                    for (PayoutElementDto payoutElementDto : entry.getValue()) {
                        if (!mergedElements.containsKey(entry.getKey())) {
                            mergedElements.put(entry.getKey(), payoutElementDto);
                            continue;
                        }
                        mergedElements.get(entry.getKey()).setValue(mergedElements.get(entry.getKey()).getValue() + payoutElementDto.getValue());
                    }
                }
                PartnerPayoutSummaryDto summaryDto = PartnerPayoutSummaryDto.builder()
                        .partnerPayout(PartnerPayoutDto.fromDao(payoutDao,null))
                        .payoutStartDate(payoutDao.getPayoutStartDate())
                        .payoutEndDate(payoutDao.getPayoutEndDate())
                        .totalPayoutAmount(payoutDao.getPayoutTotalAmount())
                        .elements(new ArrayList<>(mergedElements.values()))
                        .build();

                payoutDao.setPayoutSummary(ApplicationConstants.GSON.toJson(summaryDto));
                payoutDao = partnerPayoutRepository.update(payoutDao);
            }


        }


    }
}
