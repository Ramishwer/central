package com.goev.central.repository.partner.duty;

import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dao.partner.duty.PartnerShiftDao;
import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import org.joda.time.DateTime;

import java.util.List;

public interface PartnerShiftRepository {
    PartnerShiftDao save(PartnerShiftDao partner);

    PartnerShiftDao update(PartnerShiftDao partner);

    void delete(Integer id);

    PartnerShiftDao findByUUID(String uuid);

    PartnerShiftDao findById(Integer id);

    List<PartnerShiftDao> findAllByIds(List<Integer> ids);

    List<PartnerShiftDao> findAllActive();

    List<PartnerShiftDao> findAllByPartnerId(Integer id);

    List<PartnerShiftDao> findAllByStatus(String status, PageDto page, FilterDto filter);

    List<PartnerShiftDao> findAllByStatus(String status, PageDto page);

    List<PartnerShiftDao> findAllByStatuses(List<String> status);

    PartnerShiftDao findByPartnerIdShiftIdDayDate(Integer partnerId, Integer shiftId, String currentDay, DateTime date);

    List<PartnerShiftDao> findAllByPartnerIdAndShiftIdAndStatus(Integer id, Integer shiftId, String name);

    List<PartnerShiftDao> findAllByPartnerIdAndStatusAndStartTimeBetweenStartAndEnd(Integer partnerId, String name, DateTime start, DateTime end);

    PartnerShiftDao findPartnerShiftDetailsByPartnerIdAndDutyDate(Integer id, DateTime dutyDate);

    Integer getNumberOfPresentDays (Integer id , DateTime monthStartDate , DateTime monthEndDate);

    Integer getNumberOfAbsentDays (Integer id , DateTime monthStartDate , DateTime monthEndDat);

}