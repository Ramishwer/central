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
        return assetMapping;
    }

    @Override
    public VehicleAssetMappingDao update(VehicleAssetMappingDao assetMapping) {
        VehicleAssetMappingsRecord vehicleAssetMappingsRecord = context.newRecord(VEHICLE_ASSET_MAPPINGS, assetMapping);
        vehicleAssetMappingsRecord.update();
        return assetMapping;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_ASSET_MAPPINGS).set(VEHICLE_ASSET_MAPPINGS.STATE, RecordState.DELETED.name()).where(VEHICLE_ASSET_MAPPINGS.ID.eq(id)).execute();
    }

    @Override
    public VehicleAssetMappingDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_ASSET_MAPPINGS).where(VEHICLE_ASSET_MAPPINGS.UUID.eq(uuid)).fetchAnyInto(VehicleAssetMappingDao.class);
    }

    @Override
    public VehicleAssetMappingDao findById(Integer id) {
        return context.selectFrom(VEHICLE_ASSET_MAPPINGS).where(VEHICLE_ASSET_MAPPINGS.ID.eq(id)).fetchAnyInto(VehicleAssetMappingDao.class);
    }

    @Override
    public List<VehicleAssetMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_ASSET_MAPPINGS).where(VEHICLE_ASSET_MAPPINGS.ID.in(ids)).fetchInto(VehicleAssetMappingDao.class);
    }

    @Override
    public List<VehicleAssetMappingDao> findAll() {
        return context.selectFrom(VEHICLE_ASSET_MAPPINGS).fetchInto(VehicleAssetMappingDao.class);
    }
}
