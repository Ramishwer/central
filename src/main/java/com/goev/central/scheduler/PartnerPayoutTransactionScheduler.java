package com.goev.central.scheduler;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dao.partner.payout.PartnerCreditDebitTransactionDao;
import com.goev.central.dao.partner.payout.PartnerPayoutDao;
import com.goev.central.dao.partner.payout.PartnerPayoutTransactionDao;
import com.goev.central.dto.VariableDto;
import com.goev.central.dto.payout.PayoutConfigDto;
import com.goev.central.dto.payout.PayoutElementDto;
import com.goev.central.enums.TransactionType;
import com.goev.central.enums.partner.PartnerCreditDebitTransactionStatus;
import com.goev.central.enums.partner.PartnerDutyStatus;
import com.goev.central.enums.partner.PartnerShiftStatus;
import com.goev.central.enums.partner.PartnerShiftSubStatus;
import com.goev.central.enums.payout.PayoutElementType;
import com.goev.central.repository.partner.duty.PartnerDutyRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.repository.partner.payout.PartnerCreditDebitTransactionRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutRepository;
import com.goev.central.repository.partner.payout.PartnerPayoutTransactionRepository;
import com.google.common.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@AllArgsConstructor
public class PartnerPayoutTransactionScheduler {
    private final PartnerPayoutRepository partnerPayoutRepository;
    private final PartnerPayoutTransactionRepository partnerPayoutTransactionRepository;
    private final PartnerShiftRepository partnerShiftRepository;
    private final PartnerDutyRepository partnerDutyRepository;
    private final PartnerCreditDebitTransactionRepository partnerCreditDebitTransactionRepository;


