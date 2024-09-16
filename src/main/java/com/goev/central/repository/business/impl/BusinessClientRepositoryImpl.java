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
        client.setCreatedBy(businessClientsRecord.getCreatedBy());
        client.setUpdatedBy(businessClientsRecord.getUpdatedBy());
        client.setCreatedOn(businessClientsRecord.getCreatedOn());
        client.setUpdatedOn(businessClientsRecord.getUpdatedOn());
        client.setIsActive(businessClientsRecord.getIsActive());
        client.setState(businessClientsRecord.getState());
        client.setApiSource(businessClientsRecord.getApiSource());
        client.setNotes(businessClientsRecord.getNotes());
        return client;
    }

    @Override
    public BusinessClientDao update(BusinessClientDao client) {
        BusinessClientsRecord businessClientsRecord = context.newRecord(BUSINESS_CLIENTS, client);
        businessClientsRecord.update();


        client.setCreatedBy(businessClientsRecord.getCreatedBy());
        client.setUpdatedBy(businessClientsRecord.getUpdatedBy());
        client.setCreatedOn(businessClientsRecord.getCreatedOn());
        client.setUpdatedOn(businessClientsRecord.getUpdatedOn());
        client.setIsActive(businessClientsRecord.getIsActive());
        client.setState(businessClientsRecord.getState());
        client.setApiSource(businessClientsRecord.getApiSource());
        client.setNotes(businessClientsRecord.getNotes());
        return client;
    }

    @Override
    public void delete(Integer id) {
        context.update(BUSINESS_CLIENTS)
                .set(BUSINESS_CLIENTS.STATE, RecordState.DELETED.name())
                .where(BUSINESS_CLIENTS.ID.eq(id))
                .and(BUSINESS_CLIENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BUSINESS_CLIENTS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public BusinessClientDao findByUUID(String uuid) {
        return context.selectFrom(BUSINESS_CLIENTS).where(BUSINESS_CLIENTS.UUID.eq(uuid))
                .and(BUSINESS_CLIENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BusinessClientDao.class);
    }

    @Override
    public BusinessClientDao findById(Integer id) {
        return context.selectFrom(BUSINESS_CLIENTS).where(BUSINESS_CLIENTS.ID.eq(id))
                .and(BUSINESS_CLIENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BusinessClientDao.class);
    }

    @Override
    public List<BusinessClientDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BUSINESS_CLIENTS).where(BUSINESS_CLIENTS.ID.in(ids)).fetchInto(BusinessClientDao.class);
    }

    @Override
    public List<BusinessClientDao> findAllActive() {
        return context.selectFrom(BUSINESS_CLIENTS)
                .where(BUSINESS_CLIENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BUSINESS_CLIENTS.IS_ACTIVE.eq(true))
                .fetchInto(BusinessClientDao.class);
    }
}
