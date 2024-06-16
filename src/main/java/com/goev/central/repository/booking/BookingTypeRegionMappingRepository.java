package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingTypeRegionMappingDao;

import java.util.List;

public interface BookingTypeRegionMappingRepository {
    BookingTypeRegionMappingDao save(BookingTypeRegionMappingDao mapping);

    BookingTypeRegionMappingDao update(BookingTypeRegionMappingDao mapping);

    void delete(Integer id);

    BookingTypeRegionMappingDao findByUUID(String uuid);

    BookingTypeRegionMappingDao findById(Integer id);

    List<BookingTypeRegionMappingDao> findAllByIds(List<Integer> ids);

    List<BookingTypeRegionMappingDao> findAllActive();
}
