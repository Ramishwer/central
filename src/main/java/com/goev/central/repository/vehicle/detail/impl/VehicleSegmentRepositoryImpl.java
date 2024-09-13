package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleSegmentDao;
import com.goev.central.repository.vehicle.detail.VehicleSegmentRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleSegmentsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerSegments.PARTNER_SEGMENTS;
import static com.goev.record.central.tables.VehicleSegments.VEHICLE_SEGMENTS;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleSegmentRepositoryImpl implements VehicleSegmentRepository {
    private final DSLContext context;

    @Override
    public VehicleSegmentDao save(VehicleSegmentDao segment) {
        VehicleSegmentsRecord vehicleSegmentsRecord = context.newRecord(VEHICLE_SEGMENTS, segment);
        vehicleSegmentsRecord.store();
        segment.setId(vehicleSegmentsRecord.getId());
        segment.setUuid(vehicleSegmentsRecord.getUuid());
        segment.setCreatedBy(vehicleSegmentsRecord.getCreatedBy());
        segment.setUpdatedBy(vehicleSegmentsRecord.getUpdatedBy());
        segment.setCreatedOn(vehicleSegmentsRecord.getCreatedOn());
        segment.setUpdatedOn(vehicleSegmentsRecord.getUpdatedOn());
        segment.setIsActive(vehicleSegmentsRecord.getIsActive());
        segment.setState(vehicleSegmentsRecord.getState());
        segment.setApiSource(vehicleSegmentsRecord.getApiSource());
        segment.setNotes(vehicleSegmentsRecord.getNotes());
        return segment;
    }

    @Override
    public VehicleSegmentDao update(VehicleSegmentDao segment) {
        VehicleSegmentsRecord vehicleSegmentsRecord = context.newRecord(VEHICLE_SEGMENTS, segment);
        vehicleSegmentsRecord.update();


        segment.setCreatedBy(vehicleSegmentsRecord.getCreatedBy());
        segment.setUpdatedBy(vehicleSegmentsRecord.getUpdatedBy());
        segment.setCreatedOn(vehicleSegmentsRecord.getCreatedOn());
        segment.setUpdatedOn(vehicleSegmentsRecord.getUpdatedOn());
        segment.setIsActive(vehicleSegmentsRecord.getIsActive());
        segment.setState(vehicleSegmentsRecord.getState());
        segment.setApiSource(vehicleSegmentsRecord.getApiSource());
        segment.setNotes(vehicleSegmentsRecord.getNotes());
        return segment;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_SEGMENTS)
                .set(VEHICLE_SEGMENTS.STATE, RecordState.DELETED.name())
                .where(VEHICLE_SEGMENTS.ID.eq(id))
                .and(VEHICLE_SEGMENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_SEGMENTS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public VehicleSegmentDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_SEGMENTS).where(VEHICLE_SEGMENTS.UUID.eq(uuid))
                .and(VEHICLE_SEGMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleSegmentDao.class);
    }

    @Override
    public VehicleSegmentDao findById(Integer id) {
        return context.selectFrom(VEHICLE_SEGMENTS).where(VEHICLE_SEGMENTS.ID.eq(id))
                .and(VEHICLE_SEGMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleSegmentDao.class);
    }

    @Override
    public List<VehicleSegmentDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_SEGMENTS).where(VEHICLE_SEGMENTS.ID.in(ids)).fetchInto(VehicleSegmentDao.class);
    }

    @Override
    public List<VehicleSegmentDao> findAllActive() {
        return context.selectFrom(VEHICLE_SEGMENTS)
                .where(VEHICLE_SEGMENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_SEGMENTS.IS_ACTIVE.eq(true))
                .fetchInto(VehicleSegmentDao.class);
    }
}
