package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleDetailDao;
import com.goev.central.repository.vehicle.detail.VehicleDetailRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleDetails.VEHICLE_DETAILS;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleDetailRepositoryImpl implements VehicleDetailRepository {
    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public VehicleDetailDao save(VehicleDetailDao vehicleDetails) {
        VehicleDetailsRecord vehicleDetailsRecord = context.newRecord(VEHICLE_DETAILS, vehicleDetails);
        vehicleDetailsRecord.store();
        vehicleDetails.setId(vehicleDetailsRecord.getId());
        vehicleDetails.setUuid(vehicleDetails.getUuid());
        vehicleDetails.setCreatedBy(vehicleDetails.getCreatedBy());
        vehicleDetails.setUpdatedBy(vehicleDetails.getUpdatedBy());
        vehicleDetails.setCreatedOn(vehicleDetails.getCreatedOn());
        vehicleDetails.setUpdatedOn(vehicleDetails.getUpdatedOn());
        vehicleDetails.setIsActive(vehicleDetails.getIsActive());
        vehicleDetails.setState(vehicleDetails.getState());
        vehicleDetails.setApiSource(vehicleDetails.getApiSource());
        vehicleDetails.setNotes(vehicleDetails.getNotes());

        eventExecutor.fireEvent("VehicleDetailSaveEvent", vehicleDetails);
        return vehicleDetails;
    }

    @Override
    public VehicleDetailDao update(VehicleDetailDao vehicleDetails) {
        VehicleDetailsRecord vehicleDetailsRecord = context.newRecord(VEHICLE_DETAILS, vehicleDetails);
        vehicleDetailsRecord.update();


        vehicleDetails.setCreatedBy(vehicleDetailsRecord.getCreatedBy());
        vehicleDetails.setUpdatedBy(vehicleDetailsRecord.getUpdatedBy());
        vehicleDetails.setCreatedOn(vehicleDetailsRecord.getCreatedOn());
        vehicleDetails.setUpdatedOn(vehicleDetailsRecord.getUpdatedOn());
        vehicleDetails.setIsActive(vehicleDetailsRecord.getIsActive());
        vehicleDetails.setState(vehicleDetailsRecord.getState());
        vehicleDetails.setApiSource(vehicleDetailsRecord.getApiSource());
        vehicleDetails.setNotes(vehicleDetailsRecord.getNotes());
        eventExecutor.fireEvent("VehicleDetailUpdateEvent", vehicleDetails);
        return vehicleDetails;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_DETAILS)
                .set(VEHICLE_DETAILS.STATE, RecordState.DELETED.name())
                .where(VEHICLE_DETAILS.ID.eq(id))
                .and(VEHICLE_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_DETAILS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public VehicleDetailDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_DETAILS).where(VEHICLE_DETAILS.UUID.eq(uuid))
                .and(VEHICLE_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleDetailDao.class);
    }

    @Override
    public VehicleDetailDao findById(Integer id) {
        return context.selectFrom(VEHICLE_DETAILS).where(VEHICLE_DETAILS.ID.eq(id))
                .and(VEHICLE_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleDetailDao.class);
    }

    @Override
    public List<VehicleDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_DETAILS).where(VEHICLE_DETAILS.ID.in(ids)).fetchInto(VehicleDetailDao.class);
    }

    @Override
    public List<VehicleDetailDao> findAllActive() {
        return context.selectFrom(VEHICLE_DETAILS).fetchInto(VehicleDetailDao.class);
    }
}
