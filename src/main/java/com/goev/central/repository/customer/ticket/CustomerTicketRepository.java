package com.goev.central.repository.customer.ticket;

import com.goev.central.dao.customer.ticket.CustomerTicketDao;

import java.util.List;

public interface CustomerTicketRepository {
    CustomerTicketDao save(CustomerTicketDao partner);

    CustomerTicketDao update(CustomerTicketDao partner);

    void delete(Integer id);

    CustomerTicketDao findByUUID(String uuid);

    CustomerTicketDao findById(Integer id);

    List<CustomerTicketDao> findAllByIds(List<Integer> ids);

    List<CustomerTicketDao> findAllActive();
}