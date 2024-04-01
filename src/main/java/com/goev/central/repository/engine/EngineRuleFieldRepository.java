
package com.goev.central.repository.engine;

import com.goev.central.dao.engine.EngineRuleFieldDao;

import java.util.List;

public interface EngineRuleFieldRepository {
    EngineRuleFieldDao save(EngineRuleFieldDao partner);
    EngineRuleFieldDao update(EngineRuleFieldDao partner);
    void delete(Integer id);
    EngineRuleFieldDao findByUUID(String uuid);
    EngineRuleFieldDao findById(Integer id);
    List<EngineRuleFieldDao> findAllByIds(List<Integer> ids);
    List<EngineRuleFieldDao> findAll();
}