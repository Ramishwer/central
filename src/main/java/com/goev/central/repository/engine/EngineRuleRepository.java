package com.goev.central.repository.engine;

import com.goev.central.dao.engine.EngineRuleDao;

import java.util.List;

public interface EngineRuleRepository {
    EngineRuleDao save(EngineRuleDao partner);

    EngineRuleDao update(EngineRuleDao partner);

    void delete(Integer id);

    EngineRuleDao findByUUID(String uuid);

    EngineRuleDao findById(Integer id);

    List<EngineRuleDao> findAllByIds(List<Integer> ids);

    List<EngineRuleDao> findAllActive();
}