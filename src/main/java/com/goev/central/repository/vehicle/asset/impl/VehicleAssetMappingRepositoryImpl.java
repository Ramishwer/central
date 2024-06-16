package com.goev.central.repository.vehicle.asset.impl;

import com.goev.central.dao.vehicle.asset.VehicleAssetMappingDao;
import com.goev.central.repository.vehicle.asset.VehicleAssetMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleAssetMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleAssetMappings.VEHICLE_ASSET_MAPPINGS;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleAssetMappingRepositoryImpl implements VehicleAssetMappingRepository {
    private final DSLContext context;


    @Override
    public VehicleAssetMappingDao save(VehicleAssetMappingDao assetMapping) {
        VehicleAssetMappingsRecord vehicleAssetMappingsRecord = context.newRecord(VEHICLE_ASSET_MAPPINGS, assetMapping);
        vehicleAssetMappingsRecord.store();
        assetMapping.setId(vehicleAssetMappingsRecord.getId());
        assetMapping.setUuid(vehicleAssetMappingsRecord.getUuid());
        assetMapping.setCreatedBy(vehicleAssetMappingsRecord.getCreatedBy());
        assetMapping.setUpdatedBy(vehicleAssetMappingsRecord.getUpdatedBy());
        assetMapping.setCreatedOn(vehicleAssetMappingsRecord.getCreatedOn());
        assetMapping.setUpdatedOn(vehicleAssetMappingsRecord.getUpdatedOn());
        assetMapping.setIsActive(vehicleAssetMappingsRecord.getIsActive());
        assetMapping.setState(vehicleAssetMappingsRecord.getState());
        assetMapping.setApiSource(vehicleAssetMappingsRecord.getApiSource());
        assetMapping.setNotes(vehicleAssetMappingsRecord.getNotes());
        return assetMapping;
    }

    @Override
    public VehicleAssetMappingDao update(VehicleAssetMappingDao assetMapping) {
        VehicleAssetMappingsRecord vehicleAssetMappingsRecord = context.newRecord(VEHICLE_ASSET_MAPPINGS, assetMapping);
        vehicleAssetMappingsRecord.update();


        assetMapping.setCreatedBy(vehicleAssetMappingsRecord.getCreatedBy());
        assetMapping.setUpdatedBy(vehicleAssetMappingsRecord.getUpdatedBy());
        assetMapping.setCreatedOn(vehicleAssetMappingsRecord.getCreatedOn());
        assetMapping.setUpdatedOn(vehicleAssetMappingsRecord.getUpdatedOn());
        assetMapping.setIsActive(vehicleAssetMappingsRecord.getIsActive());
        assetMapping.setState(vehicleAssetMappingsRecord.getState());
        assetMapping.setApiSource(vehicleAssetMappingsRecord.getApiSource());
        assetMapping.setNotes(vehicleAssetMappingsRecord.getNotes());
        return assetMapping;
    }

    @Override
    public void delete(Integer id) {
     context.update(VEHICLE_ASSET_MAPPINGS)
     .set(VEHICLE_ASSET_MAPPINGS.STATE,RecordState.DELETED.name())
     .where(VEHICLE_ASSET_MAPPINGS.ID.eq(id))
     .and(VEHICLE_ASSET_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
     .and(VEHICLE_ASSET_MAPPINGS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public VehicleAssetMappingDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_ASSET_MAPPINGS).where(VEHICLE_ASSET_MAPPINGS.UUID.eq(uuid))
                .and(VEHICLE_ASSET_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleAssetMappingDao.class);
    }

    @Override
    public VehicleAssetMappingDao findById(Integer id) {
        return context.selectFrom(VEHICLE_ASSET_MAPPINGS).where(VEHICLE_ASSET_MAPPINGS.ID.eq(id))
                .and(VEHICLE_ASSET_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleAssetMappingDao.class);
    }

    @Override
    public List<VehicleAssetMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_ASSET_MAPPINGS).where(VEHICLE_ASSET_MAPPINGS.ID.in(ids)).fetchInto(VehicleAssetMappingDao.class);
    }

    @Override
    public List<VehicleAssetMappingDao> findAllActive() {
        return context.selectFrom(VEHICLE_ASSET_MAPPINGS).fetchInto(VehicleAssetMappingDao.class);
    }

    @Override
    public List<VehicleAssetMappingDao> findAllByVehicleId(Integer id) {
        return context.selectFrom(VEHICLE_ASSET_MAPPINGS)
                .where(VEHICLE_ASSET_MAPPINGS.VEHICLE_ID.eq(id))
                .and(VEHICLE_ASSET_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchInto(VehicleAssetMappingDao.class);
    }

    @Override
    public VehicleAssetMappingDao findByVehicleIdAndAssetId(Integer vehicleId, Integer assetId) {
        return context.selectFrom(VEHICLE_ASSET_MAPPINGS)
                .where(VEHICLE_ASSET_MAPPINGS.VEHICLE_ID.eq(vehicleId))
                .and(VEHICLE_ASSET_MAPPINGS.ASSET_ID.eq(assetId))
                .and(VEHICLE_ASSET_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchOneInto(VehicleAssetMappingDao.class);
    }

    @Override
    public VehicleAssetMappingDao findByAssetId(Integer assetId) {
        return context.selectFrom(VEHICLE_ASSET_MAPPINGS)
                .where(VEHICLE_ASSET_MAPPINGS.ASSET_ID.eq(assetId))
                .and(VEHICLE_ASSET_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchOneInto(VehicleAssetMappingDao.class);
    }
}
