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
        manufacturer.setCreatedBy(vehicleManufacturersRecord.getCreatedBy());
        manufacturer.setUpdatedBy(vehicleManufacturersRecord.getUpdatedBy());
        manufacturer.setCreatedOn(vehicleManufacturersRecord.getCreatedOn());
        manufacturer.setUpdatedOn(vehicleManufacturersRecord.getUpdatedOn());
        manufacturer.setIsActive(vehicleManufacturersRecord.getIsActive());
        manufacturer.setState(vehicleManufacturersRecord.getState());
        manufacturer.setApiSource(vehicleManufacturersRecord.getApiSource());
        manufacturer.setNotes(vehicleManufacturersRecord.getNotes());
        return manufacturer;
    }

    @Override
    public VehicleManufacturerDao update(VehicleManufacturerDao manufacturer) {
        VehicleManufacturersRecord vehicleManufacturersRecord = context.newRecord(VEHICLE_MANUFACTURERS, manufacturer);
        vehicleManufacturersRecord.update();


        manufacturer.setCreatedBy(vehicleManufacturersRecord.getCreatedBy());
        manufacturer.setUpdatedBy(vehicleManufacturersRecord.getUpdatedBy());
        manufacturer.setCreatedOn(vehicleManufacturersRecord.getCreatedOn());
        manufacturer.setUpdatedOn(vehicleManufacturersRecord.getUpdatedOn());
        manufacturer.setIsActive(vehicleManufacturersRecord.getIsActive());
        manufacturer.setState(vehicleManufacturersRecord.getState());
        manufacturer.setApiSource(vehicleManufacturersRecord.getApiSource());
        manufacturer.setNotes(vehicleManufacturersRecord.getNotes());
        return manufacturer;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_MANUFACTURERS)
                .set(VEHICLE_MANUFACTURERS.STATE, RecordState.DELETED.name())
                .where(VEHICLE_MANUFACTURERS.ID.eq(id))
                .and(VEHICLE_MANUFACTURERS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_MANUFACTURERS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public VehicleManufacturerDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_MANUFACTURERS).where(VEHICLE_MANUFACTURERS.UUID.eq(uuid))
                .and(VEHICLE_MANUFACTURERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleManufacturerDao.class);
    }

    @Override
    public VehicleManufacturerDao findById(Integer id) {
        return context.selectFrom(VEHICLE_MANUFACTURERS).where(VEHICLE_MANUFACTURERS.ID.eq(id))
                .and(VEHICLE_MANUFACTURERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleManufacturerDao.class);
    }

    @Override
    public List<VehicleManufacturerDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_MANUFACTURERS).where(VEHICLE_MANUFACTURERS.ID.in(ids)).fetchInto(VehicleManufacturerDao.class);
    }

    @Override
    public List<VehicleManufacturerDao> findAllActive() {
        return context.selectFrom(VEHICLE_MANUFACTURERS).fetchInto(VehicleManufacturerDao.class);
    }
}
