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
        return payoutModelConfiguration;
    }

    @Override
    public PayoutModelConfigurationDao update(PayoutModelConfigurationDao payoutModelConfiguration) {
        PayoutModelConfigurationsRecord payoutModelConfigurationsRecord = context.newRecord(PAYOUT_MODEL_CONFIGURATIONS, payoutModelConfiguration);
        payoutModelConfigurationsRecord.update();
        return payoutModelConfiguration;
    }

    @Override
    public void delete(Integer id) {
        context.update(PAYOUT_MODEL_CONFIGURATIONS).set(PAYOUT_MODEL_CONFIGURATIONS.STATE, RecordState.DELETED.name()).where(PAYOUT_MODEL_CONFIGURATIONS.ID.eq(id)).execute();
    }

    @Override
    public PayoutModelConfigurationDao findByUUID(String uuid) {
        return context.selectFrom(PAYOUT_MODEL_CONFIGURATIONS).where(PAYOUT_MODEL_CONFIGURATIONS.UUID.eq(uuid)).fetchAnyInto(PayoutModelConfigurationDao.class);
    }

    @Override
    public PayoutModelConfigurationDao findById(Integer id) {
        return context.selectFrom(PAYOUT_MODEL_CONFIGURATIONS).where(PAYOUT_MODEL_CONFIGURATIONS.ID.eq(id)).fetchAnyInto(PayoutModelConfigurationDao.class);
    }

    @Override
    public List<PayoutModelConfigurationDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PAYOUT_MODEL_CONFIGURATIONS).where(PAYOUT_MODEL_CONFIGURATIONS.ID.in(ids)).fetchInto(PayoutModelConfigurationDao.class);
    }

    @Override
    public List<PayoutModelConfigurationDao> findAll() {
        return context.selectFrom(PAYOUT_MODEL_CONFIGURATIONS).fetchInto(PayoutModelConfigurationDao.class);
    }
}
