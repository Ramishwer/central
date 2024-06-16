package com.goev.central.repository.payout.impl;

import com.goev.central.dao.payout.PayoutModelConfigurationDao;
import com.goev.central.repository.payout.PayoutModelConfigurationRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PayoutModelConfigurationsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PayoutModelConfigurations.PAYOUT_MODEL_CONFIGURATIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class PayoutModelConfigurationRepositoryImpl implements PayoutModelConfigurationRepository {

    private final DSLContext context;

    @Override
    public PayoutModelConfigurationDao save(PayoutModelConfigurationDao payoutModelConfiguration) {
        PayoutModelConfigurationsRecord payoutModelConfigurationsRecord = context.newRecord(PAYOUT_MODEL_CONFIGURATIONS, payoutModelConfiguration);
        payoutModelConfigurationsRecord.store();
        payoutModelConfiguration.setId(payoutModelConfigurationsRecord.getId());
        payoutModelConfiguration.setUuid(payoutModelConfiguration.getUuid());
        payoutModelConfiguration.setCreatedBy(payoutModelConfiguration.getCreatedBy());
        payoutModelConfiguration.setUpdatedBy(payoutModelConfiguration.getUpdatedBy());
        payoutModelConfiguration.setCreatedOn(payoutModelConfiguration.getCreatedOn());
        payoutModelConfiguration.setUpdatedOn(payoutModelConfiguration.getUpdatedOn());
        payoutModelConfiguration.setIsActive(payoutModelConfiguration.getIsActive());
        payoutModelConfiguration.setState(payoutModelConfiguration.getState());
        payoutModelConfiguration.setApiSource(payoutModelConfiguration.getApiSource());
        payoutModelConfiguration.setNotes(payoutModelConfiguration.getNotes());
        return payoutModelConfiguration;
    }

    @Override
    public PayoutModelConfigurationDao update(PayoutModelConfigurationDao payoutModelConfiguration) {
        PayoutModelConfigurationsRecord payoutModelConfigurationsRecord = context.newRecord(PAYOUT_MODEL_CONFIGURATIONS, payoutModelConfiguration);
        payoutModelConfigurationsRecord.update();


        payoutModelConfiguration.setCreatedBy(payoutModelConfigurationsRecord.getCreatedBy());
        payoutModelConfiguration.setUpdatedBy(payoutModelConfigurationsRecord.getUpdatedBy());
        payoutModelConfiguration.setCreatedOn(payoutModelConfigurationsRecord.getCreatedOn());
        payoutModelConfiguration.setUpdatedOn(payoutModelConfigurationsRecord.getUpdatedOn());
        payoutModelConfiguration.setIsActive(payoutModelConfigurationsRecord.getIsActive());
        payoutModelConfiguration.setState(payoutModelConfigurationsRecord.getState());
        payoutModelConfiguration.setApiSource(payoutModelConfigurationsRecord.getApiSource());
        payoutModelConfiguration.setNotes(payoutModelConfigurationsRecord.getNotes());
        return payoutModelConfiguration;
    }

    @Override
    public void delete(Integer id) {
     context.update(PAYOUT_MODEL_CONFIGURATIONS)
     .set(PAYOUT_MODEL_CONFIGURATIONS.STATE,RecordState.DELETED.name())
     .where(PAYOUT_MODEL_CONFIGURATIONS.ID.eq(id))
     .and(PAYOUT_MODEL_CONFIGURATIONS.STATE.eq(RecordState.ACTIVE.name()))
     .and(PAYOUT_MODEL_CONFIGURATIONS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public PayoutModelConfigurationDao findByUUID(String uuid) {
        return context.selectFrom(PAYOUT_MODEL_CONFIGURATIONS).where(PAYOUT_MODEL_CONFIGURATIONS.UUID.eq(uuid))
                .and(PAYOUT_MODEL_CONFIGURATIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PayoutModelConfigurationDao.class);
    }

    @Override
    public PayoutModelConfigurationDao findById(Integer id) {
        return context.selectFrom(PAYOUT_MODEL_CONFIGURATIONS).where(PAYOUT_MODEL_CONFIGURATIONS.ID.eq(id))
                .and(PAYOUT_MODEL_CONFIGURATIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PayoutModelConfigurationDao.class);
    }

    @Override
    public List<PayoutModelConfigurationDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PAYOUT_MODEL_CONFIGURATIONS).where(PAYOUT_MODEL_CONFIGURATIONS.ID.in(ids)).fetchInto(PayoutModelConfigurationDao.class);
    }

    @Override
    public List<PayoutModelConfigurationDao> findAllActive() {
        return context.selectFrom(PAYOUT_MODEL_CONFIGURATIONS).fetchInto(PayoutModelConfigurationDao.class);
    }
}
