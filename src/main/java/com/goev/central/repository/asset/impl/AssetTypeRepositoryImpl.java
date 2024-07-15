package com.goev.central.repository.asset.impl;

import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.repository.asset.AssetTypeRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.AssetTypesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.AssetTypes.ASSET_TYPES;

@Slf4j
@Repository
@AllArgsConstructor
public class AssetTypeRepositoryImpl implements AssetTypeRepository {

    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public AssetTypeDao save(AssetTypeDao assetTypeDao) {
        AssetTypesRecord assetTypesRecord = context.newRecord(ASSET_TYPES, assetTypeDao);
        assetTypesRecord.store();
        assetTypeDao.setId(assetTypesRecord.getId());
        assetTypeDao.setUuid(assetTypesRecord.getUuid());
        assetTypeDao.setCreatedBy(assetTypesRecord.getCreatedBy());
        assetTypeDao.setUpdatedBy(assetTypesRecord.getUpdatedBy());
        assetTypeDao.setCreatedOn(assetTypesRecord.getCreatedOn());
        assetTypeDao.setUpdatedOn(assetTypesRecord.getUpdatedOn());
        assetTypeDao.setIsActive(assetTypesRecord.getIsActive());
        assetTypeDao.setState(assetTypesRecord.getState());
        assetTypeDao.setApiSource(assetTypesRecord.getApiSource());
        assetTypeDao.setNotes(assetTypesRecord.getNotes());

        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("AssetTypeSaveEvent", assetTypeDao);
        return assetTypeDao;
    }

    @Override
    public AssetTypeDao update(AssetTypeDao assetTypeDao) {
        AssetTypesRecord assetTypesRecord = context.newRecord(ASSET_TYPES, assetTypeDao);
        assetTypesRecord.update();


        assetTypeDao.setCreatedBy(assetTypesRecord.getCreatedBy());
        assetTypeDao.setUpdatedBy(assetTypesRecord.getUpdatedBy());
        assetTypeDao.setCreatedOn(assetTypesRecord.getCreatedOn());
        assetTypeDao.setUpdatedOn(assetTypesRecord.getUpdatedOn());
        assetTypeDao.setIsActive(assetTypesRecord.getIsActive());
        assetTypeDao.setState(assetTypesRecord.getState());
        assetTypeDao.setApiSource(assetTypesRecord.getApiSource());
        assetTypeDao.setNotes(assetTypesRecord.getNotes());

        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("AssetTypeUpdateEvent", assetTypeDao);
        return assetTypeDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(ASSET_TYPES)
                .set(ASSET_TYPES.STATE, RecordState.DELETED.name())
                .where(ASSET_TYPES.ID.eq(id))
                .and(ASSET_TYPES.STATE.eq(RecordState.ACTIVE.name()))
                .and(ASSET_TYPES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public AssetTypeDao findByUUID(String uuid) {
        return context.selectFrom(ASSET_TYPES).where(ASSET_TYPES.UUID.eq(uuid))
                .and(ASSET_TYPES.IS_ACTIVE.eq(true))
                .fetchAnyInto(AssetTypeDao.class);
    }

    @Override
    public AssetTypeDao findById(Integer id) {
        return context.selectFrom(ASSET_TYPES).where(ASSET_TYPES.ID.eq(id))
                .and(ASSET_TYPES.IS_ACTIVE.eq(true))
                .fetchAnyInto(AssetTypeDao.class);
    }

    @Override
    public List<AssetTypeDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(ASSET_TYPES).where(ASSET_TYPES.ID.in(ids)).fetchInto(AssetTypeDao.class);
    }

    @Override
    public List<AssetTypeDao> findAllActive() {
        return context.selectFrom(ASSET_TYPES).fetchInto(AssetTypeDao.class);
    }
}
