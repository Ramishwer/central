package com.goev.central.repository.partner.app.impl;

import com.goev.central.dao.partner.app.PartnerAppEventDao;
import com.goev.central.repository.partner.app.PartnerAppEventRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerAppEventsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerAppEvents.PARTNER_APP_EVENTS;

@Repository
@AllArgsConstructor
@Slf4j
public class PartnerAppEventRepositoryImpl implements PartnerAppEventRepository {
    private final DSLContext context;

    @Override
    public PartnerAppEventDao save(PartnerAppEventDao partnerAppEvent) {
        PartnerAppEventsRecord partnerAppEventsRecord = context.newRecord(PARTNER_APP_EVENTS, partnerAppEvent);
        partnerAppEventsRecord.store();
        partnerAppEvent.setId(partnerAppEventsRecord.getId());
        partnerAppEvent.setUuid(partnerAppEventsRecord.getUuid());
        partnerAppEvent.setCreatedBy(partnerAppEventsRecord.getCreatedBy());
        partnerAppEvent.setUpdatedBy(partnerAppEventsRecord.getUpdatedBy());
        partnerAppEvent.setCreatedOn(partnerAppEventsRecord.getCreatedOn());
        partnerAppEvent.setUpdatedOn(partnerAppEventsRecord.getUpdatedOn());
        partnerAppEvent.setIsActive(partnerAppEventsRecord.getIsActive());
        partnerAppEvent.setState(partnerAppEventsRecord.getState());
        partnerAppEvent.setApiSource(partnerAppEventsRecord.getApiSource());
        partnerAppEvent.setNotes(partnerAppEventsRecord.getNotes());
        return partnerAppEvent;
    }

    @Override
    public PartnerAppEventDao update(PartnerAppEventDao partnerAppEvent) {
        PartnerAppEventsRecord partnerAppEventsRecord = context.newRecord(PARTNER_APP_EVENTS, partnerAppEvent);
        partnerAppEventsRecord.update();


        partnerAppEvent.setCreatedBy(partnerAppEventsRecord.getCreatedBy());
        partnerAppEvent.setUpdatedBy(partnerAppEventsRecord.getUpdatedBy());
        partnerAppEvent.setCreatedOn(partnerAppEventsRecord.getCreatedOn());
        partnerAppEvent.setUpdatedOn(partnerAppEventsRecord.getUpdatedOn());
        partnerAppEvent.setIsActive(partnerAppEventsRecord.getIsActive());
        partnerAppEvent.setState(partnerAppEventsRecord.getState());
        partnerAppEvent.setApiSource(partnerAppEventsRecord.getApiSource());
        partnerAppEvent.setNotes(partnerAppEventsRecord.getNotes());
        return partnerAppEvent;
    }

    @Override
    public void delete(Integer id) {
     context.update(PARTNER_APP_EVENTS)
     .set(PARTNER_APP_EVENTS.STATE,RecordState.DELETED.name())
     .where(PARTNER_APP_EVENTS.ID.eq(id))
     .and(PARTNER_APP_EVENTS.STATE.eq(RecordState.ACTIVE.name()))
     .and(PARTNER_APP_EVENTS.IS_ACTIVE.eq(true))
     .execute();
    }

    @Override
    public PartnerAppEventDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_APP_EVENTS).where(PARTNER_APP_EVENTS.UUID.eq(uuid))
                .and(PARTNER_APP_EVENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerAppEventDao.class);
    }

    @Override
    public PartnerAppEventDao findById(Integer id) {
        return context.selectFrom(PARTNER_APP_EVENTS).where(PARTNER_APP_EVENTS.ID.eq(id))
                .and(PARTNER_APP_EVENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerAppEventDao.class);
    }

    @Override
    public List<PartnerAppEventDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_APP_EVENTS).where(PARTNER_APP_EVENTS.ID.in(ids)).fetchInto(PartnerAppEventDao.class);
    }

    @Override
    public List<PartnerAppEventDao> findAllActive() {
        return context.selectFrom(PARTNER_APP_EVENTS).fetchInto(PartnerAppEventDao.class);
    }


}