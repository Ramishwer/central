package com.goev.central.scheduler;

import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dao.partner.duty.PartnerShiftMappingDao;
import com.goev.central.dao.shift.ShiftConfigurationDao;
import com.goev.central.repository.partner.duty.PartnerShiftMappingRepository;
import com.goev.central.repository.partner.duty.PartnerShiftRepository;
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
public class PartnerShiftCreationScheduler {

    private final PartnerShiftMappingRepository partnerShiftMappingRepository;
    private final PartnerShiftRepository partnerShiftRepository;

//    @Scheduled(fixedRate = 10 * 1000)
//    public void reportCurrentTime() {
//        log.info("The time is now {}", DateTime.now());
//        List<PartnerShiftMappingDao> allPartnerMappings =partnerShiftMappingRepository.findAllActive();
//
//        for(PartnerShiftMappingDao partnerShiftMappingDao:allPartnerMappings){
//            Type t = new TypeToken<Map<String,ShiftConfigurationDao>>(){}.getType();
//            Map<String, ShiftConfigurationDao> dayWiseShiftConfig = ApplicationConstants.GSON.fromJson(partnerShiftMappingDao.getShiftConfig(),t);
//
//            DateTimeFormatter df = DateTimeFormat.forPattern("dd-mm-yyyy");
//            String currentDay  =""+DateTime.now().getDayOfWeek();
//            DateTime date = DateTime.now().withTimeAtStartOfDay();
//            log.info("{} {} {}",dayWiseShiftConfig,currentDay,date);
//            PartnerShiftDao partnerShiftDao = partnerShiftRepository.findByPartnerIdShiftIdDayDate(partnerShiftMappingDao.getPartnerId(),partnerShiftMappingDao.getShiftId(),currentDay,date);
//            if(partnerShiftDao == null && dayWiseShiftConfig.containsKey(currentDay)){
//                partnerShiftDao = new PartnerShiftDao();
//                partnerShiftDao.setPartnerId(partnerShiftMappingDao.getPartnerId());
//                partnerShiftDao.setShiftId(partnerShiftMappingDao.getShiftId());
//                partnerShiftDao.setDay(currentDay);
//                partnerShiftDao.setDutyDate(date);
//                partnerShiftDao.setEstimatedStartTime(date.plus());
//                partnerShiftDao.setEstimatedEndTime(date.plus());
//                partnerShiftDao.set
//            }
//
//        }
//
//    }
}
