package com.goev.central.dao.asset;

import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AssetTypeDao extends BaseDao {
    private String name;
    private String description;
    private String parentType;
}
