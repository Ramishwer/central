package com.goev.central.repository.partner.payout;

import com.goev.central.dao.partner.payout.PartnerPayoutDao;
import com.goev.central.dao.partner.payout.PartnerPayoutTransactionDao;
import com.goev.central.dto.common.FilterDto;
import com.goev.central.dto.common.PageDto;
import org.joda.time.DateTime;

import java.util.List;

public interface PartnerPayoutRepository {
    PartnerPayoutDao save(PartnerPayoutDao partner);

    PartnerPayoutDao update(PartnerPayoutDao partner);

    void delete(Integer id);

    PartnerPayoutDao findByUUID(String uuid);

    PartnerPayoutDao findById(Integer id);

    List<PartnerPayoutDao> findAllByIds(List<Integer> ids);

    List<PartnerPayoutDao> findAllActive();

    List<PartnerPayoutDao> findAllByPartnerId(Integer id);

    List<PartnerPayoutDao> findAllByStatus(String status, PageDto page, FilterDto filter);

    List<PartnerPayoutDao> findAllByStatus(String status, PageDto page);

    PartnerPayoutDao findByPartnerIdAndStartAndEndTime(Integer partnerId, DateTime start, DateTime end);

    List<PartnerPayoutDao> findAllInProgressPayouts();

}