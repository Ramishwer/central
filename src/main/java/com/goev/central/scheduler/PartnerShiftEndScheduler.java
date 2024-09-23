package com.goev.central.scheduler;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.enums.partner.PartnerShiftStatus;
import com.goev.central.enums.partner.PartnerShiftSubStatus;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.enums.partner.PartnerSubStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class PartnerShiftEndScheduler {

    private final PartnerRepository partnerRepository;
    private final PartnerShiftRepository partnerShiftRepository;


    @Scheduled(fixedRate = 30 * 60 * 1000)
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        List<PartnerShiftDao> shifts = partnerShiftRepository.findAllByStatuses(Arrays.asList(PartnerShiftStatus.PENDING.name(),PartnerShiftStatus.IN_PROGRESS.name()));

        for(PartnerShiftDao shiftDao:shifts){
            if(shiftDao.getEstimatedEndTime().isBeforeNow())
                continue;
            PartnerDao partnerDao = partnerRepository.findById(shiftDao.getId());

            if(PartnerStatus.OFF_DUTY.name().equals(partnerDao.getStatus()) && shiftDao.getId().equals(partnerDao.getPartnerShiftId())){
                partnerDao.setPartnerShiftId(null);
                partnerDao.setDutyDetails(null);
                partnerDao.setSubStatus(PartnerSubStatus.NO_DUTY.name());
                partnerRepository.update(partnerDao);
            }



            if(PartnerShiftStatus.PENDING.name().equals(shiftDao.getStatus())){
                shiftDao.setSubStatus(PartnerShiftSubStatus.ABSENT.name());
            }
            shiftDao.setStatus(PartnerShiftStatus.COMPLETED.name());
            partnerShiftRepository.update(shiftDao);
        }

    }
}
