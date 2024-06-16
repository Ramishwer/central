package com.goev.central.event.events.asset.update;

import com.goev.central.dao.asset.AssetDao;
import com.goev.central.event.targets.PartnerTarget;
import com.goev.lib.event.core.Event;
import jakarta.annotation.PostConstruct;

public class AssetUpdateEvent extends Event<AssetDao> {

    @Override
    @PostConstruct
    public void init() {
        registerEventTargets(PartnerTarget.getTargetName());
    }

    @Override
    public String getName() {
        return "AssetUpdateEvent";
    }

}
