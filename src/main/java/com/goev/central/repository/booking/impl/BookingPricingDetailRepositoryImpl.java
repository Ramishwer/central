package com.goev.central.repository.booking.impl;


import com.goev.lib.enums.RecordState;
import com.goev.central.dao.booking.BookingPricingDetailDao;
import com.goev.central.repository.booking.BookingPricingDetailRepository;
import com.goev.record.central.tables.records.BookingPricingDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BookingPricingDetails.BOOKING_PRICING_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class BookingPricingDetailRepositoryImpl implements BookingPricingDetailRepository {

    private final DSLContext context;

    @Override
    public BookingPricingDetailDao save(BookingPricingDetailDao bookingPricingDetailDao) {
        BookingPricingDetailsRecord bookingPricingDetailsRecord = context.newRecord(BOOKING_PRICING_DETAILS, bookingPricingDetailDao);
        bookingPricingDetailsRecord.store();
        bookingPricingDetailDao.setId(bookingPricingDetailsRecord.getId());
        bookingPricingDetailDao.setUuid(bookingPricingDetailsRecord.getUuid());
        return bookingPricingDetailDao;
    }

    @Override
    public BookingPricingDetailDao update(BookingPricingDetailDao bookingPricingDetailDao) {
        BookingPricingDetailsRecord bookingPricingDetailsRecord = context.newRecord(BOOKING_PRICING_DETAILS, bookingPricingDetailDao);
        bookingPricingDetailsRecord.update();
        return bookingPricingDetailDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(BOOKING_PRICING_DETAILS).set(BOOKING_PRICING_DETAILS.STATE, RecordState.DELETED.name()).where(BOOKING_PRICING_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public BookingPricingDetailDao findByUUID(String uuid) {
        return context.selectFrom(BOOKING_PRICING_DETAILS).where(BOOKING_PRICING_DETAILS.UUID.eq(uuid)).fetchAnyInto(BookingPricingDetailDao.class);
    }

    @Override
    public BookingPricingDetailDao findById(Integer id) {
        return context.selectFrom(BOOKING_PRICING_DETAILS).where(BOOKING_PRICING_DETAILS.ID.eq(id)).fetchAnyInto(BookingPricingDetailDao.class);
    }

    @Override
    public List<BookingPricingDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BOOKING_PRICING_DETAILS).where(BOOKING_PRICING_DETAILS.ID.in(ids)).fetchInto(BookingPricingDetailDao.class);
    }

    @Override
    public List<BookingPricingDetailDao> findAll() {
        return context.selectFrom(BOOKING_PRICING_DETAILS).fetchInto(BookingPricingDetailDao.class);
    }
}
