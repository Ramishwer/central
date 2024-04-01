package com.goev.central.repository.business.impl;

import com.goev.central.dao.business.BusinessClientDao;
import com.goev.central.repository.business.BusinessClientRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BusinessClientsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.BusinessClients.BUSINESS_CLIENTS;

@Slf4j
@Repository
@AllArgsConstructor
public class BusinessClientRepositoryImpl implements BusinessClientRepository {

    private final DSLContext context;

    @Override
    public BusinessClientDao save(BusinessClientDao client) {
        BusinessClientsRecord businessClientsRecord = context.newRecord(BUSINESS_CLIENTS, client);
        businessClientsRecord.store();
        client.setId(businessClientsRecord.getId());
        client.setUuid(businessClientsRecord.getUuid());
        return client;
    }

    @Override
    public BusinessClientDao update(BusinessClientDao client) {
        BusinessClientsRecord businessClientsRecord = context.newRecord(BUSINESS_CLIENTS, client);
        businessClientsRecord.update();
        return client;
    }

    @Override
    public void delete(Integer id) {
        context.update(BUSINESS_CLIENTS).set(BUSINESS_CLIENTS.STATE, RecordState.DELETED.name()).where(BUSINESS_CLIENTS.ID.eq(id)).execute();
    }

    @Override
    public BusinessClientDao findByUUID(String uuid) {
        return context.selectFrom(BUSINESS_CLIENTS).where(BUSINESS_CLIENTS.UUID.eq(uuid)).fetchAnyInto(BusinessClientDao.class);
    }

    @Override
    public BusinessClientDao findById(Integer id) {
        return context.selectFrom(BUSINESS_CLIENTS).where(BUSINESS_CLIENTS.ID.eq(id)).fetchAnyInto(BusinessClientDao.class);
    }

    @Override
    public List<BusinessClientDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BUSINESS_CLIENTS).where(BUSINESS_CLIENTS.ID.in(ids)).fetchInto(BusinessClientDao.class);
    }

    @Override
    public List<BusinessClientDao> findAll() {
        return context.selectFrom(BUSINESS_CLIENTS).fetchInto(BusinessClientDao.class);
    }
}
