package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleManufacturerDao;
import com.goev.central.repository.vehicle.detail.VehicleManufacturerRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleManufacturersRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleManufacturers.VEHICLE_MANUFACTURERS;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleManufacturerRepositoryImpl implements VehicleManufacturerRepository {
    private final DSLContext context;

    @Override
    public VehicleManufacturerDao save(VehicleManufacturerDao manufacturer) {
        VehicleManufacturersRecord vehicleManufacturersRecord = context.newRecord(VEHICLE_MANUFACTURERS, manufacturer);
        vehicleManufacturersRecord.store();
        manufacturer.setId(vehicleManufacturersRecord.getId());
        manufacturer.setUuid(vehicleManufacturersRecord.getUuid());
        return manufacturer;
    }

    @Override
    public VehicleManufacturerDao update(VehicleManufacturerDao manufacturer) {
        VehicleManufacturersRecord vehicleManufacturersRecord = context.newRecord(VEHICLE_MANUFACTURERS, manufacturer);
        vehicleManufacturersRecord.update();
        return manufacturer;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_MANUFACTURERS).set(VEHICLE_MANUFACTURERS.STATE, RecordState.DELETED.name()).where(VEHICLE_MANUFACTURERS.ID.eq(id)).execute();
    }

    @Override
    public VehicleManufacturerDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_MANUFACTURERS).where(VEHICLE_MANUFACTURERS.UUID.eq(uuid)).fetchAnyInto(VehicleManufacturerDao.class);
    }

    @Override
    public VehicleManufacturerDao findById(Integer id) {
        return context.selectFrom(VEHICLE_MANUFACTURERS).where(VEHICLE_MANUFACTURERS.ID.eq(id)).fetchAnyInto(VehicleManufacturerDao.class);
    }

    @Override
    public List<VehicleManufacturerDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_MANUFACTURERS).where(VEHICLE_MANUFACTURERS.ID.in(ids)).fetchInto(VehicleManufacturerDao.class);
    }

    @Override
    public List<VehicleManufacturerDao> findAll() {
        return context.selectFrom(VEHICLE_MANUFACTURERS).fetchInto(VehicleManufacturerDao.class);
    }
}
