package com.goev.central.scheduler;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dto.partner.detail.PartnerActionDto;
import com.goev.central.enums.partner.PartnerAction;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.service.partner.detail.PartnerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class PartnerAutoCheckoutScheduler {

    private final PartnerRepository partnerRepository;
    private final PartnerShiftRepository partnerShiftRepository;
    private final PartnerService partnerService;


    @Scheduled(fixedRate = 15 * 60 * 1000)
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        List<PartnerDao> partnerDaoList = partnerRepository.findAllByStatus(Collections.singletonList(PartnerStatus.ON_DUTY.name()));

        for(PartnerDao partner : partnerDaoList){
            PartnerShiftDao shiftDao = partnerShiftRepository.findById(partner.getPartnerShiftId());
            if(shiftDao!=null && shiftDao.getEstimatedEndTime() !=null && shiftDao.getEstimatedEndTime().isBeforeNow()){
                partnerService.updatePartner(partner.getUuid(), PartnerActionDto.builder().action(PartnerAction.CHECK_OUT).build());
            }
        }

    }
}
