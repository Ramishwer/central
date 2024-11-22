package com.goev.central.repository.earning.impl;

import com.goev.central.dao.earning.EarningRuleDao;
import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.repository.earning.PartnerFixedEarningRepository;
import com.goev.central.utilities.EventExecutorUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.springframework.stereotype.Repository;
import com.goev.record.central.tables.records.PartnerFixedEarningTransactionRecord;
import static com.goev.record.central.tables.PartnerFixedEarningTransaction.PARTNER_FIXED_EARNING_TRANSACTION;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerFixedEarningRepositoryImpl implements PartnerFixedEarningRepository {
    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;


    @Override
    public void saveEarningTransaction (PartnerDao partnerDao, EarningRuleDao earningRuleDao , Float fixedEarning, DateTime start){
        PartnerFixedEarningTransactionRecord partnerFixedEarningTransactionRecord = context.newRecord(PARTNER_FIXED_EARNING_TRANSACTION);
        partnerFixedEarningTransactionRecord.setEarningRuleId(earningRuleDao.getRuleId());
        partnerFixedEarningTransactionRecord.setPartnerId(partnerDao.getId());
        partnerFixedEarningTransactionRecord.setBusinessType(earningRuleDao.getBusinessType());
        partnerFixedEarningTransactionRecord.setClientName(earningRuleDao.getClientName());
        partnerFixedEarningTransactionRecord.setTransactionDate(start);
        partnerFixedEarningTransactionRecord.setTransactionAmount(Double.valueOf(fixedEarning));
        partnerFixedEarningTransactionRecord.setTransactionType("CREDIT");
        partnerFixedEarningTransactionRecord.setTransactionStatus("COMPLETED");
        partnerFixedEarningTransactionRecord.setIsActive(true);
        partnerFixedEarningTransactionRecord.setApiSource("CENTRAL");

        partnerFixedEarningTransactionRecord.store();

    }



    @Override
    public Float getTotalFixedEarning(PartnerDao partnerDao, DateTime startMonthDate, DateTime endMonthDate) {
        Long totalTransactionAmount = context
                .select(DSL.sum(PARTNER_FIXED_EARNING_TRANSACTION.TRANSACTION_AMOUNT))
                .from(PARTNER_FIXED_EARNING_TRANSACTION)
                .where(PARTNER_FIXED_EARNING_TRANSACTION.PARTNER_ID.eq(partnerDao.getId())
                        .and(PARTNER_FIXED_EARNING_TRANSACTION.TRANSACTION_DATE.between(startMonthDate, endMonthDate)))
                .fetchOne(0, Long.class);
        Float totalTransactionAmountFloat = totalTransactionAmount != null ? totalTransactionAmount.floatValue() : 0.0f;

        return totalTransactionAmountFloat;
    }

}
