package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehiclesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Vehicles.VEHICLES;
import static com.goev.record.central.tables.Vehicles.VEHICLES;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleRepositoryImpl implements VehicleRepository {
    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public VehicleDao save(VehicleDao vehicle) {
        VehiclesRecord vehiclesRecord = context.newRecord(VEHICLES, vehicle);
        vehiclesRecord.store();
        vehicle.setId(vehiclesRecord.getId());
        vehicle.setUuid(vehiclesRecord.getUuid());
        vehicle.setCreatedBy(vehiclesRecord.getCreatedBy());
        vehicle.setUpdatedBy(vehiclesRecord.getUpdatedBy());
        vehicle.setCreatedOn(vehiclesRecord.getCreatedOn());
        vehicle.setUpdatedOn(vehiclesRecord.getUpdatedOn());
        vehicle.setIsActive(vehiclesRecord.getIsActive());
        vehicle.setState(vehiclesRecord.getState());
        vehicle.setApiSource(vehiclesRecord.getApiSource());
        vehicle.setNotes(vehiclesRecord.getNotes());

        eventExecutor.fireEvent("VehicleSaveEvent", vehicle);
        return vehicle;
    }

    @Override
    public VehicleDao update(VehicleDao vehicle) {
        VehiclesRecord vehiclesRecord = context.newRecord(VEHICLES, vehicle);
        vehiclesRecord.update();


        vehicle.setCreatedBy(vehiclesRecord.getCreatedBy());
        vehicle.setUpdatedBy(vehiclesRecord.getUpdatedBy());
        vehicle.setCreatedOn(vehiclesRecord.getCreatedOn());
        vehicle.setUpdatedOn(vehiclesRecord.getUpdatedOn());
        vehicle.setIsActive(vehiclesRecord.getIsActive());
        vehicle.setState(vehiclesRecord.getState());
        vehicle.setApiSource(vehiclesRecord.getApiSource());
        vehicle.setNotes(vehiclesRecord.getNotes());

        eventExecutor.fireEvent("VehicleUpdateEvent", vehicle);
        return vehicle;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLES)
                .set(VEHICLES.STATE, RecordState.DELETED.name())
                .where(VEHICLES.ID.eq(id))
                .and(VEHICLES.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public VehicleDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLES).where(VEHICLES.UUID.eq(uuid))
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleDao.class);
    }

    @Override
    public VehicleDao findById(Integer id) {
        return context.selectFrom(VEHICLES).where(VEHICLES.ID.eq(id))
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleDao.class);
    }

    @Override
    public List<VehicleDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLES).where(VEHICLES.ID.in(ids)).fetchInto(VehicleDao.class);
    }

    @Override
    public List<VehicleDao> findAllActive() {
        return context.selectFrom(VEHICLES).fetchInto(VehicleDao.class);
    }

    @Override
    public VehicleDao findByPlateNumber(String plateNumber) {
        return context.selectFrom(VEHICLES).where(VEHICLES.PLATE_NUMBER.eq(plateNumber)).fetchAnyInto(VehicleDao.class);
    }

    @Override
    public List<VehicleDao> findAllByOnboardingStatus(String onboardingStatus) {
        return context.selectFrom(VEHICLES)
                .where(VEHICLES.ONBOARDING_STATUS.in(onboardingStatus))
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .fetchInto(VehicleDao.class);
    }
}
