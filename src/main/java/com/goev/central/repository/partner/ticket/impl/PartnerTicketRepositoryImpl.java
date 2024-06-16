package com.goev.central.repository.partner.ticket.impl;

import com.goev.central.dao.partner.ticket.PartnerTicketDao;
import com.goev.central.repository.partner.ticket.PartnerTicketRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerTicketsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerTickets.PARTNER_TICKETS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerTicketRepositoryImpl implements PartnerTicketRepository {

    private final DSLContext context;

    @Override
    public PartnerTicketDao save(PartnerTicketDao partnerTicket) {
        PartnerTicketsRecord partnerTicketsRecord = context.newRecord(PARTNER_TICKETS, partnerTicket);
        partnerTicketsRecord.store();
        partnerTicket.setId(partnerTicketsRecord.getId());
        partnerTicket.setUuid(partnerTicket.getUuid());
        partnerTicket.setCreatedBy(partnerTicket.getCreatedBy());
        partnerTicket.setUpdatedBy(partnerTicket.getUpdatedBy());
        partnerTicket.setCreatedOn(partnerTicket.getCreatedOn());
        partnerTicket.setUpdatedOn(partnerTicket.getUpdatedOn());
        partnerTicket.setIsActive(partnerTicket.getIsActive());
        partnerTicket.setState(partnerTicket.getState());
        partnerTicket.setApiSource(partnerTicket.getApiSource());
        partnerTicket.setNotes(partnerTicket.getNotes());
        return partnerTicket;
    }

    @Override
    public PartnerTicketDao update(PartnerTicketDao partnerTicket) {
        PartnerTicketsRecord partnerTicketsRecord = context.newRecord(PARTNER_TICKETS, partnerTicket);
        partnerTicketsRecord.update();


        partnerTicket.setCreatedBy(partnerTicketsRecord.getCreatedBy());
        partnerTicket.setUpdatedBy(partnerTicketsRecord.getUpdatedBy());
        partnerTicket.setCreatedOn(partnerTicketsRecord.getCreatedOn());
        partnerTicket.setUpdatedOn(partnerTicketsRecord.getUpdatedOn());
        partnerTicket.setIsActive(partnerTicketsRecord.getIsActive());
        partnerTicket.setState(partnerTicketsRecord.getState());
        partnerTicket.setApiSource(partnerTicketsRecord.getApiSource());
        partnerTicket.setNotes(partnerTicketsRecord.getNotes());
        return partnerTicket;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_TICKETS)
                .set(PARTNER_TICKETS.STATE, RecordState.DELETED.name())
                .where(PARTNER_TICKETS.ID.eq(id))
                .and(PARTNER_TICKETS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_TICKETS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerTicketDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_TICKETS).where(PARTNER_TICKETS.UUID.eq(uuid))
                .and(PARTNER_TICKETS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerTicketDao.class);
    }

    @Override
    public PartnerTicketDao findById(Integer id) {
        return context.selectFrom(PARTNER_TICKETS).where(PARTNER_TICKETS.ID.eq(id))
                .and(PARTNER_TICKETS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerTicketDao.class);
    }

    @Override
    public List<PartnerTicketDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_TICKETS).where(PARTNER_TICKETS.ID.in(ids)).fetchInto(PartnerTicketDao.class);
    }

    @Override
    public List<PartnerTicketDao> findAllActive() {
        return context.selectFrom(PARTNER_TICKETS).fetchInto(PartnerTicketDao.class);
    }
}
