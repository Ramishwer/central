package com.goev.central.repository.partner.asset;

import com.goev.central.dao.partner.asset.PartnerAssetMappingDao;

import java.util.List;

public interface PartnerAssetMappingRepository {
    PartnerAssetMappingDao save(PartnerAssetMappingDao partner);
    PartnerAssetMappingDao update(PartnerAssetMappingDao partner);
    void delete(Integer id);
    PartnerAssetMappingDao findByUUID(String uuid);
    PartnerAssetMappingDao findById(Integer id);
    List<PartnerAssetMappingDao> findAllByIds(List<Integer> ids);
    List<PartnerAssetMappingDao> findAll();

    List<PartnerAssetMappingDao> findAllByPartnerId(Integer id);
    PartnerAssetMappingDao findByPartnerIdAndAssetId(Integer partnerId,Integer assetId);
}