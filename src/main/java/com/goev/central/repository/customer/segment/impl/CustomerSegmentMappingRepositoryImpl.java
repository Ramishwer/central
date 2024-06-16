package com.goev.central.repository.customer.segment.impl;

import com.goev.central.dao.customer.segment.CustomerSegmentMappingDao;
import com.goev.central.repository.customer.segment.CustomerSegmentMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerSegmentMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerSegmentMappings.CUSTOMER_SEGMENT_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerSegmentMappingRepositoryImpl implements CustomerSegmentMappingRepository {

    private final DSLContext context;

    @Override
    public CustomerSegmentMappingDao save(CustomerSegmentMappingDao customerSegmentMapping) {
        CustomerSegmentMappingsRecord customerSegmentMappingsRecord = context.newRecord(CUSTOMER_SEGMENT_MAPPINGS, customerSegmentMapping);
        customerSegmentMappingsRecord.store();
        customerSegmentMapping.setId(customerSegmentMappingsRecord.getId());
        customerSegmentMapping.setUuid(customerSegmentMapping.getUuid());
        customerSegmentMapping.setCreatedBy(customerSegmentMapping.getCreatedBy());
        customerSegmentMapping.setUpdatedBy(customerSegmentMapping.getUpdatedBy());
        customerSegmentMapping.setCreatedOn(customerSegmentMapping.getCreatedOn());
        customerSegmentMapping.setUpdatedOn(customerSegmentMapping.getUpdatedOn());
        customerSegmentMapping.setIsActive(customerSegmentMapping.getIsActive());
        customerSegmentMapping.setState(customerSegmentMapping.getState());
        customerSegmentMapping.setApiSource(customerSegmentMapping.getApiSource());
        customerSegmentMapping.setNotes(customerSegmentMapping.getNotes());
        return customerSegmentMapping;
    }

    @Override
    public CustomerSegmentMappingDao update(CustomerSegmentMappingDao customerSegmentMapping) {
        CustomerSegmentMappingsRecord customerSegmentMappingsRecord = context.newRecord(CUSTOMER_SEGMENT_MAPPINGS, customerSegmentMapping);
        customerSegmentMappingsRecord.update();


        customerSegmentMapping.setCreatedBy(customerSegmentMappingsRecord.getCreatedBy());
        customerSegmentMapping.setUpdatedBy(customerSegmentMappingsRecord.getUpdatedBy());
        customerSegmentMapping.setCreatedOn(customerSegmentMappingsRecord.getCreatedOn());
        customerSegmentMapping.setUpdatedOn(customerSegmentMappingsRecord.getUpdatedOn());
        customerSegmentMapping.setIsActive(customerSegmentMappingsRecord.getIsActive());
        customerSegmentMapping.setState(customerSegmentMappingsRecord.getState());
        customerSegmentMapping.setApiSource(customerSegmentMappingsRecord.getApiSource());
        customerSegmentMapping.setNotes(customerSegmentMappingsRecord.getNotes());
        return customerSegmentMapping;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_SEGMENT_MAPPINGS)
                .set(CUSTOMER_SEGMENT_MAPPINGS.STATE, RecordState.DELETED.name())
                .where(CUSTOMER_SEGMENT_MAPPINGS.ID.eq(id))
                .and(CUSTOMER_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(CUSTOMER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public CustomerSegmentMappingDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_SEGMENT_MAPPINGS).where(CUSTOMER_SEGMENT_MAPPINGS.UUID.eq(uuid))
                .and(CUSTOMER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerSegmentMappingDao.class);
    }

    @Override
    public CustomerSegmentMappingDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_SEGMENT_MAPPINGS).where(CUSTOMER_SEGMENT_MAPPINGS.ID.eq(id))
                .and(CUSTOMER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerSegmentMappingDao.class);
    }

    @Override
    public List<CustomerSegmentMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_SEGMENT_MAPPINGS).where(CUSTOMER_SEGMENT_MAPPINGS.ID.in(ids)).fetchInto(CustomerSegmentMappingDao.class);
    }

    @Override
    public List<CustomerSegmentMappingDao> findAllActive() {
        return context.selectFrom(CUSTOMER_SEGMENT_MAPPINGS).fetchInto(CustomerSegmentMappingDao.class);
    }
}
