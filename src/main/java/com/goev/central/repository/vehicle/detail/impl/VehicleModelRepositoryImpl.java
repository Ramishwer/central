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
        return vehicleModel;
    }

    @Override
    public VehicleModelDao update(VehicleModelDao vehicleModel) {
        VehicleModelsRecord vehiclesRecord = context.newRecord(VEHICLE_MODELS, vehicleModel);
        vehiclesRecord.update();
        return vehicleModel;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_MODELS).set(VEHICLE_MODELS.STATE, RecordState.DELETED.name()).where(VEHICLE_MODELS.ID.eq(id)).execute();
    }

    @Override
    public VehicleModelDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_MODELS).where(VEHICLE_MODELS.UUID.eq(uuid)).fetchAnyInto(VehicleModelDao.class);
    }

    @Override
    public VehicleModelDao findById(Integer id) {
        return context.selectFrom(VEHICLE_MODELS).where(VEHICLE_MODELS.ID.eq(id)).fetchAnyInto(VehicleModelDao.class);
    }

    @Override
    public List<VehicleModelDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_MODELS).where(VEHICLE_MODELS.ID.in(ids)).fetchInto(VehicleModelDao.class);
    }

    @Override
    public List<VehicleModelDao> findAll() {
        return context.selectFrom(VEHICLE_MODELS).fetchInto(VehicleModelDao.class);
    }
}
