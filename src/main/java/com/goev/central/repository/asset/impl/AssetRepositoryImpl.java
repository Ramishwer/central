package com.goev.central.repository.asset.impl;

import com.goev.central.dao.asset.AssetDao;
import com.goev.central.repository.asset.AssetRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.AssetsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Assets.ASSETS;

@Slf4j
@Repository
@AllArgsConstructor
public class AssetRepositoryImpl implements AssetRepository {

    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public AssetDao save(AssetDao assetDao) {
        AssetsRecord assetsRecord = context.newRecord(ASSETS, assetDao);
        assetsRecord.store();
        assetDao.setId(assetsRecord.getId());
        assetDao.setUuid(assetsRecord.getUuid());
        assetDao.setCreatedBy(assetsRecord.getCreatedBy());
        assetDao.setUpdatedBy(assetsRecord.getUpdatedBy());
        assetDao.setCreatedOn(assetsRecord.getCreatedOn());
        assetDao.setUpdatedOn(assetsRecord.getUpdatedOn());
        assetDao.setIsActive(assetsRecord.getIsActive());
        assetDao.setState(assetsRecord.getState());
        assetDao.setApiSource(assetsRecord.getApiSource());
        assetDao.setNotes(assetsRecord.getNotes());

         if ("API".equals(RequestContext.getRequestSource()))
             eventExecutor.fireEvent("AssetSaveEvent", assetDao);
        return assetDao;
    }

    @Override
    public AssetDao update(AssetDao assetDao) {
        AssetsRecord assetsRecord = context.newRecord(ASSETS, assetDao);
        assetsRecord.update();


        assetDao.setCreatedBy(assetsRecord.getCreatedBy());
        assetDao.setUpdatedBy(assetsRecord.getUpdatedBy());
        assetDao.setCreatedOn(assetsRecord.getCreatedOn());
        assetDao.setUpdatedOn(assetsRecord.getUpdatedOn());
        assetDao.setIsActive(assetsRecord.getIsActive());
        assetDao.setState(assetsRecord.getState());
        assetDao.setApiSource(assetsRecord.getApiSource());
        assetDao.setNotes(assetsRecord.getNotes());

        if ("API".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("AssetUpdateEvent", assetDao);
        return assetDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(ASSETS)
                .set(ASSETS.STATE, RecordState.DELETED.name())
                .where(ASSETS.ID.eq(id))
                .and(ASSETS.STATE.eq(RecordState.ACTIVE.name()))
                .and(ASSETS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public AssetDao findByUUID(String uuid) {
        return context.selectFrom(ASSETS).where(ASSETS.UUID.eq(uuid))
                .and(ASSETS.IS_ACTIVE.eq(true))
                .fetchAnyInto(AssetDao.class);
    }

    @Override
    public AssetDao findById(Integer id) {
        return context.selectFrom(ASSETS).where(ASSETS.ID.eq(id))
                .and(ASSETS.IS_ACTIVE.eq(true))
                .fetchAnyInto(AssetDao.class);
    }

    @Override
    public List<AssetDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(ASSETS).where(ASSETS.ID.in(ids)).fetchInto(AssetDao.class);
    }

    @Override
    public List<AssetDao> findAllActive() {
        return context.selectFrom(ASSETS).fetchInto(AssetDao.class);
    }
}
