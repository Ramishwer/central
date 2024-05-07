package com.goev.central.repository.partner.duty.impl;

import com.goev.central.dao.partner.duty.PartnerShiftMappingDao;
import com.goev.central.repository.partner.duty.PartnerShiftMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerShiftMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerShiftMappings.PARTNER_SHIFT_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerShiftMappingRepositoryImpl implements PartnerShiftMappingRepository {

    private final DSLContext context;

    @Override
    public PartnerShiftMappingDao save(PartnerShiftMappingDao partnerShiftMappingDao) {
        PartnerShiftMappingsRecord partnerShiftMappingsRecord = context.newRecord(PARTNER_SHIFT_MAPPINGS, partnerShiftMappingDao);
        partnerShiftMappingsRecord.store();
        partnerShiftMappingDao.setId(partnerShiftMappingsRecord.getId());
        partnerShiftMappingDao.setUuid(partnerShiftMappingsRecord.getUuid());
        return partnerShiftMappingDao;
    }

    @Override
    public PartnerShiftMappingDao update(PartnerShiftMappingDao partnerShiftMappingDao) {
        PartnerShiftMappingsRecord partnerShiftMappingsRecord = context.newRecord(PARTNER_SHIFT_MAPPINGS, partnerShiftMappingDao);
        partnerShiftMappingsRecord.update();
        return partnerShiftMappingDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_SHIFT_MAPPINGS).set(PARTNER_SHIFT_MAPPINGS.STATE, RecordState.DELETED.name()).where(PARTNER_SHIFT_MAPPINGS.ID.eq(id)).execute();
    }

    @Override
    public PartnerShiftMappingDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_SHIFT_MAPPINGS).where(PARTNER_SHIFT_MAPPINGS.UUID.eq(uuid)).fetchAnyInto(PartnerShiftMappingDao.class);
    }

    @Override
    public PartnerShiftMappingDao findById(Integer id) {
        return context.selectFrom(PARTNER_SHIFT_MAPPINGS).where(PARTNER_SHIFT_MAPPINGS.ID.eq(id)).fetchAnyInto(PartnerShiftMappingDao.class);
    }

    @Override
    public List<PartnerShiftMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_SHIFT_MAPPINGS).where(PARTNER_SHIFT_MAPPINGS.ID.in(ids)).fetchInto(PartnerShiftMappingDao.class);
    }

    @Override
    public List<PartnerShiftMappingDao> findAll() {
        return context.selectFrom(PARTNER_SHIFT_MAPPINGS).fetchInto(PartnerShiftMappingDao.class);
    }
}
