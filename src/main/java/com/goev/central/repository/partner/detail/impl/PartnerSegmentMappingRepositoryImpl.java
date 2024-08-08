package com.goev.central.repository.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerSegmentMappingDao;
import com.goev.central.repository.partner.detail.PartnerSegmentMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerSegmentMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerSegmentMappings.PARTNER_SEGMENT_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerSegmentMappingRepositoryImpl implements PartnerSegmentMappingRepository {

    private final DSLContext context;

    @Override
    public PartnerSegmentMappingDao save(PartnerSegmentMappingDao partnerSegmentMappingDao) {
        PartnerSegmentMappingsRecord partnerSegmentMappingsRecord = context.newRecord(PARTNER_SEGMENT_MAPPINGS, partnerSegmentMappingDao);
        partnerSegmentMappingsRecord.store();
        partnerSegmentMappingDao.setId(partnerSegmentMappingsRecord.getId());
        partnerSegmentMappingDao.setUuid(partnerSegmentMappingsRecord.getUuid());
        partnerSegmentMappingDao.setCreatedBy(partnerSegmentMappingsRecord.getCreatedBy());
        partnerSegmentMappingDao.setUpdatedBy(partnerSegmentMappingsRecord.getUpdatedBy());
        partnerSegmentMappingDao.setCreatedOn(partnerSegmentMappingsRecord.getCreatedOn());
        partnerSegmentMappingDao.setUpdatedOn(partnerSegmentMappingsRecord.getUpdatedOn());
        partnerSegmentMappingDao.setIsActive(partnerSegmentMappingsRecord.getIsActive());
        partnerSegmentMappingDao.setState(partnerSegmentMappingsRecord.getState());
        partnerSegmentMappingDao.setApiSource(partnerSegmentMappingsRecord.getApiSource());
        partnerSegmentMappingDao.setNotes(partnerSegmentMappingsRecord.getNotes());
        return partnerSegmentMappingDao;
    }

    @Override
    public PartnerSegmentMappingDao update(PartnerSegmentMappingDao partnerSegmentMappingDao) {
        PartnerSegmentMappingsRecord partnerSegmentMappingsRecord = context.newRecord(PARTNER_SEGMENT_MAPPINGS, partnerSegmentMappingDao);
        partnerSegmentMappingsRecord.update();


        partnerSegmentMappingDao.setCreatedBy(partnerSegmentMappingsRecord.getCreatedBy());
        partnerSegmentMappingDao.setUpdatedBy(partnerSegmentMappingsRecord.getUpdatedBy());
        partnerSegmentMappingDao.setCreatedOn(partnerSegmentMappingsRecord.getCreatedOn());
        partnerSegmentMappingDao.setUpdatedOn(partnerSegmentMappingsRecord.getUpdatedOn());
        partnerSegmentMappingDao.setIsActive(partnerSegmentMappingsRecord.getIsActive());
        partnerSegmentMappingDao.setState(partnerSegmentMappingsRecord.getState());
        partnerSegmentMappingDao.setApiSource(partnerSegmentMappingsRecord.getApiSource());
        partnerSegmentMappingDao.setNotes(partnerSegmentMappingsRecord.getNotes());
        return partnerSegmentMappingDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_SEGMENT_MAPPINGS)
                .set(PARTNER_SEGMENT_MAPPINGS.STATE, RecordState.DELETED.name())
                .where(PARTNER_SEGMENT_MAPPINGS.ID.eq(id))
                .and(PARTNER_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerSegmentMappingDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_SEGMENT_MAPPINGS).where(PARTNER_SEGMENT_MAPPINGS.UUID.eq(uuid))
                .and(PARTNER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerSegmentMappingDao.class);
    }

    @Override
    public PartnerSegmentMappingDao findById(Integer id) {
        return context.selectFrom(PARTNER_SEGMENT_MAPPINGS).where(PARTNER_SEGMENT_MAPPINGS.ID.eq(id))
                .and(PARTNER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerSegmentMappingDao.class);
    }

    @Override
    public List<PartnerSegmentMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_SEGMENT_MAPPINGS).where(PARTNER_SEGMENT_MAPPINGS.ID.in(ids)).fetchInto(PartnerSegmentMappingDao.class);
    }

    @Override
    public List<PartnerSegmentMappingDao> findAllActive() {
        return context.selectFrom(PARTNER_SEGMENT_MAPPINGS)
                .where(PARTNER_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerSegmentMappingDao.class);
    }

    @Override
    public List<PartnerSegmentMappingDao> findAllBySegmentId(Integer partnerSegmentId) {
        return context.selectFrom(PARTNER_SEGMENT_MAPPINGS).where(PARTNER_SEGMENT_MAPPINGS.PARTNER_SEGMENT_ID.eq(partnerSegmentId))
                .and(PARTNER_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerSegmentMappingDao.class);
    }


    @Override
    public List<PartnerSegmentMappingDao> findAllPartnerBySegmentId(Integer partnerSegmentId) {
        return context.selectFrom(PARTNER_SEGMENT_MAPPINGS).where(PARTNER_SEGMENT_MAPPINGS.PARTNER_SEGMENT_ID.eq(partnerSegmentId))
                .and(PARTNER_SEGMENT_MAPPINGS.PARTNER_ID.isNotNull())
                .and(PARTNER_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerSegmentMappingDao.class);
    }

    @Override
    public List<PartnerSegmentMappingDao> findAllVehicleSegmentBySegmentId(Integer partnerSegmentId) {
        return context.selectFrom(PARTNER_SEGMENT_MAPPINGS).where(PARTNER_SEGMENT_MAPPINGS.PARTNER_SEGMENT_ID.eq(partnerSegmentId))
                .and(PARTNER_SEGMENT_MAPPINGS.VEHICLE_SEGMENT_ID.isNotNull())
                .and(PARTNER_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerSegmentMappingDao.class);
    }
}
