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
        customerSegment.setUuid(customerSegmentsRecord.getUuid());
        customerSegment.setCreatedBy(customerSegmentsRecord.getCreatedBy());
        customerSegment.setUpdatedBy(customerSegmentsRecord.getUpdatedBy());
        customerSegment.setCreatedOn(customerSegmentsRecord.getCreatedOn());
        customerSegment.setUpdatedOn(customerSegmentsRecord.getUpdatedOn());
        customerSegment.setIsActive(customerSegmentsRecord.getIsActive());
        customerSegment.setState(customerSegmentsRecord.getState());
        customerSegment.setApiSource(customerSegmentsRecord.getApiSource());
        customerSegment.setNotes(customerSegmentsRecord.getNotes());
        return customerSegment;
    }

    @Override
    public CustomerSegmentDao update(CustomerSegmentDao customerSegment) {
        CustomerSegmentsRecord customerSegmentsRecord = context.newRecord(CUSTOMER_SEGMENTS, customerSegment);
        customerSegmentsRecord.update();


        customerSegment.setCreatedBy(customerSegmentsRecord.getCreatedBy());
        customerSegment.setUpdatedBy(customerSegmentsRecord.getUpdatedBy());
        customerSegment.setCreatedOn(customerSegmentsRecord.getCreatedOn());
        customerSegment.setUpdatedOn(customerSegmentsRecord.getUpdatedOn());
        customerSegment.setIsActive(customerSegmentsRecord.getIsActive());
        customerSegment.setState(customerSegmentsRecord.getState());
        customerSegment.setApiSource(customerSegmentsRecord.getApiSource());
        customerSegment.setNotes(customerSegmentsRecord.getNotes());
        return customerSegment;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_SEGMENTS)
                .set(CUSTOMER_SEGMENTS.STATE, RecordState.DELETED.name())
                .where(CUSTOMER_SEGMENTS.ID.eq(id))
                .and(CUSTOMER_SEGMENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(CUSTOMER_SEGMENTS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public CustomerSegmentDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_SEGMENTS).where(CUSTOMER_SEGMENTS.UUID.eq(uuid))
                .and(CUSTOMER_SEGMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerSegmentDao.class);
    }

    @Override
    public CustomerSegmentDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_SEGMENTS).where(CUSTOMER_SEGMENTS.ID.eq(id))
                .and(CUSTOMER_SEGMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerSegmentDao.class);
    }

    @Override
    public List<CustomerSegmentDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_SEGMENTS).where(CUSTOMER_SEGMENTS.ID.in(ids)).fetchInto(CustomerSegmentDao.class);
    }

    @Override
    public List<CustomerSegmentDao> findAllActive() {
        return context.selectFrom(CUSTOMER_SEGMENTS).fetchInto(CustomerSegmentDao.class);
    }
}
