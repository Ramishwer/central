package com.goev.central.repository.booking;

import com.goev.central.dao.booking.BookingPricingElementDao;

import java.util.List;

public interface BookingPricingElementRepository {
    BookingPricingElementDao save(BookingPricingElementDao pricingElement);
    BookingPricingElementDao update(BookingPricingElementDao pricingElement);
    void delete(Integer id);
    BookingPricingElementDao findByUUID(String uuid);
    BookingPricingElementDao findById(Integer id);
    List<BookingPricingElementDao> findAllByIds(List<Integer> ids);
    List<BookingPricingElementDao> findAll();
}