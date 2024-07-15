package com.goev.central.repository.vehicle.document.impl;

import com.goev.central.dao.vehicle.document.VehicleDocumentTypeDao;
import com.goev.central.repository.vehicle.document.VehicleDocumentTypeRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleDocumentTypesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleDocumentTypes.VEHICLE_DOCUMENT_TYPES;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleDocumentTypeRepositoryImpl implements VehicleDocumentTypeRepository {
    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public VehicleDocumentTypeDao save(VehicleDocumentTypeDao vehicleDocumentType) {
        VehicleDocumentTypesRecord vehiclesDocumentTypeRecord = context.newRecord(VEHICLE_DOCUMENT_TYPES, vehicleDocumentType);
        vehiclesDocumentTypeRecord.store();
        vehicleDocumentType.setId(vehiclesDocumentTypeRecord.getId());
        vehicleDocumentType.setUuid(vehiclesDocumentTypeRecord.getUuid());
        vehicleDocumentType.setCreatedBy(vehiclesDocumentTypeRecord.getCreatedBy());
        vehicleDocumentType.setUpdatedBy(vehiclesDocumentTypeRecord.getUpdatedBy());
        vehicleDocumentType.setCreatedOn(vehiclesDocumentTypeRecord.getCreatedOn());
        vehicleDocumentType.setUpdatedOn(vehiclesDocumentTypeRecord.getUpdatedOn());
        vehicleDocumentType.setIsActive(vehiclesDocumentTypeRecord.getIsActive());
        vehicleDocumentType.setState(vehiclesDocumentTypeRecord.getState());
        vehicleDocumentType.setApiSource(vehiclesDocumentTypeRecord.getApiSource());
        vehicleDocumentType.setNotes(vehiclesDocumentTypeRecord.getNotes());

        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("VehicleDocumentTypeSaveEvent", vehicleDocumentType);

        return vehicleDocumentType;
    }

    @Override
    public VehicleDocumentTypeDao update(VehicleDocumentTypeDao vehicleDocumentType) {
        VehicleDocumentTypesRecord vehiclesDocumentTypeRecord = context.newRecord(VEHICLE_DOCUMENT_TYPES, vehicleDocumentType);
        vehiclesDocumentTypeRecord.update();


        vehicleDocumentType.setCreatedBy(vehiclesDocumentTypeRecord.getCreatedBy());
        vehicleDocumentType.setUpdatedBy(vehiclesDocumentTypeRecord.getUpdatedBy());
        vehicleDocumentType.setCreatedOn(vehiclesDocumentTypeRecord.getCreatedOn());
        vehicleDocumentType.setUpdatedOn(vehiclesDocumentTypeRecord.getUpdatedOn());
        vehicleDocumentType.setIsActive(vehiclesDocumentTypeRecord.getIsActive());
        vehicleDocumentType.setState(vehiclesDocumentTypeRecord.getState());
        vehicleDocumentType.setApiSource(vehiclesDocumentTypeRecord.getApiSource());
        vehicleDocumentType.setNotes(vehiclesDocumentTypeRecord.getNotes());
        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("VehicleDocumentTypeUpdateEvent", vehicleDocumentType);
        return vehicleDocumentType;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_DOCUMENT_TYPES)
                .set(VEHICLE_DOCUMENT_TYPES.STATE, RecordState.DELETED.name())
                .where(VEHICLE_DOCUMENT_TYPES.ID.eq(id))
                .and(VEHICLE_DOCUMENT_TYPES.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_DOCUMENT_TYPES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public VehicleDocumentTypeDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_DOCUMENT_TYPES).where(VEHICLE_DOCUMENT_TYPES.UUID.eq(uuid))
                .and(VEHICLE_DOCUMENT_TYPES.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleDocumentTypeDao.class);
    }

    @Override
    public VehicleDocumentTypeDao findById(Integer id) {
        return context.selectFrom(VEHICLE_DOCUMENT_TYPES).where(VEHICLE_DOCUMENT_TYPES.ID.eq(id))
                .and(VEHICLE_DOCUMENT_TYPES.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleDocumentTypeDao.class);
    }

    @Override
    public List<VehicleDocumentTypeDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_DOCUMENT_TYPES).where(VEHICLE_DOCUMENT_TYPES.ID.in(ids)).fetchInto(VehicleDocumentTypeDao.class);
    }

    @Override
    public List<VehicleDocumentTypeDao> findAllActive() {
        return context.selectFrom(VEHICLE_DOCUMENT_TYPES).fetchInto(VehicleDocumentTypeDao.class);
    }


}