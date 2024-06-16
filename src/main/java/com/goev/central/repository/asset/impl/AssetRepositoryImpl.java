package com.goev.central.repository.asset.impl;

import com.goev.central.dao.asset.AssetDao;
import com.goev.central.repository.asset.AssetRepository;
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

    @Override
    public AssetDao save(AssetDao client) {
        AssetsRecord assetsRecord = context.newRecord(ASSETS, client);
        assetsRecord.store();
        client.setId(assetsRecord.getId());
        client.setUuid(assetsRecord.getUuid());
        client.setCreatedBy(assetsRecord.getCreatedBy());
        client.setUpdatedBy(assetsRecord.getUpdatedBy());
        client.setCreatedOn(assetsRecord.getCreatedOn());
        client.setUpdatedOn(assetsRecord.getUpdatedOn());
        client.setIsActive(assetsRecord.getIsActive());
        client.setState(assetsRecord.getState());
        client.setApiSource(assetsRecord.getApiSource());
        client.setNotes(assetsRecord.getNotes());
        return client;
    }

    @Override
    public AssetDao update(AssetDao client) {
        AssetsRecord assetsRecord = context.newRecord(ASSETS, client);
        assetsRecord.update();


        client.setCreatedBy(assetsRecord.getCreatedBy());
        client.setUpdatedBy(assetsRecord.getUpdatedBy());
        client.setCreatedOn(assetsRecord.getCreatedOn());
        client.setUpdatedOn(assetsRecord.getUpdatedOn());
        client.setIsActive(assetsRecord.getIsActive());
        client.setState(assetsRecord.getState());
        client.setApiSource(assetsRecord.getApiSource());
        client.setNotes(assetsRecord.getNotes());
        return client;
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
