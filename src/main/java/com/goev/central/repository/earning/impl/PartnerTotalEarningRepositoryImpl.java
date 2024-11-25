package com.goev.central.repository.earning.impl;

import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dao.earning.PartnerEarningDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.repository.earning.PartnerTotalEarningRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.record.central.tables.PartnerEarning;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import static com.goev.record.central.tables.PartnerEarning.PARTNER_EARNING;


import com.goev.record.central.tables.records.PartnerEarningRecord;


@Slf4j
@Repository
@AllArgsConstructor
public class PartnerTotalEarningRepositoryImpl implements PartnerTotalEarningRepository {
    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public PartnerEarningDao getPartnerEarningDetails(PartnerDao partner, DateTime monthStartDate, DateTime monthEndDate) {

        return context.selectFrom(PARTNER_EARNING)
                .where(PARTNER_EARNING.PARTNER_ID.eq(partner.getId()))
                .and(PARTNER_EARNING.START_DATE.eq(monthStartDate))
                .and(PARTNER_EARNING.END_DATE.eq(monthEndDate))
                .fetchOneInto(PartnerEarningDao.class);
    }


    @Override
    public void savePartnerTotalEarning(PartnerDao partner , EarningRuleDao earningRuleDao, DateTime monthStartDate , DateTime monthEndDate, Float totalFixedEaring , Integer noOfPersentdays , Integer noOfAbsentdays){
        PartnerEarningRecord partnerEarningRecord = context.newRecord(PARTNER_EARNING);
        partnerEarningRecord.setEarningRuleId(earningRuleDao.getRuleId());
        partnerEarningRecord.setPartnerId(partner.getId());
        partnerEarningRecord.setBusinessType(earningRuleDao.getBusinessType());
        partnerEarningRecord.setClientName(earningRuleDao.getClientName());
        partnerEarningRecord.setStartDate(monthStartDate);
        partnerEarningRecord.setEndDate(monthEndDate);
        partnerEarningRecord.setTotalEarning(Double.valueOf(totalFixedEaring));
        partnerEarningRecord.setTransactionType("CREDIT");
        partnerEarningRecord.setTransactionStatus("COMPLETED");
        partnerEarningRecord.setPresentdays(noOfPersentdays);
        partnerEarningRecord.setAbsentdays(noOfAbsentdays);
        partnerEarningRecord.setTotalWorkingDays(earningRuleDao.getCheckValue());
        partnerEarningRecord.store();


        PartnerEarningDao partnerEarningDao = new PartnerEarningDao();
        partnerEarningDao.setEarningRuleId(earningRuleDao.getRuleId());
        partnerEarningDao.setPartnerId(partner.getId());
        partnerEarningDao.setBusinessType(earningRuleDao.getBusinessType());
        partnerEarningDao.setClientName(earningRuleDao.getClientName());
        partnerEarningDao.setStartDate(monthStartDate);
        partnerEarningDao.setEndDate(monthEndDate);
        partnerEarningDao.setTotalEarning(totalFixedEaring);
        partnerEarningDao.setTransactionType("CREDIT");
        partnerEarningDao.setTransactionStatus("COMPLETED");
        partnerEarningDao.setPresentDays(noOfPersentdays);
        partnerEarningDao.setAbsentDays(noOfAbsentdays);
        partnerEarningDao.setTotalWorkingDays(earningRuleDao.getCheckValue());


        if (!"EVENT".equals(RequestContext.getRequestSource())){
            System.out.println("EVENTEVENTEVENTEVENT");
            eventExecutor.fireEvent("PartnerTotalEarningSaveEvent", partnerEarningDao);
        }



    }

    @Override

    public void updatePartnerTotalEarning (PartnerEarningDao partnerEarningDao){
        PartnerEarningRecord partnerEarningRecord = context.newRecord(PARTNER_EARNING,partnerEarningDao);
        partnerEarningRecord.update();

        if (!"EVENT".equals(RequestContext.getRequestSource())){
            System.out.println("EVENTEVENTEVENTEVENT22");
            eventExecutor.fireEvent("PartnerTotalEarningSaveEvent", partnerEarningDao);
        }

    }
}
