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
        customerSession.setUuid(customerSession.getUuid());
        return customerSession;
    }

    @Override
    public CustomerSessionDao update(CustomerSessionDao customerSession) {
        CustomerSessionsRecord customerSessionsRecord = context.newRecord(CUSTOMER_SESSIONS, customerSession);
        customerSessionsRecord.update();
        return customerSession;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_SESSIONS).set(CUSTOMER_SESSIONS.STATE, RecordState.DELETED.name()).where(CUSTOMER_SESSIONS.ID.eq(id)).execute();
    }

    @Override
    public CustomerSessionDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_SESSIONS).where(CUSTOMER_SESSIONS.UUID.eq(uuid)).fetchAnyInto(CustomerSessionDao.class);
    }

    @Override
    public CustomerSessionDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_SESSIONS).where(CUSTOMER_SESSIONS.ID.eq(id)).fetchAnyInto(CustomerSessionDao.class);
    }

    @Override
    public List<CustomerSessionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_SESSIONS).where(CUSTOMER_SESSIONS.ID.in(ids)).fetchInto(CustomerSessionDao.class);
    }

    @Override
    public List<CustomerSessionDao> findAll() {
        return context.selectFrom(CUSTOMER_SESSIONS).fetchInto(CustomerSessionDao.class);
    }
}
