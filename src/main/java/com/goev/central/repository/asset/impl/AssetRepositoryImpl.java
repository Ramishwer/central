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
        return client;
    }

    @Override
    public AssetDao update(AssetDao client) {
        AssetsRecord assetsRecord = context.newRecord(ASSETS, client);
        assetsRecord.update();
        return client;
    }

    @Override
    public void delete(Integer id) {
        context.update(ASSETS).set(ASSETS.STATE, RecordState.DELETED.name()).where(ASSETS.ID.eq(id)).execute();
    }

    @Override
    public AssetDao findByUUID(String uuid) {
        return context.selectFrom(ASSETS).where(ASSETS.UUID.eq(uuid)).fetchAnyInto(AssetDao.class);
    }

    @Override
    public AssetDao findById(Integer id) {
        return context.selectFrom(ASSETS).where(ASSETS.ID.eq(id)).fetchAnyInto(AssetDao.class);
    }

    @Override
    public List<AssetDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(ASSETS).where(ASSETS.ID.in(ids)).fetchInto(AssetDao.class);
    }

    @Override
    public List<AssetDao> findAll() {
        return context.selectFrom(ASSETS).fetchInto(AssetDao.class);
    }
}
