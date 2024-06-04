package com.goev.central.repository.location.impl;

import com.goev.central.dao.location.LocationAssetMappingDao;
import com.goev.central.repository.location.LocationAssetMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.LocationAssetMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.LocationAssetMappings.LOCATION_ASSET_MAPPINGS;

@Repository
@AllArgsConstructor
@Slf4j
public class LocationAssetMappingRepositoryImpl implements LocationAssetMappingRepository {
    private final DSLContext context;


    @Override
    public LocationAssetMappingDao save(LocationAssetMappingDao assetMapping) {
        LocationAssetMappingsRecord locationAssetMappingsRecord = context.newRecord(LOCATION_ASSET_MAPPINGS, assetMapping);
        locationAssetMappingsRecord.store();
        assetMapping.setId(locationAssetMappingsRecord.getId());
        assetMapping.setUuid(locationAssetMappingsRecord.getUuid());
        return assetMapping;
    }

    @Override
    public LocationAssetMappingDao update(LocationAssetMappingDao assetMapping) {
        LocationAssetMappingsRecord locationAssetMappingsRecord = context.newRecord(LOCATION_ASSET_MAPPINGS, assetMapping);
        locationAssetMappingsRecord.update();
        return assetMapping;
    }

    @Override
    public void delete(Integer id) {
        context.update(LOCATION_ASSET_MAPPINGS).set(LOCATION_ASSET_MAPPINGS.STATE, RecordState.DELETED.name()).where(LOCATION_ASSET_MAPPINGS.ID.eq(id)).execute();
    }

    @Override
    public LocationAssetMappingDao findByUUID(String uuid) {
        return context.selectFrom(LOCATION_ASSET_MAPPINGS).where(LOCATION_ASSET_MAPPINGS.UUID.eq(uuid)).fetchAnyInto(LocationAssetMappingDao.class);
    }

    @Override
    public LocationAssetMappingDao findById(Integer id) {
        return context.selectFrom(LOCATION_ASSET_MAPPINGS).where(LOCATION_ASSET_MAPPINGS.ID.eq(id)).fetchAnyInto(LocationAssetMappingDao.class);
    }

    @Override
    public List<LocationAssetMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(LOCATION_ASSET_MAPPINGS).where(LOCATION_ASSET_MAPPINGS.ID.in(ids)).fetchInto(LocationAssetMappingDao.class);
    }

    @Override
    public List<LocationAssetMappingDao> findAll() {
        return context.selectFrom(LOCATION_ASSET_MAPPINGS).fetchInto(LocationAssetMappingDao.class);
    }

    @Override
    public List<LocationAssetMappingDao> findAllByLocationId(Integer id) {
        return context.selectFrom(LOCATION_ASSET_MAPPINGS).where(LOCATION_ASSET_MAPPINGS.LOCATION_ID.eq(id)).fetchInto(LocationAssetMappingDao.class);
    }

    @Override
    public LocationAssetMappingDao findByLocationIdAndAssetId(Integer locationId, Integer assetId) {
        return context.selectFrom(LOCATION_ASSET_MAPPINGS).where(LOCATION_ASSET_MAPPINGS.LOCATION_ID.eq(locationId)).and(LOCATION_ASSET_MAPPINGS.ASSET_ID.eq(assetId)).fetchOneInto(LocationAssetMappingDao.class);
    }

    @Override
    public LocationAssetMappingDao findByAssetId(Integer assetId) {
        return context.selectFrom(LOCATION_ASSET_MAPPINGS).where(LOCATION_ASSET_MAPPINGS.ASSET_ID.eq(assetId)).fetchOneInto(LocationAssetMappingDao.class);

    }
}
