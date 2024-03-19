package com.goev.central.repository.vehicle.impl;

import com.goev.central.dao.vehicle.VehicleDao;
import com.goev.central.repository.vehicle.VehicleRepository;
import com.goev.record.central.tables.records.VehiclesRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Vehicles.VEHICLES;

@Repository
@Slf4j
public class VehicleRepositoryImpl implements VehicleRepository {
    @Autowired
    private DSLContext context;
    @Override
    public VehicleDao save(VehicleDao vehicle) {
        VehiclesRecord record =  context.newRecord(VEHICLES,vehicle);
        record.store();
        vehicle.setId(record.getId());
        return vehicle;
    }

    @Override
    public VehicleDao update(VehicleDao vehicle) {
        VehiclesRecord record =  context.newRecord(VEHICLES,vehicle);
        record.update();
        return vehicle;
    }

    @Override
    public void delete(Integer id) {
       context.update(VEHICLES).set(VEHICLES.STATE,"DELETED").where(VEHICLES.ID.eq(id)).execute();
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
}
