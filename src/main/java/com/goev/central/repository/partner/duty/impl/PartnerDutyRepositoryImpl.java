package com.goev.central.repository.partner.duty.impl;

import com.goev.central.dao.partner.duty.PartnerDutyDao;
import com.goev.central.repository.partner.duty.PartnerDutyRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerDutiesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerDuties.PARTNER_DUTIES;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerDutyRepositoryImpl implements PartnerDutyRepository {

    private final DSLContext context;

    @Override
    public PartnerDutyDao save(PartnerDutyDao partnerDutyDao) {
        PartnerDutiesRecord partnerDutiesRecord = context.newRecord(PARTNER_DUTIES, partnerDutyDao);
        partnerDutiesRecord.store();
        partnerDutyDao.setId(partnerDutiesRecord.getId());
        partnerDutyDao.setUuid(partnerDutiesRecord.getUuid());
        return partnerDutyDao;
    }

    @Override
    public PartnerDutyDao update(PartnerDutyDao partnerDutyDao) {
        PartnerDutiesRecord partnerDutiesRecord = context.newRecord(PARTNER_DUTIES, partnerDutyDao);
        partnerDutiesRecord.update();
        return partnerDutyDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_DUTIES).set(PARTNER_DUTIES.STATE, RecordState.DELETED.name()).where(PARTNER_DUTIES.ID.eq(id)).execute();
    }

    @Override
    public PartnerDutyDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_DUTIES).where(PARTNER_DUTIES.UUID.eq(uuid)).fetchAnyInto(PartnerDutyDao.class);
    }

    @Override
    public PartnerDutyDao findById(Integer id) {
        return context.selectFrom(PARTNER_DUTIES).where(PARTNER_DUTIES.ID.eq(id)).fetchAnyInto(PartnerDutyDao.class);
    }

    @Override
    public List<PartnerDutyDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_DUTIES).where(PARTNER_DUTIES.ID.in(ids)).fetchInto(PartnerDutyDao.class);
    }

    @Override
    public List<PartnerDutyDao> findAll() {
        return context.selectFrom(PARTNER_DUTIES).fetchInto(PartnerDutyDao.class);
    }


    @Override
    public List<PartnerDutyDao> findAllByPartnerId(Integer id) {
        return context.selectFrom(PARTNER_DUTIES).where(PARTNER_DUTIES.PARTNER_ID.eq(id)).fetchInto(PartnerDutyDao.class);
    }
}
