package com.goev.central.repository.partner.attendance.impl;

import com.goev.central.dao.partner.attendance.PartnerShiftConfigurationDao;
import com.goev.central.repository.partner.attendance.PartnerShiftConfigurationRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerShiftConfigurationsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerShiftConfigurations.PARTNER_SHIFT_CONFIGURATIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerShiftConfigurationRepositoryImpl implements PartnerShiftConfigurationRepository {

    private final DSLContext context;

    @Override
    public PartnerShiftConfigurationDao save(PartnerShiftConfigurationDao shiftConfiguration) {
        PartnerShiftConfigurationsRecord partnerShiftConfigurationsRecord = context.newRecord(PARTNER_SHIFT_CONFIGURATIONS, shiftConfiguration);
        partnerShiftConfigurationsRecord.store();
        shiftConfiguration.setId(partnerShiftConfigurationsRecord.getId());
        shiftConfiguration.setUuid(partnerShiftConfigurationsRecord.getUuid());
        return shiftConfiguration;
    }

    @Override
    public PartnerShiftConfigurationDao update(PartnerShiftConfigurationDao shiftConfiguration) {
        PartnerShiftConfigurationsRecord partnerShiftConfigurationsRecord = context.newRecord(PARTNER_SHIFT_CONFIGURATIONS, shiftConfiguration);
        partnerShiftConfigurationsRecord.update();
        return shiftConfiguration;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_SHIFT_CONFIGURATIONS).set(PARTNER_SHIFT_CONFIGURATIONS.STATE, RecordState.DELETED.name()).where(PARTNER_SHIFT_CONFIGURATIONS.ID.eq(id)).execute();
    }

    @Override
    public PartnerShiftConfigurationDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_SHIFT_CONFIGURATIONS).where(PARTNER_SHIFT_CONFIGURATIONS.UUID.eq(uuid)).fetchAnyInto(PartnerShiftConfigurationDao.class);
    }

    @Override
    public PartnerShiftConfigurationDao findById(Integer id) {
        return context.selectFrom(PARTNER_SHIFT_CONFIGURATIONS).where(PARTNER_SHIFT_CONFIGURATIONS.ID.eq(id)).fetchAnyInto(PartnerShiftConfigurationDao.class);
    }

    @Override
    public List<PartnerShiftConfigurationDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_SHIFT_CONFIGURATIONS).where(PARTNER_SHIFT_CONFIGURATIONS.ID.in(ids)).fetchInto(PartnerShiftConfigurationDao.class);
    }

    @Override
    public List<PartnerShiftConfigurationDao> findAll() {
        return context.selectFrom(PARTNER_SHIFT_CONFIGURATIONS).fetchInto(PartnerShiftConfigurationDao.class);
    }
}
