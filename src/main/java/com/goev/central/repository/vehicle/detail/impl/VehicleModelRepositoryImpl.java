package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleModelDao;
import com.goev.central.repository.vehicle.detail.VehicleModelRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleModelsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleModels.VEHICLE_MODELS;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleModelRepositoryImpl implements VehicleModelRepository {
    private final DSLContext context;

    @Override
    public VehicleModelDao save(VehicleModelDao vehicleModel) {
        VehicleModelsRecord vehicleModelsRecord = context.newRecord(VEHICLE_MODELS, vehicleModel);
        vehicleModelsRecord.store();
        vehicleModel.setId(vehicleModelsRecord.getId());
        vehicleModel.setUuid(vehicleModel.getUuid());
        vehicleModel.setCreatedBy(vehicleModel.getCreatedBy());
        vehicleModel.setUpdatedBy(vehicleModel.getUpdatedBy());
        vehicleModel.setCreatedOn(vehicleModel.getCreatedOn());
        vehicleModel.setUpdatedOn(vehicleModel.getUpdatedOn());
        vehicleModel.setIsActive(vehicleModel.getIsActive());
        vehicleModel.setState(vehicleModel.getState());
        vehicleModel.setApiSource(vehicleModel.getApiSource());
        vehicleModel.setNotes(vehicleModel.getNotes());
        return vehicleModel;
    }

    @Override
    public VehicleModelDao update(VehicleModelDao vehicleModel) {
        VehicleModelsRecord vehiclesRecord = context.newRecord(VEHICLE_MODELS, vehicleModel);
        vehiclesRecord.update();


        vehicleModel.setCreatedBy(vehiclesRecord.getCreatedBy());
        vehicleModel.setUpdatedBy(vehiclesRecord.getUpdatedBy());
        vehicleModel.setCreatedOn(vehiclesRecord.getCreatedOn());
        vehicleModel.setUpdatedOn(vehiclesRecord.getUpdatedOn());
        vehicleModel.setIsActive(vehiclesRecord.getIsActive());
        vehicleModel.setState(vehiclesRecord.getState());
        vehicleModel.setApiSource(vehiclesRecord.getApiSource());
        vehicleModel.setNotes(vehiclesRecord.getNotes());
        return vehicleModel;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_MODELS)
                .set(VEHICLE_MODELS.STATE, RecordState.DELETED.name())
                .where(VEHICLE_MODELS.ID.eq(id))
                .and(VEHICLE_MODELS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_MODELS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public VehicleModelDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_MODELS).where(VEHICLE_MODELS.UUID.eq(uuid))
                .and(VEHICLE_MODELS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleModelDao.class);
    }

    @Override
    public VehicleModelDao findById(Integer id) {
        return context.selectFrom(VEHICLE_MODELS).where(VEHICLE_MODELS.ID.eq(id))
                .and(VEHICLE_MODELS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleModelDao.class);
    }

    @Override
    public List<VehicleModelDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_MODELS).where(VEHICLE_MODELS.ID.in(ids)).fetchInto(VehicleModelDao.class);
    }

    @Override
    public List<VehicleModelDao> findAllActive() {
        return context.selectFrom(VEHICLE_MODELS).fetchInto(VehicleModelDao.class);
    }
}
