package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingTypeVehicleCategoryMappingDao;

import java.util.List;

public interface BookingTypeVehicleCategoryMappingRepository {
    BookingTypeVehicleCategoryMappingDao save(BookingTypeVehicleCategoryMappingDao mapping);
    BookingTypeVehicleCategoryMappingDao update(BookingTypeVehicleCategoryMappingDao mapping);
    void delete(Integer id);
    BookingTypeVehicleCategoryMappingDao findByUUID(String uuid);
    BookingTypeVehicleCategoryMappingDao findById(Integer id);
    List<BookingTypeVehicleCategoryMappingDao> findAllByIds(List<Integer> ids);
    List<BookingTypeVehicleCategoryMappingDao> findAll();
}