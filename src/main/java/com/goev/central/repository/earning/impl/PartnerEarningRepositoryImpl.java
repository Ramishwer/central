package com.goev.central.repository.earning.impl;

import com.goev.central.dao.earning.PartnerEarningDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.repository.earning.PartnerEarningRepository;
import com.goev.central.utilities.EventExecutorUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.goev.record.central.tables.PartnerEarning.PARTNER_EARNING;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerEarningRepositoryImpl implements PartnerEarningRepository {
    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public PartnerEarningDao getPartnerEarningDetails(Integer partnerId, DateTime monthStartDate, DateTime monthEndDate) {

        return context.selectFrom(PARTNER_EARNING)
                .where(PARTNER_EARNING.PARTNER_ID.eq(partnerId))
                .and(PARTNER_EARNING.START_DATE.eq(monthStartDate))
                .and(PARTNER_EARNING.END_DATE.eq(monthEndDate))
                .fetchOneInto(PartnerEarningDao.class);
    }
}
