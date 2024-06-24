package com.goev.central.repository.customer.detail.impl;

import com.goev.central.dao.customer.detail.CustomerSessionDao;
import com.goev.central.repository.customer.detail.CustomerSessionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerSessionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerSessions.CUSTOMER_SESSIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerSessionRepositoryImpl implements CustomerSessionRepository {

    private final DSLContext context;

    @Override
    public CustomerSessionDao save(CustomerSessionDao customerSession) {
        CustomerSessionsRecord customerSessionsRecord = context.newRecord(CUSTOMER_SESSIONS, customerSession);
        customerSessionsRecord.store();
        customerSession.setId(customerSessionsRecord.getId());
        customerSession.setUuid(customerSessionsRecord.getUuid());
        customerSession.setCreatedBy(customerSessionsRecord.getCreatedBy());
        customerSession.setUpdatedBy(customerSessionsRecord.getUpdatedBy());
        customerSession.setCreatedOn(customerSessionsRecord.getCreatedOn());
        customerSession.setUpdatedOn(customerSessionsRecord.getUpdatedOn());
        customerSession.setIsActive(customerSessionsRecord.getIsActive());
        customerSession.setState(customerSessionsRecord.getState());
        customerSession.setApiSource(customerSessionsRecord.getApiSource());
        customerSession.setNotes(customerSessionsRecord.getNotes());
        return customerSession;
    }

    @Override
    public CustomerSessionDao update(CustomerSessionDao customerSession) {
        CustomerSessionsRecord customerSessionsRecord = context.newRecord(CUSTOMER_SESSIONS, customerSession);
        customerSessionsRecord.update();


        customerSession.setCreatedBy(customerSessionsRecord.getCreatedBy());
        customerSession.setUpdatedBy(customerSessionsRecord.getUpdatedBy());
        customerSession.setCreatedOn(customerSessionsRecord.getCreatedOn());
        customerSession.setUpdatedOn(customerSessionsRecord.getUpdatedOn());
        customerSession.setIsActive(customerSessionsRecord.getIsActive());
        customerSession.setState(customerSessionsRecord.getState());
        customerSession.setApiSource(customerSessionsRecord.getApiSource());
        customerSession.setNotes(customerSessionsRecord.getNotes());
        return customerSession;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_SESSIONS)
                .set(CUSTOMER_SESSIONS.STATE, RecordState.DELETED.name())
                .where(CUSTOMER_SESSIONS.ID.eq(id))
                .and(CUSTOMER_SESSIONS.STATE.eq(RecordState.ACTIVE.name()))
                .and(CUSTOMER_SESSIONS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public CustomerSessionDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_SESSIONS).where(CUSTOMER_SESSIONS.UUID.eq(uuid))
                .and(CUSTOMER_SESSIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerSessionDao.class);
    }

    @Override
    public CustomerSessionDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_SESSIONS).where(CUSTOMER_SESSIONS.ID.eq(id))
                .and(CUSTOMER_SESSIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(CustomerSessionDao.class);
    }

    @Override
    public List<CustomerSessionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_SESSIONS).where(CUSTOMER_SESSIONS.ID.in(ids)).fetchInto(CustomerSessionDao.class);
    }

    @Override
    public List<CustomerSessionDao> findAllActive() {
        return context.selectFrom(CUSTOMER_SESSIONS).fetchInto(CustomerSessionDao.class);
    }
}
