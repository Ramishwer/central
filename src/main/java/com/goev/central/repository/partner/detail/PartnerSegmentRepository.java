package com.goev.central.repository.partner.detail;

import com.goev.central.dao.partner.detail.PartnerSegmentDao;

import java.util.List;

public interface PartnerSegmentRepository {
    PartnerSegmentDao save(PartnerSegmentDao segment);

    PartnerSegmentDao update(PartnerSegmentDao segment);

    void delete(Integer id);

    PartnerSegmentDao findByUUID(String uuid);

    PartnerSegmentDao findById(Integer id);

    List<PartnerSegmentDao> findAllByIds(List<Integer> ids);

    List<PartnerSegmentDao> findAllActive();

    List<PartnerSegmentDao> findAllByPartnerId(Integer partnerId);
}