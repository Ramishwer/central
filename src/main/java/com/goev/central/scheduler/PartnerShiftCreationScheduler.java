package com.goev.central.scheduler;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dao.partner.duty.PartnerShiftMappingDao;
import com.goev.central.dao.shift.ShiftConfigurationDao;
import com.goev.central.dao.shift.ShiftDao;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.enums.partner.PartnerShiftStatus;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.enums.partner.PartnerSubStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerShiftMappingRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.repository.shift.ShiftConfigurationRepository;
import com.goev.central.repository.shift.ShiftRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@AllArgsConstructor
public class PartnerShiftCreationScheduler {

    private final PartnerShiftMappingRepository partnerShiftMappingRepository;
    private final PartnerShiftRepository partnerShiftRepository;
    private final ShiftConfigurationRepository shiftConfigurationRepository;
    private final PartnerRepository partnerRepository;
    private final ShiftRepository shiftRepository;

    @Scheduled(fixedRate = 2 * 60 * 1000)
    public void reportCurrentTime() {
        log.info("The {} time is now {}",this.getClass().getName() ,DateTime.now());
        List<PartnerShiftMappingDao> allPartnerMappings = partnerShiftMappingRepository.findAllActive();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");

        int day = DateTime.now().getDayOfWeek();
        DateTime date = DateTime.now().withTimeAtStartOfDay();
        for (PartnerShiftMappingDao partnerShiftMappingDao : allPartnerMappings) {

            PartnerDao partner = partnerRepository.findById(partnerShiftMappingDao.getPartnerId());
            ShiftDao shift = shiftRepository.findById(partnerShiftMappingDao.getShiftId());
            if (PartnerStatus.OFF_DUTY.name().equals(partner.getStatus()) && PartnerSubStatus.NO_DUTY.name().equals(partner.getSubStatus())) {
                PartnerShiftDao existingShiftDao = partnerShiftRepository.findByPartnerIdShiftIdDayDate(partner.getId(),shift.getId(),String.valueOf(day),date);
                if( existingShiftDao !=null)
                    continue;
                ShiftConfigurationDao shiftConfigurationDao = shiftConfigurationRepository.findByShiftIdAndDay(partnerShiftMappingDao.getShiftId(), day);

                if (shiftConfigurationDao != null) {
                    PartnerShiftDao partnerShiftDao = new PartnerShiftDao();

                    partnerShiftDao.setShiftStart(shiftConfigurationDao.getEstimatedIn());
                    partnerShiftDao.setShiftEnd(shiftConfigurationDao.getEstimatedOut());
                    partnerShiftDao.setShiftConfig(ApplicationConstants.GSON.toJson(shiftConfigurationDao));
                    partnerShiftDao.setShiftId(partnerShiftMappingDao.getShiftId());
                    partnerShiftDao.setPartnerId(partner.getId());
                    partnerShiftDao.setPayoutModelId(shiftConfigurationDao.getPayoutModelId());
                    partnerShiftDao.setDay(shiftConfigurationDao.getDay());
                    partnerShiftDao.setEstimatedStartTime(DateTime.now().withTimeAtStartOfDay().plus(formatter.parseDateTime(shiftConfigurationDao.getEstimatedIn()).getMillis()));
                    partnerShiftDao.setEstimatedEndTime(DateTime.now().withTimeAtStartOfDay().plus(formatter.parseDateTime(shiftConfigurationDao.getEstimatedOut()).getMillis()));

                    partnerShiftDao.setType(shift.getShiftType());
                    partnerShiftDao.setStatus(PartnerShiftStatus.PENDING.name());
                    partnerShiftDao.setDutyDate(date);
                    partnerShiftDao = partnerShiftRepository.save(partnerShiftDao);

                    partner.setDutyDetails(ApplicationConstants.GSON.toJson(PartnerDutyDto.builder().partner(PartnerViewDto.fromDao(partner))
                            .shiftDetails(PartnerShiftDto.fromDao(partnerShiftDao, PartnerViewDto.fromDao(partner)))
                            .build()));
                    partner.setSubStatus(PartnerSubStatus.DUTY_ASSIGNED.name());
                    partner.setPartnerShiftId(partnerShiftDao.getId());
                    partnerRepository.update(partner);

                }

            }


        }
    }
}
