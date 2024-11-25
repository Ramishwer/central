package com.goev.central.scheduler;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dao.earning.PartnerEarningDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.payout.PartnerPayoutDao;
import com.goev.central.dto.partner.detail.PartnerSegmentDto;
import com.goev.central.repository.earning.EarningRuleRepository;
import com.goev.central.repository.earning.PartnerFixedEarningRepository;
import com.goev.central.repository.earning.PartnerTotalEarningRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutRepository;
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
public class EarningCalculateSchedular {
    private final PartnerRepository partnerRepository;
    private final PartnerShiftRepository partnerShiftRepository;
    private final PartnerFixedEarningRepository partnerFixedEarningRepository;
    private final EarningRuleRepository earningRuleRepository;
    private final PartnerTotalEarningRepository partnerTotalEarningRepository;

    @Scheduled(cron = "0 0 2 * * ?", zone = "Asia/Calcutta")
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        calculateEarning(DateTime.now());
    }

    public void calculateEarning (DateTime executionTime) {
        DateTime monthStartDate = executionTime.withZone(ApplicationConstants.TIME_ZONE)
                .withDayOfMonth(1)
                .withTimeAtStartOfDay();

        DateTime monthEndDate = monthStartDate.plusMonths(1).minus(1L);

        List<PartnerDao> partners = partnerRepository.findAllByOnboardingStatus("ONBOARDED");
        for (PartnerDao partner : partners) {
            String segmentString = partner.getSegments();
            Gson gson = new Gson();
            Type segmentListType = new TypeToken<List<PartnerSegmentDto>>() {
            }.getType();
            List<PartnerSegmentDto> segments = gson.fromJson(segmentString, segmentListType);
            if (segments!=null) {
                String segmentName = segments.get(0).getName();
                EarningRuleDao earningRuleDao = earningRuleRepository.findAllByClientName(segmentName);
                if (earningRuleDao!=null) {
                    Float totalFixedEarning = partnerFixedEarningRepository.getTotalFixedEarning(partner, monthStartDate, monthEndDate);

                    Integer noOfPresentDays = partnerShiftRepository.getNumberOfPresentDays(partner.getId(), monthStartDate, monthEndDate);

                    Integer noOfAbsentDays = partnerShiftRepository.getNumberOfAbsentDays(partner.getId(), monthStartDate, monthEndDate);

                    PartnerEarningDao partnerEarningDao = partnerTotalEarningRepository.getPartnerEarningDetails(partner, monthStartDate, monthEndDate);

                    if (partnerEarningDao == null) {
                        partnerTotalEarningRepository.savePartnerTotalEarning(partner, earningRuleDao,monthStartDate, monthEndDate, totalFixedEarning, noOfPresentDays, noOfAbsentDays);
                    } else {
                        partnerEarningDao.setStartDate(monthStartDate);
                        partnerEarningDao.setEndDate(monthEndDate);
                        partnerEarningDao.setTotalEarning(totalFixedEarning);
                        partnerEarningDao.setPresentDays(noOfPresentDays);
                        partnerEarningDao.setAbsentDays(noOfAbsentDays);
                        partnerTotalEarningRepository.updatePartnerTotalEarning(partnerEarningDao);
                    }
                }



            }
        }
    }

    }

