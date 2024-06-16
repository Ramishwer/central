package com.goev.central.event.handlers.asset.update;

import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.repository.asset.AssetTypeRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class AssetTypeUpdateEventHandler extends EventHandler<AssetTypeDao> {

    private final AssetTypeRepository assetTypeRepository;

    @Override
    public boolean onEvent(Event<AssetTypeDao> event) {
        log.info("Data:{}", event.getData());
        AssetTypeDao assetTypeDao = event.getData();
        if (assetTypeDao == null) {
            log.info("AssetType Data Null");
            return false;
        }
        AssetTypeDao existing = assetTypeRepository.findByUUID(assetTypeDao.getUuid());
        if (existing != null) {
            assetTypeDao.setId(existing.getId());
            assetTypeDao.setUuid(existing.getUuid());
            assetTypeRepository.update(assetTypeDao);
            return true;
        }
        return false;
    }
}
