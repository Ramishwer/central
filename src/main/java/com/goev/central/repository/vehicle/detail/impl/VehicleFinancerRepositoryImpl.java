package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleFinancerDao;
import com.goev.central.repository.vehicle.detail.VehicleFinancerRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleFinancersRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleFinancers.VEHICLE_FINANCERS;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleFinancerRepositoryImpl implements VehicleFinancerRepository {
    private final DSLContext context;

    @Override
    public VehicleFinancerDao save(VehicleFinancerDao vehicleFinancer) {
        VehicleFinancersRecord vehicleFinancersRecord = context.newRecord(VEHICLE_FINANCERS, vehicleFinancer);
        vehicleFinancersRecord.store();
        vehicleFinancer.setId(vehicleFinancersRecord.getId());
        vehicleFinancer.setUuid(vehicleFinancer.getUuid());
        vehicleFinancer.setCreatedBy(vehicleFinancer.getCreatedBy());
        vehicleFinancer.setUpdatedBy(vehicleFinancer.getUpdatedBy());
        vehicleFinancer.setCreatedOn(vehicleFinancer.getCreatedOn());
        vehicleFinancer.setUpdatedOn(vehicleFinancer.getUpdatedOn());
        vehicleFinancer.setIsActive(vehicleFinancer.getIsActive());
        vehicleFinancer.setState(vehicleFinancer.getState());
        vehicleFinancer.setApiSource(vehicleFinancer.getApiSource());
        vehicleFinancer.setNotes(vehicleFinancer.getNotes());
        return vehicleFinancer;
    }

    @Override
    public VehicleFinancerDao update(VehicleFinancerDao vehicleFinancer) {
        VehicleFinancersRecord vehicleFinancersRecord = context.newRecord(VEHICLE_FINANCERS, vehicleFinancer);
        vehicleFinancersRecord.update();


        vehicleFinancer.setCreatedBy(vehicleFinancersRecord.getCreatedBy());
        vehicleFinancer.setUpdatedBy(vehicleFinancersRecord.getUpdatedBy());
        vehicleFinancer.setCreatedOn(vehicleFinancersRecord.getCreatedOn());
        vehicleFinancer.setUpdatedOn(vehicleFinancersRecord.getUpdatedOn());
        vehicleFinancer.setIsActive(vehicleFinancersRecord.getIsActive());
        vehicleFinancer.setState(vehicleFinancersRecord.getState());
        vehicleFinancer.setApiSource(vehicleFinancersRecord.getApiSource());
        vehicleFinancer.setNotes(vehicleFinancersRecord.getNotes());
        return vehicleFinancer;
    }

    @Override
    public void delete(Integer id) {
     context.update(VEHICLE_FINANCERS)
     .set(VEHICLE_FINANCERS.STATE,RecordState.DELETED.name())
     .where(VEHICLE_FINANCERS.ID.eq(id))
     .and(VEHICLE_FINANCERS.STATE.eq(RecordState.ACTIVE.name()))
     .and(VEHICLE_FINANCERS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public VehicleFinancerDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_FINANCERS).where(VEHICLE_FINANCERS.UUID.eq(uuid))
                .and(VEHICLE_FINANCERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleFinancerDao.class);
    }

    @Override
    public VehicleFinancerDao findById(Integer id) {
        return context.selectFrom(VEHICLE_FINANCERS).where(VEHICLE_FINANCERS.ID.eq(id))
                .and(VEHICLE_FINANCERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleFinancerDao.class);
    }

    @Override
    public List<VehicleFinancerDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_FINANCERS).where(VEHICLE_FINANCERS.ID.in(ids)).fetchInto(VehicleFinancerDao.class);
    }

    @Override
    public List<VehicleFinancerDao> findAllActive() {
        return context.selectFrom(VEHICLE_FINANCERS).fetchInto(VehicleFinancerDao.class);
    }
}
