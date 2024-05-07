package com.goev.central.repository.shift;

import com.goev.central.dao.shift.ShiftConfigurationDao;

import java.util.List;

public interface ShiftConfigurationRepository {
    ShiftConfigurationDao save(ShiftConfigurationDao partner);
    ShiftConfigurationDao update(ShiftConfigurationDao partner);
    void delete(Integer id);
    ShiftConfigurationDao findByUUID(String uuid);
    ShiftConfigurationDao findById(Integer id);
    List<ShiftConfigurationDao> findAllByIds(List<Integer> ids);
    List<ShiftConfigurationDao> findAll();
}