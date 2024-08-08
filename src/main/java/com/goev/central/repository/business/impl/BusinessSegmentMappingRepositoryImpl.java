package com.goev.central.repository.business.impl;

import com.goev.central.dao.business.BusinessSegmentMappingDao;
import com.goev.central.repository.business.BusinessSegmentMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BusinessSegmentMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BusinessSegmentMappings.BUSINESS_SEGMENT_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class BusinessSegmentMappingRepositoryImpl implements BusinessSegmentMappingRepository {

    private final DSLContext context;

    @Override
    public BusinessSegmentMappingDao save(BusinessSegmentMappingDao businessSegmentMappingDao) {
        BusinessSegmentMappingsRecord businessSegmentMappingsRecord = context.newRecord(BUSINESS_SEGMENT_MAPPINGS, businessSegmentMappingDao);
        businessSegmentMappingsRecord.store();
        businessSegmentMappingDao.setId(businessSegmentMappingsRecord.getId());
        businessSegmentMappingDao.setUuid(businessSegmentMappingsRecord.getUuid());
        businessSegmentMappingDao.setCreatedBy(businessSegmentMappingsRecord.getCreatedBy());
        businessSegmentMappingDao.setUpdatedBy(businessSegmentMappingsRecord.getUpdatedBy());
        businessSegmentMappingDao.setCreatedOn(businessSegmentMappingsRecord.getCreatedOn());
        businessSegmentMappingDao.setUpdatedOn(businessSegmentMappingsRecord.getUpdatedOn());
        businessSegmentMappingDao.setIsActive(businessSegmentMappingsRecord.getIsActive());
        businessSegmentMappingDao.setState(businessSegmentMappingsRecord.getState());
        businessSegmentMappingDao.setApiSource(businessSegmentMappingsRecord.getApiSource());
        businessSegmentMappingDao.setNotes(businessSegmentMappingsRecord.getNotes());
        return businessSegmentMappingDao;
    }

    @Override
    public BusinessSegmentMappingDao update(BusinessSegmentMappingDao businessSegmentMappingDao) {
        BusinessSegmentMappingsRecord businessSegmentMappingsRecord = context.newRecord(BUSINESS_SEGMENT_MAPPINGS, businessSegmentMappingDao);
        businessSegmentMappingsRecord.update();


        businessSegmentMappingDao.setCreatedBy(businessSegmentMappingsRecord.getCreatedBy());
        businessSegmentMappingDao.setUpdatedBy(businessSegmentMappingsRecord.getUpdatedBy());
        businessSegmentMappingDao.setCreatedOn(businessSegmentMappingsRecord.getCreatedOn());
        businessSegmentMappingDao.setUpdatedOn(businessSegmentMappingsRecord.getUpdatedOn());
        businessSegmentMappingDao.setIsActive(businessSegmentMappingsRecord.getIsActive());
        businessSegmentMappingDao.setState(businessSegmentMappingsRecord.getState());
        businessSegmentMappingDao.setApiSource(businessSegmentMappingsRecord.getApiSource());
        businessSegmentMappingDao.setNotes(businessSegmentMappingsRecord.getNotes());
        return businessSegmentMappingDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BUSINESS_SEGMENT_MAPPINGS)
                .set(BUSINESS_SEGMENT_MAPPINGS.STATE, RecordState.DELETED.name())
                .where(BUSINESS_SEGMENT_MAPPINGS.ID.eq(id))
                .and(BUSINESS_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BUSINESS_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public BusinessSegmentMappingDao findByUUID(String uuid) {
        return context.selectFrom(BUSINESS_SEGMENT_MAPPINGS).where(BUSINESS_SEGMENT_MAPPINGS.UUID.eq(uuid))
                .and(BUSINESS_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BusinessSegmentMappingDao.class);
    }

    @Override
    public BusinessSegmentMappingDao findById(Integer id) {
        return context.selectFrom(BUSINESS_SEGMENT_MAPPINGS).where(BUSINESS_SEGMENT_MAPPINGS.ID.eq(id))
                .and(BUSINESS_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BusinessSegmentMappingDao.class);
    }

    @Override
    public List<BusinessSegmentMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BUSINESS_SEGMENT_MAPPINGS).where(BUSINESS_SEGMENT_MAPPINGS.ID.in(ids)).fetchInto(BusinessSegmentMappingDao.class);
    }

    @Override
    public List<BusinessSegmentMappingDao> findAllActive() {
        return context.selectFrom(BUSINESS_SEGMENT_MAPPINGS)
                .where(BUSINESS_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BUSINESS_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(BusinessSegmentMappingDao.class);
    }

    @Override
    public List<BusinessSegmentMappingDao> findAllBySegmentId(Integer businessSegmentId) {
        return context.selectFrom(BUSINESS_SEGMENT_MAPPINGS).where(BUSINESS_SEGMENT_MAPPINGS.BUSINESS_SEGMENT_ID.eq(businessSegmentId))
                .and(BUSINESS_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BUSINESS_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(BusinessSegmentMappingDao.class);
    }

    @Override
    public List<BusinessSegmentMappingDao> findAllPartnerSegmentBySegmentId(Integer businessSegmentId) {
        return context.selectFrom(BUSINESS_SEGMENT_MAPPINGS).where(BUSINESS_SEGMENT_MAPPINGS.BUSINESS_SEGMENT_ID.eq(businessSegmentId))
                .and(BUSINESS_SEGMENT_MAPPINGS.PARTNER_SEGMENT_ID.isNotNull())
                .and(BUSINESS_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BUSINESS_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(BusinessSegmentMappingDao.class);
    }

    @Override
    public List<BusinessSegmentMappingDao> findAllVehicleSegmentBySegmentId(Integer businessSegmentId) {
        return context.selectFrom(BUSINESS_SEGMENT_MAPPINGS).where(BUSINESS_SEGMENT_MAPPINGS.BUSINESS_SEGMENT_ID.eq(businessSegmentId))
                .and(BUSINESS_SEGMENT_MAPPINGS.VEHICLE_SEGMENT_ID.isNotNull())
                .and(BUSINESS_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BUSINESS_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(BusinessSegmentMappingDao.class);
    }
}
