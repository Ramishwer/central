package com.goev.central.repository.system.string;

import com.goev.central.dao.system.string.SystemStringDao;

import java.util.List;

public interface SystemStringRepository {
    SystemStringDao save(SystemStringDao string);

    SystemStringDao update(SystemStringDao string);

    void delete(Integer id);

    SystemStringDao findByUUID(String uuid);

    SystemStringDao findById(Integer id);

    List<SystemStringDao> findAllByIds(List<Integer> ids);

    List<SystemStringDao> findAllActive();
}