    @Scheduled(cron = "1 1 * * * ?", zone = "Asia/Calcutta")
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        calculatePayout(DateTime.now());
    }

    public void calculatePayout(DateTime executionTime) {
        List<PartnerPayoutDao> allLivePayouts = partnerPayoutRepository.findAllInProgressPayouts();
        DateTime start = executionTime.withZone(ApplicationConstants.TIME_ZONE).minusDays(1).withTimeAtStartOfDay();
        DateTime end = start.plusDays(1).minus(1L);
        DateTimeFormatter format = DateTimeFormat.forPattern("dd-MM-yyyy");
        String day = String.valueOf(start.withZone(ApplicationConstants.TIME_ZONE).getDayOfWeek());
        String date = start.toString(format);
        Type t = new TypeToken<Map<String, PayoutConfigDto>>() {
        }.getType();
        for (PartnerPayoutDao payoutDao : allLivePayouts) {
            Map<String, PayoutConfigDto> dayWiseConfig = ApplicationConstants.GSON.fromJson(payoutDao.getPayoutConfig(), t);
            if (dayWiseConfig == null || dayWiseConfig.isEmpty() || !dayWiseConfig.containsKey(day))
                continue;

            PayoutConfigDto config = dayWiseConfig.get(day);
            PartnerPayoutTransactionDao partnerPayoutTransactionDao = partnerPayoutTransactionRepository.findByPartnerPayoutIdAndDayAndDate(payoutDao.getId(), day, date);
            if (partnerPayoutTransactionDao == null) {
                partnerPayoutTransactionDao = new PartnerPayoutTransactionDao();
                partnerPayoutTransactionDao.setConfig(ApplicationConstants.GSON.toJson(config));
                partnerPayoutTransactionDao.setPartnerPayoutId(payoutDao.getId());
                partnerPayoutTransactionDao.setPartnerId(payoutDao.getPartnerId());
                partnerPayoutTransactionDao.setTransactionTime(DateTime.now());
                partnerPayoutTransactionDao.setDate(date);
                partnerPayoutTransactionDao.setDay(day);
                partnerPayoutTransactionDao = partnerPayoutTransactionRepository.save(partnerPayoutTransactionDao);
            }
            List<PartnerShiftDao> allShifts = partnerShiftRepository.findAllByPartnerIdAndStatusAndStartTimeBetweenStartAndEnd(payoutDao.getPartnerId(), PartnerShiftStatus.COMPLETED.name(), start, end);
            List<PartnerDutyDao> allDuties = partnerDutyRepository.findAllByPartnerIdAndPartnerShiftIdsAndStatus(payoutDao.getPartnerId(), allShifts.stream().map(PartnerShiftDao::getId).toList(), PartnerDutyStatus.COMPLETED.name());
            List<PartnerCreditDebitTransactionDao> allCreditDebitTransactions = partnerCreditDebitTransactionRepository.findAllByPartnerPayoutIdAndPartnerIdAndTransactionTimeBetween(payoutDao.getId(), payoutDao.getPartnerId(),start,end);

            List<PayoutElementDto> calculatedPayoutElements = calculatePayoutElement(config, payoutDao.getTotalWorkingDays(), allShifts, allDuties,allCreditDebitTransactions);
            partnerPayoutTransactionDao.setCalculatedPayoutElements(ApplicationConstants.GSON.toJson(calculatedPayoutElements));
            partnerPayoutTransactionDao.setTransactionTime(DateTime.now());
            partnerPayoutTransactionDao.setAmount(calculateAmount(calculatedPayoutElements));
            partnerPayoutTransactionRepository.update(partnerPayoutTransactionDao);
        }
    }

    private Integer calculateAmount(List<PayoutElementDto> calculatedPayoutElements) {
        Integer amount = 0;
        for (PayoutElementDto elementDto : calculatedPayoutElements) {
            if (PayoutElementType.CREDIT.name().equals(elementDto.getType())) {
                amount += elementDto.getValue();
            }
            if (PayoutElementType.DEBIT.name().equals(elementDto.getType())) {
                amount -= elementDto.getValue();
            }
        }
        return amount;
    }

    private List<PayoutElementDto> calculatePayoutElement(PayoutConfigDto payoutConfigDto, Integer totalWorkingDays, List<PartnerShiftDao> allShifts, List<PartnerDutyDao> allDuties, List<PartnerCreditDebitTransactionDao> allCreditDebitTransactions) {
        List<PayoutElementDto> result = new ArrayList<>();
        for (PayoutElementDto payoutElementDto : payoutConfigDto.getPayoutElementsConfig()) {
            payoutElementDto.setValue(getValueForElement(payoutElementDto, totalWorkingDays, allShifts, allDuties,allCreditDebitTransactions));
            result.add(payoutElementDto);
        }
        return result;
    }

    private Integer getValueForElement(PayoutElementDto payoutElementDto, Integer totalWorkingDays, List<PartnerShiftDao> allShifts, List<PartnerDutyDao> allDuties,List<PartnerCreditDebitTransactionDao> allCreditDebitTransactions) {
        int value = 0;
        if ("PAYABLE_DAYS".equals(payoutElementDto.getName())) {
            if(!CollectionUtils.isEmpty(allShifts))
                value =allShifts.stream().map(x->x.getDutyDate().getMillis()).distinct().toList().size();
        } else if ("PRESENT_DAYS".equals(payoutElementDto.getName())) {
           value = allShifts.stream().filter(x -> PartnerShiftSubStatus.PRESENT.name().equals(x.getSubStatus())).toList().size();
        } else if ("ABSENT_DAYS".equals(payoutElementDto.getName())) {
            value = allShifts.stream().filter(x -> PartnerShiftSubStatus.ABSENT.name().equals(x.getSubStatus())).toList().size();
        } else if ("FIXED_AMOUNT".equals(payoutElementDto.getName())) {
            boolean isPresent = true;
            for(PartnerShiftDao shifts : allShifts){
                if(!PartnerShiftSubStatus.PRESENT.name().equals(shifts.getSubStatus())) {
                    isPresent = false;
                    break;
                }
            }
            if(!CollectionUtils.isEmpty(allShifts) && isPresent){
                Integer baseAmount = Integer.parseInt(payoutElementDto.getVariables().stream().filter(x -> "Base Amount".equals(x.getKey())).findFirst().orElse(VariableDto.builder().value(String.valueOf(0)).build()).getValue());
                value = baseAmount / totalWorkingDays;
            }

        } else if ("LATE_PENALTY".equals(payoutElementDto.getName())) {
            allDuties.sort(Comparator.comparingLong(o -> o.getActualDutyStartTime().getMillis()));
            if (!CollectionUtils.isEmpty(allDuties)) {
                PartnerDutyDao firstDuty = allDuties.get(0);
                boolean isPartnerLateInDuty = firstDuty.getActualDutyStartTime().isAfter(firstDuty.getPlannedDutyStartTime().plusMinutes(20));
                if (isPartnerLateInDuty)
                    value = 100;
            }

        } else if ("OVERTIME".equals(payoutElementDto.getName())) {
            allDuties.sort(Comparator.comparingLong(o -> o.getActualDutyStartTime().getMillis()));
            if (!CollectionUtils.isEmpty(allDuties)) {
                PartnerDutyDao lastDuty = allDuties.get(allDuties.size()-1);
                boolean isOvertimeToBeGiven = lastDuty.getActualDutyEndTime().isAfter(lastDuty.getPlannedDutyEndTime());
                if (isOvertimeToBeGiven)
                    value = BigDecimal.valueOf((Math.min(lastDuty.getMaxOvertimeCalculationTime().getMillis(),lastDuty.getActualDutyEndTime().getMillis()) - lastDuty.getPlannedDutyEndTime().getMillis())/60000L).intValue();
            }

        } else if ("CREDIT".equals(payoutElementDto.getName())) {
            List<PartnerCreditDebitTransactionDao> allCreditTransactions =allCreditDebitTransactions.stream().filter(x-> TransactionType.CREDIT.name().equals(x.getTransactionType())).toList();
            if (!CollectionUtils.isEmpty(allCreditTransactions)) {
                value = allCreditTransactions.stream().mapToInt(PartnerCreditDebitTransactionDao::getAmount).sum();
            }

        } else if ("DEBIT".equals(payoutElementDto.getName())) {
            List<PartnerCreditDebitTransactionDao> allDebitTransactions =allCreditDebitTransactions.stream().filter(x-> TransactionType.DEBIT.name().equals(x.getTransactionType())).toList();
            if (!CollectionUtils.isEmpty(allDebitTransactions)) {
                value = allDebitTransactions.stream().mapToInt(PartnerCreditDebitTransactionDao::getAmount).sum();
            }

        }

        log.info("Calculated Value For : {} {} ",payoutElementDto.getName(),value);

        return value;
    }
}
