package com.goev.central.repository.customer.app.impl;

import com.goev.central.dao.customer.app.CustomerAppEventDao;
import com.goev.central.repository.customer.app.CustomerAppEventRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerAppEventsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerAppEvents.CUSTOMER_APP_EVENTS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerAppEventRepositoryImpl implements CustomerAppEventRepository {

    private final DSLContext context;

    @Override
    public CustomerAppEventDao save(CustomerAppEventDao customerAppEvent) {
        CustomerAppEventsRecord customerAppEventsRecord = context.newRecord(CUSTOMER_APP_EVENTS, customerAppEvent);
        customerAppEventsRecord.store();
        customerAppEvent.setId(customerAppEventsRecord.getId());
        customerAppEvent.setUuid(customerAppEvent.getUuid());
        customerAppEvent.setCreatedBy(customerAppEvent.getCreatedBy());
        customerAppEvent.setUpdatedBy(customerAppEvent.getUpdatedBy());
        customerAppEvent.setCreatedOn(customerAppEvent.getCreatedOn());
        customerAppEvent.setUpdatedOn(customerAppEvent.getUpdatedOn());
        customerAppEvent.setIsActive(customerAppEvent.getIsActive());
        customerAppEvent.setState(customerAppEvent.getState());
        customerAppEvent.setApiSource(customerAppEvent.getApiSource());
        customerAppEvent.setNotes(customerAppEvent.getNotes());
        return customerAppEvent;
    }

    @Override
    public CustomerAppEventDao update(CustomerAppEventDao customerAppEvent) {
        CustomerAppEventsRecord customerAppEventsRecord = context.newRecord(CUSTOMER_APP_EVENTS, customerAppEvent);
        customerAppEventsRecord.update();


        customerAppEvent.setCreatedBy(customerAppEventsRecord.getCreatedBy());
        customerAppEvent.setUpdatedBy(customerAppEventsRecord.getUpdatedBy());
        customerAppEvent.setCreatedOn(customerAppEventsRecord.getCreatedOn());
        customerAppEvent.setUpdatedOn(customerAppEventsRecord.getUpdatedOn());
        customerAppEvent.setIsActive(customerAppEventsRecord.getIsActive());
        customerAppEvent.setState(customerAppEventsRecord.getState());
        customerAppEvent.setApiSource(customerAppEventsRecord.getApiSource());
        customerAppEvent.setNotes(customerAppEventsRecord.getNotes());
        return customerAppEvent;
    }

    @Override
    public void delete(Integer id) {
     context.update(CUSTOMER_APP_EVENTS)
     .set(CUSTOMER_APP_EVENTS.STATE,RecordState.DELETED.name())
     .where(CUSTOMER_APP_EVENTS.ID.eq(id))
     .and(CUSTOMER_APP_EVENTS.STATE.eq(RecordState.ACTIVE.name()))
     .and(CUSTOMER_APP_EVENTS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public CustomerAppEventDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_APP_EVENTS).where(CUSTOMER_APP_EVENTS.UUID.eq(uuid))
                .and(CUSTOMER_APP_EVENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerAppEventDao.class);
    }

    @Override
    public CustomerAppEventDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_APP_EVENTS).where(CUSTOMER_APP_EVENTS.ID.eq(id))
                .and(CUSTOMER_APP_EVENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerAppEventDao.class);
    }

    @Override
    public List<CustomerAppEventDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_APP_EVENTS).where(CUSTOMER_APP_EVENTS.ID.in(ids)).fetchInto(CustomerAppEventDao.class);
    }

    @Override
    public List<CustomerAppEventDao> findAllActive() {
        return context.selectFrom(CUSTOMER_APP_EVENTS).fetchInto(CustomerAppEventDao.class);
    }
}
