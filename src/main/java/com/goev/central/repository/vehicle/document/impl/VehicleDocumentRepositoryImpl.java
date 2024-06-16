package com.goev.central.repository.vehicle.document.impl;

import com.goev.central.dao.vehicle.document.VehicleDocumentDao;
import com.goev.central.repository.vehicle.document.VehicleDocumentRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleDocumentsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.goev.record.central.tables.VehicleDocuments.VEHICLE_DOCUMENTS;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleDocumentRepositoryImpl implements VehicleDocumentRepository {
    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public VehicleDocumentDao save(VehicleDocumentDao vehicleDocument) {
        VehicleDocumentsRecord vehiclesDocumentRecord = context.newRecord(VEHICLE_DOCUMENTS, vehicleDocument);
        vehiclesDocumentRecord.store();
        vehicleDocument.setId(vehiclesDocumentRecord.getId());
        vehicleDocument.setUuid(vehicleDocument.getUuid());
        vehicleDocument.setCreatedBy(vehicleDocument.getCreatedBy());
        vehicleDocument.setUpdatedBy(vehicleDocument.getUpdatedBy());
        vehicleDocument.setCreatedOn(vehicleDocument.getCreatedOn());
        vehicleDocument.setUpdatedOn(vehicleDocument.getUpdatedOn());
        vehicleDocument.setIsActive(vehicleDocument.getIsActive());
        vehicleDocument.setState(vehicleDocument.getState());
        vehicleDocument.setApiSource(vehicleDocument.getApiSource());
        vehicleDocument.setNotes(vehicleDocument.getNotes());

        if ("API".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("VehicleDocumentSaveEvent", vehicleDocument);

        return vehicleDocument;
    }

    @Override
    public VehicleDocumentDao update(VehicleDocumentDao vehicleDocument) {
        VehicleDocumentsRecord vehiclesDocumentRecord = context.newRecord(VEHICLE_DOCUMENTS, vehicleDocument);
        vehiclesDocumentRecord.update();


        vehicleDocument.setCreatedBy(vehiclesDocumentRecord.getCreatedBy());
        vehicleDocument.setUpdatedBy(vehiclesDocumentRecord.getUpdatedBy());
        vehicleDocument.setCreatedOn(vehiclesDocumentRecord.getCreatedOn());
        vehicleDocument.setUpdatedOn(vehiclesDocumentRecord.getUpdatedOn());
        vehicleDocument.setIsActive(vehiclesDocumentRecord.getIsActive());
        vehicleDocument.setState(vehiclesDocumentRecord.getState());
        vehicleDocument.setApiSource(vehiclesDocumentRecord.getApiSource());
        vehicleDocument.setNotes(vehiclesDocumentRecord.getNotes());

        if ("API".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("VehicleDocumentUpdateEvent", vehicleDocument);
        return vehicleDocument;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_DOCUMENTS)
                .set(VEHICLE_DOCUMENTS.STATE, RecordState.DELETED.name())
                .where(VEHICLE_DOCUMENTS.ID.eq(id))
                .and(VEHICLE_DOCUMENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_DOCUMENTS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public VehicleDocumentDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_DOCUMENTS).where(VEHICLE_DOCUMENTS.UUID.eq(uuid))
                .and(VEHICLE_DOCUMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleDocumentDao.class);
    }

    @Override
    public VehicleDocumentDao findById(Integer id) {
        return context.selectFrom(VEHICLE_DOCUMENTS).where(VEHICLE_DOCUMENTS.ID.eq(id))
                .and(VEHICLE_DOCUMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleDocumentDao.class);
    }

    @Override
    public List<VehicleDocumentDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_DOCUMENTS).where(VEHICLE_DOCUMENTS.ID.in(ids)).fetchInto(VehicleDocumentDao.class);
    }

    @Override
    public List<VehicleDocumentDao> findAllByVehicleId(Integer vehicleId) {
        return context.selectFrom(VEHICLE_DOCUMENTS).where(VEHICLE_DOCUMENTS.VEHICLE_ID.eq(vehicleId))
                .and(VEHICLE_DOCUMENTS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchInto(VehicleDocumentDao.class);
    }

    @Override
    public List<VehicleDocumentDao> findAllByVehicleIdAndDocumentTypeIds(Integer vehicleId, List<Integer> ids) {
        return context.selectFrom(VEHICLE_DOCUMENTS).where(VEHICLE_DOCUMENTS.VEHICLE_ID.eq(vehicleId))
                .and(VEHICLE_DOCUMENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_DOCUMENTS.VEHICLE_DOCUMENT_TYPE_ID.in(ids))
                .fetchInto(VehicleDocumentDao.class);
    }

    @Override
    public Map<Integer, VehicleDocumentDao> getExistingDocumentMap(Integer vehicleId, List<Integer> activeDocumentTypeIds) {
        List<VehicleDocumentDao> existingDocuments = findAllByVehicleIdAndDocumentTypeIds(vehicleId, activeDocumentTypeIds);
        Map<Integer, VehicleDocumentDao> documentMap = new HashMap<>();
        if (!CollectionUtils.isEmpty(existingDocuments)) {
            for (VehicleDocumentDao document : existingDocuments) {
                if (activeDocumentTypeIds.contains(document.getId())) {
                    documentMap.put(document.getVehicleDocumentTypeId(), document);
                }
            }
        }
        return documentMap;
    }

}