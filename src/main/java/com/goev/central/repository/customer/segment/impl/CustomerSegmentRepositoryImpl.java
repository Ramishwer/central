package com.goev.central.repository.customer.segment.impl;

import com.goev.central.dao.customer.segment.CustomerSegmentDao;
import com.goev.central.repository.customer.segment.CustomerSegmentRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerSegmentsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerSegments.CUSTOMER_SEGMENTS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerSegmentRepositoryImpl implements CustomerSegmentRepository {

    private final DSLContext context;

    @Override
    public CustomerSegmentDao save(CustomerSegmentDao customerSegment) {
        CustomerSegmentsRecord customerSegmentsRecord = context.newRecord(CUSTOMER_SEGMENTS, customerSegment);
        customerSegmentsRecord.store();
        customerSegment.setId(customerSegmentsRecord.getId());
        customerSegment.setUuid(customerSegment.getUuid());
        return customerSegment;
    }

    @Override
    public CustomerSegmentDao update(CustomerSegmentDao customerSegment) {
        CustomerSegmentsRecord customerSegmentsRecord = context.newRecord(CUSTOMER_SEGMENTS, customerSegment);
        customerSegmentsRecord.update();
        return customerSegment;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_SEGMENTS).set(CUSTOMER_SEGMENTS.STATE, RecordState.DELETED.name()).where(CUSTOMER_SEGMENTS.ID.eq(id)).execute();
    }

    @Override
    public CustomerSegmentDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_SEGMENTS).where(CUSTOMER_SEGMENTS.UUID.eq(uuid)).fetchAnyInto(CustomerSegmentDao.class);
    }

    @Override
    public CustomerSegmentDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_SEGMENTS).where(CUSTOMER_SEGMENTS.ID.eq(id)).fetchAnyInto(CustomerSegmentDao.class);
    }

    @Override
    public List<CustomerSegmentDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_SEGMENTS).where(CUSTOMER_SEGMENTS.ID.in(ids)).fetchInto(CustomerSegmentDao.class);
    }

    @Override
    public List<CustomerSegmentDao> findAll() {
        return context.selectFrom(CUSTOMER_SEGMENTS).fetchInto(CustomerSegmentDao.class);
    }
}
