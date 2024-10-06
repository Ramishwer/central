package com.goev.central.repository.partner.duty;

import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;

import java.util.List;

public interface PartnerDutyRepository {
    PartnerDutyDao save(PartnerDutyDao partner);

    PartnerDutyDao update(PartnerDutyDao partner);

    void delete(Integer id);

    PartnerDutyDao findByUUID(String uuid);

    PartnerDutyDao findById(Integer id);

    List<PartnerDutyDao> findAllByIds(List<Integer> ids);

    List<PartnerDutyDao> findAllActive();

    List<PartnerDutyDao> findAllByPartnerId(Integer id);

    List<PartnerDutyDao> findAllByStatus(String status, PageDto page, FilterDto filter);

    List<PartnerDutyDao> findAllByStatus(String status, PageDto page);

    List<PartnerDutyDao> findAllByPartnerIdAndPartnerShiftIdsAndStatus(Integer partnerId, List<Integer> partnerShiftIds,String status);
}