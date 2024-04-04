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
        return vehicleFinancer;
    }

    @Override
    public VehicleFinancerDao update(VehicleFinancerDao vehicleFinancer) {
        VehicleFinancersRecord vehicleFinancersRecord = context.newRecord(VEHICLE_FINANCERS, vehicleFinancer);
        vehicleFinancersRecord.update();
        return vehicleFinancer;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_FINANCERS).set(VEHICLE_FINANCERS.STATE, RecordState.DELETED.name()).where(VEHICLE_FINANCERS.ID.eq(id)).execute();
    }

    @Override
    public VehicleFinancerDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_FINANCERS).where(VEHICLE_FINANCERS.UUID.eq(uuid)).fetchAnyInto(VehicleFinancerDao.class);
    }

    @Override
    public VehicleFinancerDao findById(Integer id) {
        return context.selectFrom(VEHICLE_FINANCERS).where(VEHICLE_FINANCERS.ID.eq(id)).fetchAnyInto(VehicleFinancerDao.class);
    }

    @Override
    public List<VehicleFinancerDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_FINANCERS).where(VEHICLE_FINANCERS.ID.in(ids)).fetchInto(VehicleFinancerDao.class);
    }

    @Override
    public List<VehicleFinancerDao> findAll() {
        return context.selectFrom(VEHICLE_FINANCERS).fetchInto(VehicleFinancerDao.class);
    }
}
