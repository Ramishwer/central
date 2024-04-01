package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehiclesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Vehicles.VEHICLES;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleRepositoryImpl implements VehicleRepository {
    private final DSLContext context;

    @Override
    public VehicleDao save(VehicleDao vehicle) {
        VehiclesRecord vehiclesRecord = context.newRecord(VEHICLES, vehicle);
        vehiclesRecord.store();
        vehicle.setId(vehiclesRecord.getId());
        vehicle.setUuid(vehiclesRecord.getUuid());
        return vehicle;
    }

    @Override
    public VehicleDao update(VehicleDao vehicle) {
        VehiclesRecord vehiclesRecord = context.newRecord(VEHICLES, vehicle);
        vehiclesRecord.update();
        return vehicle;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLES).set(VEHICLES.STATE, RecordState.DELETED.name()).where(VEHICLES.ID.eq(id)).execute();
    }

    @Override
    public VehicleDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLES).where(VEHICLES.UUID.eq(uuid)).fetchAnyInto(VehicleDao.class);
    }

    @Override
    public VehicleDao findById(Integer id) {
        return context.selectFrom(VEHICLES).where(VEHICLES.ID.eq(id)).fetchAnyInto(VehicleDao.class);
    }

    @Override
    public List<VehicleDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLES).where(VEHICLES.ID.in(ids)).fetchInto(VehicleDao.class);
    }

    @Override
    public List<VehicleDao> findAll() {
        return context.selectFrom(VEHICLES).fetchInto(VehicleDao.class);
    }

    @Override
    public VehicleDao findByPlateNumber(String plateNumber) {
        return context.selectFrom(VEHICLES).where(VEHICLES.PLATE_NUMBER.eq(plateNumber)).fetchAnyInto(VehicleDao.class);
    }
}
