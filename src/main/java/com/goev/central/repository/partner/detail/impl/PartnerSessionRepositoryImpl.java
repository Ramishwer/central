package com.goev.central.repository.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerSessionDao;
import com.goev.central.repository.partner.detail.PartnerSessionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerSessionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerSessions.PARTNER_SESSIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerSessionRepositoryImpl implements PartnerSessionRepository {

    private final DSLContext context;

    @Override
    public PartnerSessionDao save(PartnerSessionDao session) {
        PartnerSessionsRecord partnerSessionsRecord = context.newRecord(PARTNER_SESSIONS, session);
        partnerSessionsRecord.store();
        session.setId(partnerSessionsRecord.getId());
        session.setUuid(partnerSessionsRecord.getUuid());
        session.setCreatedBy(partnerSessionsRecord.getCreatedBy());
        session.setUpdatedBy(partnerSessionsRecord.getUpdatedBy());
        session.setCreatedOn(partnerSessionsRecord.getCreatedOn());
        session.setUpdatedOn(partnerSessionsRecord.getUpdatedOn());
        session.setIsActive(partnerSessionsRecord.getIsActive());
        session.setState(partnerSessionsRecord.getState());
        session.setApiSource(partnerSessionsRecord.getApiSource());
        session.setNotes(partnerSessionsRecord.getNotes());
        return session;
    }

    @Override
    public PartnerSessionDao update(PartnerSessionDao session) {
        PartnerSessionsRecord partnerSessionsRecord = context.newRecord(PARTNER_SESSIONS, session);
        partnerSessionsRecord.update();


        session.setCreatedBy(partnerSessionsRecord.getCreatedBy());
        session.setUpdatedBy(partnerSessionsRecord.getUpdatedBy());
        session.setCreatedOn(partnerSessionsRecord.getCreatedOn());
        session.setUpdatedOn(partnerSessionsRecord.getUpdatedOn());
        session.setIsActive(partnerSessionsRecord.getIsActive());
        session.setState(partnerSessionsRecord.getState());
        session.setApiSource(partnerSessionsRecord.getApiSource());
        session.setNotes(partnerSessionsRecord.getNotes());
        return session;
    }

    @Override
    public void delete(Integer id) {
     context.update(PARTNER_SESSIONS)
     .set(PARTNER_SESSIONS.STATE,RecordState.DELETED.name())
     .where(PARTNER_SESSIONS.ID.eq(id))
     .and(PARTNER_SESSIONS.STATE.eq(RecordState.ACTIVE.name()))
     .and(PARTNER_SESSIONS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public PartnerSessionDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_SESSIONS).where(PARTNER_SESSIONS.UUID.eq(uuid))
                .and(PARTNER_SESSIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerSessionDao.class);
    }

    @Override
    public PartnerSessionDao findById(Integer id) {
        return context.selectFrom(PARTNER_SESSIONS).where(PARTNER_SESSIONS.ID.eq(id))
                .and(PARTNER_SESSIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerSessionDao.class);
    }

    @Override
    public List<PartnerSessionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_SESSIONS).where(PARTNER_SESSIONS.ID.in(ids)).fetchInto(PartnerSessionDao.class);
    }

    @Override
    public List<PartnerSessionDao> findAllActive() {
        return context.selectFrom(PARTNER_SESSIONS).fetchInto(PartnerSessionDao.class);
    }
}
