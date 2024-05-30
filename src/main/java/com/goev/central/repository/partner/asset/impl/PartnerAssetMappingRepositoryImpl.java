package com.goev.central.repository.partner.asset.impl;

import com.goev.central.dao.partner.asset.PartnerAssetMappingDao;
import com.goev.central.repository.partner.asset.PartnerAssetMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerAssetMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerAssetMappings.PARTNER_ASSET_MAPPINGS;

@Repository
@AllArgsConstructor
@Slf4j
public class PartnerAssetMappingRepositoryImpl implements PartnerAssetMappingRepository {
    private final DSLContext context;


    @Override
    public PartnerAssetMappingDao save(PartnerAssetMappingDao assetMapping) {
        PartnerAssetMappingsRecord partnerAssetMappingsRecord = context.newRecord(PARTNER_ASSET_MAPPINGS, assetMapping);
        partnerAssetMappingsRecord.store();
        assetMapping.setId(partnerAssetMappingsRecord.getId());
        assetMapping.setUuid(partnerAssetMappingsRecord.getUuid());
        return assetMapping;
    }

    @Override
    public PartnerAssetMappingDao update(PartnerAssetMappingDao assetMapping) {
        PartnerAssetMappingsRecord partnerAssetMappingsRecord = context.newRecord(PARTNER_ASSET_MAPPINGS, assetMapping);
        partnerAssetMappingsRecord.update();
        return assetMapping;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_ASSET_MAPPINGS).set(PARTNER_ASSET_MAPPINGS.STATE, RecordState.DELETED.name()).where(PARTNER_ASSET_MAPPINGS.ID.eq(id)).execute();
    }

    @Override
    public PartnerAssetMappingDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_ASSET_MAPPINGS).where(PARTNER_ASSET_MAPPINGS.UUID.eq(uuid)).fetchAnyInto(PartnerAssetMappingDao.class);
    }

    @Override
    public PartnerAssetMappingDao findById(Integer id) {
        return context.selectFrom(PARTNER_ASSET_MAPPINGS).where(PARTNER_ASSET_MAPPINGS.ID.eq(id)).fetchAnyInto(PartnerAssetMappingDao.class);
    }

    @Override
    public List<PartnerAssetMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_ASSET_MAPPINGS).where(PARTNER_ASSET_MAPPINGS.ID.in(ids)).fetchInto(PartnerAssetMappingDao.class);
    }

    @Override
    public List<PartnerAssetMappingDao> findAll() {
        return context.selectFrom(PARTNER_ASSET_MAPPINGS).fetchInto(PartnerAssetMappingDao.class);
    }

    @Override
    public List<PartnerAssetMappingDao> findAllByPartnerId(Integer id) {
        return context.selectFrom(PARTNER_ASSET_MAPPINGS).where(PARTNER_ASSET_MAPPINGS.PARTNER_ID.eq(id)).fetchInto(PartnerAssetMappingDao.class);
    }

    @Override
    public PartnerAssetMappingDao findByPartnerIdAndAssetId(Integer partnerId,Integer assetId) {
        return context.selectFrom(PARTNER_ASSET_MAPPINGS).where(PARTNER_ASSET_MAPPINGS.PARTNER_ID.eq(partnerId)).and(PARTNER_ASSET_MAPPINGS.ASSET_ID.eq(assetId)).fetchOneInto(PartnerAssetMappingDao.class);
    }
}
