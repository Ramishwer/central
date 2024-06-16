package com.goev.central.event.events.asset.save;

import com.goev.central.dao.asset.AssetTypeDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class AssetTypeSaveEvent extends Event<AssetTypeDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "AssetTypeSaveEvent";
    }

}
