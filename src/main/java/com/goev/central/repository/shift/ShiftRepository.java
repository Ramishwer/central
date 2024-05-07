package com.goev.central.repository.shift;

import com.goev.central.dao.shift.ShiftDao;

import java.util.List;

public interface ShiftRepository {
    ShiftDao save(ShiftDao partner);
    ShiftDao update(ShiftDao partner);
    void delete(Integer id);
    ShiftDao findByUUID(String uuid);
    ShiftDao findById(Integer id);
    List<ShiftDao> findAllByIds(List<Integer> ids);
    List<ShiftDao> findAll();
}
