package com.goev.central.repository.customer.ticket.impl;

import com.goev.central.dao.customer.ticket.CustomerTicketDao;
import com.goev.central.repository.customer.ticket.CustomerTicketRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerTicketsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerTickets.CUSTOMER_TICKETS;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerTicketRepositoryImpl implements CustomerTicketRepository {

    private final DSLContext context;

    @Override
    public CustomerTicketDao save(CustomerTicketDao customerTicket) {
        CustomerTicketsRecord customerTicketsRecord = context.newRecord(CUSTOMER_TICKETS, customerTicket);
        customerTicketsRecord.store();
        customerTicket.setId(customerTicketsRecord.getId());
        customerTicket.setUuid(customerTicket.getUuid());
        return customerTicket;
    }

    @Override
    public CustomerTicketDao update(CustomerTicketDao customerTicket) {
        CustomerTicketsRecord customerTicketsRecord = context.newRecord(CUSTOMER_TICKETS, customerTicket);
        customerTicketsRecord.update();
        return customerTicket;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_TICKETS).set(CUSTOMER_TICKETS.STATE, RecordState.DELETED.name()).where(CUSTOMER_TICKETS.ID.eq(id)).execute();
    }

    @Override
    public CustomerTicketDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_TICKETS).where(CUSTOMER_TICKETS.UUID.eq(uuid)).fetchAnyInto(CustomerTicketDao.class);
    }

    @Override
    public CustomerTicketDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_TICKETS).where(CUSTOMER_TICKETS.ID.eq(id)).fetchAnyInto(CustomerTicketDao.class);
    }

    @Override
    public List<CustomerTicketDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_TICKETS).where(CUSTOMER_TICKETS.ID.in(ids)).fetchInto(CustomerTicketDao.class);
    }

    @Override
    public List<CustomerTicketDao> findAll() {
        return context.selectFrom(CUSTOMER_TICKETS).fetchInto(CustomerTicketDao.class);
    }
}
