package com.goev.central.repository.business.impl;

import com.goev.central.dao.business.BusinessClientDetailDao;
import com.goev.central.repository.business.BusinessClientDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.BusinessClientDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.Tables.BUSINESS_CLIENT_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class BusinessClientDetailRepositoryImpl implements BusinessClientDetailRepository {

    private final DSLContext context;

    @Override
    public BusinessClientDetailDao save(BusinessClientDetailDao client) {
        BusinessClientDetailsRecord businessClientDetailsRecord = context.newRecord(BUSINESS_CLIENT_DETAILS, client);
        businessClientDetailsRecord.store();
        client.setId(businessClientDetailsRecord.getId());
        client.setUuid(businessClientDetailsRecord.getUuid());
        client.setCreatedBy(businessClientDetailsRecord.getCreatedBy());
        client.setUpdatedBy(businessClientDetailsRecord.getUpdatedBy());
        client.setCreatedOn(businessClientDetailsRecord.getCreatedOn());
        client.setUpdatedOn(businessClientDetailsRecord.getUpdatedOn());
        client.setIsActive(businessClientDetailsRecord.getIsActive());
        client.setState(businessClientDetailsRecord.getState());
        client.setApiSource(businessClientDetailsRecord.getApiSource());
        client.setNotes(businessClientDetailsRecord.getNotes());
        return client;
    }

    @Override
    public BusinessClientDetailDao update(BusinessClientDetailDao client) {
        BusinessClientDetailsRecord businessClientDetailsRecord = context.newRecord(BUSINESS_CLIENT_DETAILS, client);
        businessClientDetailsRecord.update();


        client.setCreatedBy(businessClientDetailsRecord.getCreatedBy());
        client.setUpdatedBy(businessClientDetailsRecord.getUpdatedBy());
        client.setCreatedOn(businessClientDetailsRecord.getCreatedOn());
        client.setUpdatedOn(businessClientDetailsRecord.getUpdatedOn());
        client.setIsActive(businessClientDetailsRecord.getIsActive());
        client.setState(businessClientDetailsRecord.getState());
        client.setApiSource(businessClientDetailsRecord.getApiSource());
        client.setNotes(businessClientDetailsRecord.getNotes());
        return client;
    }

    @Override
    public void delete(Integer id) {
        context.update(BUSINESS_CLIENT_DETAILS)
                .set(BUSINESS_CLIENT_DETAILS.STATE, RecordState.DELETED.name())
                .where(BUSINESS_CLIENT_DETAILS.ID.eq(id))
                .and(BUSINESS_CLIENT_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(BUSINESS_CLIENT_DETAILS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public BusinessClientDetailDao findByUUID(String uuid) {
        return context.selectFrom(BUSINESS_CLIENT_DETAILS).where(BUSINESS_CLIENT_DETAILS.UUID.eq(uuid))
                .and(BUSINESS_CLIENT_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BusinessClientDetailDao.class);
    }

    @Override
    public BusinessClientDetailDao findById(Integer id) {
        return context.selectFrom(BUSINESS_CLIENT_DETAILS).where(BUSINESS_CLIENT_DETAILS.ID.eq(id))
                .and(BUSINESS_CLIENT_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(BusinessClientDetailDao.class);
    }

    @Override
    public List<BusinessClientDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(BUSINESS_CLIENT_DETAILS).where(BUSINESS_CLIENT_DETAILS.ID.in(ids)).fetchInto(BusinessClientDetailDao.class);
    }

    @Override
    public List<BusinessClientDetailDao> findAllActive() {
        return context.selectFrom(BUSINESS_CLIENT_DETAILS)
                .where(BUSINESS_CLIENT_DETAILS.IS_ACTIVE.eq(true))
                .and(BUSINESS_CLIENT_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchInto(BusinessClientDetailDao.class);
    }
}
