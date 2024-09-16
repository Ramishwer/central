package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleSegmentMappingDao;
import com.goev.central.repository.vehicle.detail.VehicleSegmentMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleSegmentMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleSegmentMappings.VEHICLE_SEGMENT_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class VehicleSegmentMappingRepositoryImpl implements VehicleSegmentMappingRepository {

    private final DSLContext context;

    @Override
    public VehicleSegmentMappingDao save(VehicleSegmentMappingDao vehicleSegmentMappingDao) {
        VehicleSegmentMappingsRecord vehicleSegmentMappingsRecord = context.newRecord(VEHICLE_SEGMENT_MAPPINGS, vehicleSegmentMappingDao);
        vehicleSegmentMappingsRecord.store();
        vehicleSegmentMappingDao.setId(vehicleSegmentMappingsRecord.getId());
        vehicleSegmentMappingDao.setUuid(vehicleSegmentMappingsRecord.getUuid());
        vehicleSegmentMappingDao.setCreatedBy(vehicleSegmentMappingsRecord.getCreatedBy());
        vehicleSegmentMappingDao.setUpdatedBy(vehicleSegmentMappingsRecord.getUpdatedBy());
        vehicleSegmentMappingDao.setCreatedOn(vehicleSegmentMappingsRecord.getCreatedOn());
        vehicleSegmentMappingDao.setUpdatedOn(vehicleSegmentMappingsRecord.getUpdatedOn());
        vehicleSegmentMappingDao.setIsActive(vehicleSegmentMappingsRecord.getIsActive());
        vehicleSegmentMappingDao.setState(vehicleSegmentMappingsRecord.getState());
        vehicleSegmentMappingDao.setApiSource(vehicleSegmentMappingsRecord.getApiSource());
        vehicleSegmentMappingDao.setNotes(vehicleSegmentMappingsRecord.getNotes());
        return vehicleSegmentMappingDao;
    }

    @Override
    public VehicleSegmentMappingDao update(VehicleSegmentMappingDao vehicleSegmentMappingDao) {
        VehicleSegmentMappingsRecord vehicleSegmentMappingsRecord = context.newRecord(VEHICLE_SEGMENT_MAPPINGS, vehicleSegmentMappingDao);
        vehicleSegmentMappingsRecord.update();


        vehicleSegmentMappingDao.setCreatedBy(vehicleSegmentMappingsRecord.getCreatedBy());
        vehicleSegmentMappingDao.setUpdatedBy(vehicleSegmentMappingsRecord.getUpdatedBy());
        vehicleSegmentMappingDao.setCreatedOn(vehicleSegmentMappingsRecord.getCreatedOn());
        vehicleSegmentMappingDao.setUpdatedOn(vehicleSegmentMappingsRecord.getUpdatedOn());
        vehicleSegmentMappingDao.setIsActive(vehicleSegmentMappingsRecord.getIsActive());
        vehicleSegmentMappingDao.setState(vehicleSegmentMappingsRecord.getState());
        vehicleSegmentMappingDao.setApiSource(vehicleSegmentMappingsRecord.getApiSource());
        vehicleSegmentMappingDao.setNotes(vehicleSegmentMappingsRecord.getNotes());
        return vehicleSegmentMappingDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_SEGMENT_MAPPINGS)
                .set(VEHICLE_SEGMENT_MAPPINGS.STATE, RecordState.DELETED.name())
                .where(VEHICLE_SEGMENT_MAPPINGS.ID.eq(id))
                .and(VEHICLE_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public VehicleSegmentMappingDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_SEGMENT_MAPPINGS).where(VEHICLE_SEGMENT_MAPPINGS.UUID.eq(uuid))
                .and(VEHICLE_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleSegmentMappingDao.class);
    }

    @Override
    public VehicleSegmentMappingDao findById(Integer id) {
        return context.selectFrom(VEHICLE_SEGMENT_MAPPINGS).where(VEHICLE_SEGMENT_MAPPINGS.ID.eq(id))
                .and(VEHICLE_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleSegmentMappingDao.class);
    }

    @Override
    public List<VehicleSegmentMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_SEGMENT_MAPPINGS).where(VEHICLE_SEGMENT_MAPPINGS.ID.in(ids)).fetchInto(VehicleSegmentMappingDao.class);
    }

    @Override
    public List<VehicleSegmentMappingDao> findAllActive() {
        return context.selectFrom(VEHICLE_SEGMENT_MAPPINGS)
                .where(VEHICLE_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(VehicleSegmentMappingDao.class);
    }

    @Override
    public List<VehicleSegmentMappingDao> findAllBySegmentId(Integer vehicleSegmentId) {
        return context.selectFrom(VEHICLE_SEGMENT_MAPPINGS).where(VEHICLE_SEGMENT_MAPPINGS.VEHICLE_SEGMENT_ID.eq(vehicleSegmentId))
                .and(VEHICLE_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(VehicleSegmentMappingDao.class);
    }

    @Override
    public List<VehicleSegmentMappingDao> findAllByVehicleId(Integer vehicleId) {
        return context.selectFrom(VEHICLE_SEGMENT_MAPPINGS)
                .where(VEHICLE_SEGMENT_MAPPINGS.VEHICLE_ID.eq(vehicleId))
                .and(VEHICLE_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(VehicleSegmentMappingDao.class);
    }
}
