package com.goev.central.repository.system.property;

import com.goev.central.dao.system.property.SystemPropertyDao;

import java.util.List;
import java.util.Map;

public interface SystemPropertyRepository {
    Map<String, SystemPropertyDao> getPropertyMap();

    SystemPropertyDao save(SystemPropertyDao property);

    SystemPropertyDao update(SystemPropertyDao property);

    void delete(Integer id);

    SystemPropertyDao findByUUID(String uuid);

    SystemPropertyDao findById(Integer id);

    List<SystemPropertyDao> findAllByIds(List<Integer> ids);

    List<SystemPropertyDao> findAllActive();
}

