package com.goev.central.dao.partner.asset;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PartnerAssetMappingDao extends BaseDao {
    private Integer partnerId;
    private Integer assetId;
    private String status;
}
