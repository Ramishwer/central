package com.goev.central.repository.other;

import com.goev.central.dao.VariableDao;

import java.util.List;

public interface VariableRepository {
    VariableDao save(VariableDao partner);

    VariableDao update(VariableDao partner);

    void delete(Integer id);

    VariableDao findByUUID(String uuid);

    VariableDao findById(Integer id);

    List<VariableDao> findAllByIds(List<Integer> ids);

    List<VariableDao> findAllActive();
}
