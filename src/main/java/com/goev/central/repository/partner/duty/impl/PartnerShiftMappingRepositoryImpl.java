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
        partnerShiftMappingDao.setCreatedBy(partnerShiftMappingsRecord.getCreatedBy());
        partnerShiftMappingDao.setUpdatedBy(partnerShiftMappingsRecord.getUpdatedBy());
        partnerShiftMappingDao.setCreatedOn(partnerShiftMappingsRecord.getCreatedOn());
        partnerShiftMappingDao.setUpdatedOn(partnerShiftMappingsRecord.getUpdatedOn());
        partnerShiftMappingDao.setIsActive(partnerShiftMappingsRecord.getIsActive());
        partnerShiftMappingDao.setState(partnerShiftMappingsRecord.getState());
        partnerShiftMappingDao.setApiSource(partnerShiftMappingsRecord.getApiSource());
        partnerShiftMappingDao.setNotes(partnerShiftMappingsRecord.getNotes());
        return partnerShiftMappingDao;
    }

    @Override
    public PartnerShiftMappingDao update(PartnerShiftMappingDao partnerShiftMappingDao) {
        PartnerShiftMappingsRecord partnerShiftMappingsRecord = context.newRecord(PARTNER_SHIFT_MAPPINGS, partnerShiftMappingDao);
        partnerShiftMappingsRecord.update();


        partnerShiftMappingDao.setCreatedBy(partnerShiftMappingsRecord.getCreatedBy());
        partnerShiftMappingDao.setUpdatedBy(partnerShiftMappingsRecord.getUpdatedBy());
        partnerShiftMappingDao.setCreatedOn(partnerShiftMappingsRecord.getCreatedOn());
        partnerShiftMappingDao.setUpdatedOn(partnerShiftMappingsRecord.getUpdatedOn());
        partnerShiftMappingDao.setIsActive(partnerShiftMappingsRecord.getIsActive());
        partnerShiftMappingDao.setState(partnerShiftMappingsRecord.getState());
        partnerShiftMappingDao.setApiSource(partnerShiftMappingsRecord.getApiSource());
        partnerShiftMappingDao.setNotes(partnerShiftMappingsRecord.getNotes());
        return partnerShiftMappingDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_SHIFT_MAPPINGS)
                .set(PARTNER_SHIFT_MAPPINGS.STATE, RecordState.DELETED.name())
                .where(PARTNER_SHIFT_MAPPINGS.ID.eq(id))
                .and(PARTNER_SHIFT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_SHIFT_MAPPINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerShiftMappingDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_SHIFT_MAPPINGS).where(PARTNER_SHIFT_MAPPINGS.UUID.eq(uuid))
                .and(PARTNER_SHIFT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerShiftMappingDao.class);
    }

    @Override
    public PartnerShiftMappingDao findById(Integer id) {
        return context.selectFrom(PARTNER_SHIFT_MAPPINGS).where(PARTNER_SHIFT_MAPPINGS.ID.eq(id))
                .and(PARTNER_SHIFT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerShiftMappingDao.class);
    }

    @Override
    public List<PartnerShiftMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_SHIFT_MAPPINGS).where(PARTNER_SHIFT_MAPPINGS.ID.in(ids)).fetchInto(PartnerShiftMappingDao.class);
    }

    @Override
    public List<PartnerShiftMappingDao> findAllActive() {
        return context.selectFrom(PARTNER_SHIFT_MAPPINGS).fetchInto(PartnerShiftMappingDao.class);
    }
}
