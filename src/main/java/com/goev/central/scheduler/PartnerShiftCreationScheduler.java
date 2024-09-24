package com.goev.central.scheduler;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.location.LocationDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.partner.detail.PartnerDetailDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dao.partner.duty.PartnerShiftMappingDao;
import com.goev.central.dao.shift.ShiftConfigurationDao;
import com.goev.central.dao.shift.ShiftDao;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.partner.PartnerViewDto;
import com.goev.central.dto.partner.duty.PartnerDutyDto;
import com.goev.central.dto.partner.duty.PartnerShiftDto;
import com.goev.central.enums.partner.*;
import com.goev.central.repository.location.LocationRepository;
import com.goev.central.repository.partner.detail.PartnerDetailRepository;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.repository.partner.duty.PartnerShiftMappingRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
import com.goev.central.repository.shift.ShiftConfigurationRepository;
import com.goev.central.repository.shift.ShiftRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.TimeZone;

@Slf4j
@Component
@AllArgsConstructor
public class PartnerShiftCreationScheduler {

    private final PartnerShiftMappingRepository partnerShiftMappingRepository;
    private final PartnerShiftRepository partnerShiftRepository;
    private final ShiftConfigurationRepository shiftConfigurationRepository;
    private final PartnerRepository partnerRepository;
    private final LocationRepository locationRepository;
    private final PartnerDetailRepository partnerDetailRepository;
    private final ShiftRepository shiftRepository;

    @Scheduled(fixedRate = 2 * 60 * 1000)
    public void reportCurrentTime() {
        log.info("The {} time is now {}", this.getClass().getName(), DateTime.now());
        List<PartnerShiftMappingDao> allPartnerMappings = partnerShiftMappingRepository.findAllActive();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("HH:mm");

        int day = DateTime.now().withZone(ApplicationConstants.TIME_ZONE).getDayOfWeek();
        DateTime date = DateTime.now().withZone(ApplicationConstants.TIME_ZONE).withTimeAtStartOfDay();
        for (PartnerShiftMappingDao partnerShiftMappingDao : allPartnerMappings) {

            PartnerDao partner = partnerRepository.findById(partnerShiftMappingDao.getPartnerId());

            if(!PartnerOnboardingStatus.ONBOARDED.name().equals(partner.getOnboardingStatus()))
                continue;
            ShiftDao shift = shiftRepository.findById(partnerShiftMappingDao.getShiftId());
            if (PartnerStatus.OFF_DUTY.name().equals(partner.getStatus()) && PartnerSubStatus.NO_DUTY.name().equals(partner.getSubStatus())) {
                PartnerShiftDao partnerShiftDao = partnerShiftRepository.findByPartnerIdShiftIdDayDate(partner.getId(), shift.getId(), String.valueOf(day), date);
                if (partnerShiftDao == null) {

                    ShiftConfigurationDao shiftConfigurationDao = shiftConfigurationRepository.findByShiftIdAndDay(partnerShiftMappingDao.getShiftId(), day);

                    if (shiftConfigurationDao != null) {

                        partnerShiftDao = new PartnerShiftDao();

                        partnerShiftDao.setShiftStart(shiftConfigurationDao.getEstimatedIn());
                        partnerShiftDao.setShiftEnd(shiftConfigurationDao.getEstimatedOut());
                        partnerShiftDao.setShiftConfig(ApplicationConstants.GSON.toJson(shiftConfigurationDao));
                        partnerShiftDao.setShiftId(partnerShiftMappingDao.getShiftId());
                        partnerShiftDao.setPartnerId(partner.getId());
                        partnerShiftDao.setPayoutModelId(shiftConfigurationDao.getPayoutModelId());
                        partnerShiftDao.setDay(shiftConfigurationDao.getDay());
                        partnerShiftDao.setEstimatedStartTime(date.plus(formatter.parseDateTime(shiftConfigurationDao.getEstimatedIn()).getMillis()));
                        partnerShiftDao.setEstimatedEndTime(date.plus(formatter.parseDateTime(shiftConfigurationDao.getEstimatedOut()).getMillis()));
                        if(partner.getViewInfo()!=null) {
                            PartnerViewDto partnerViewDto = PartnerViewDto.fromDao(partner);
                            if(partnerViewDto!=null && partnerViewDto.getHomeLocation()!=null) {
                                LocationDao homeLocation = locationRepository.findByUUID(partnerViewDto.getHomeLocation().getUuid());
                                partnerShiftDao.setInLocationDetails(ApplicationConstants.GSON.toJson(partnerViewDto.getHomeLocation()));
                                partnerShiftDao.setInLocationId(homeLocation.getId());
                                partnerShiftDao.setOutLocationDetails(ApplicationConstants.GSON.toJson(partnerViewDto.getHomeLocation()));
                                partnerShiftDao.setOutLocationId(homeLocation.getId());
                            }
                        }
                        partnerShiftDao.setType(shift.getShiftType());
                        partnerShiftDao.setStatus(PartnerShiftStatus.PENDING.name());
                        partnerShiftDao.setSubStatus(PartnerShiftSubStatus.PENDING.name());
                        partnerShiftDao.setDutyDate(date);
                        log.info("Duty Date : {}", date);
                        partnerShiftDao = partnerShiftRepository.save(partnerShiftDao);
                    }

                }

                if (partnerShiftDao != null) {
                    if (partnerShiftDao.getEstimatedEndTime() != null && DateTime.now().isAfter(partnerShiftDao.getEstimatedEndTime())) {
                        continue;
                    }

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
