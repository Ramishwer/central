package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingPricingModelDao;

import java.util.List;

public interface BookingPricingModelRepository {
    BookingPricingModelDao save(BookingPricingModelDao pricingModel);
    BookingPricingModelDao update(BookingPricingModelDao pricingModel);
    void delete(Integer id);
    BookingPricingModelDao findByUUID(String uuid);
    BookingPricingModelDao findById(Integer id);
    List<BookingPricingModelDao> findAllByIds(List<Integer> ids);
    List<BookingPricingModelDao> findAll();
}