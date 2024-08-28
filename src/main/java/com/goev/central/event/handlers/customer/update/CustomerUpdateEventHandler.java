package com.goev.central.event.handlers.customer.update;

import com.goev.central.dao.customer.detail.CustomerDao;
import com.goev.central.repository.customer.detail.CustomerRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class CustomerUpdateEventHandler extends EventHandler<CustomerDao> {

    private final CustomerRepository customerRepository;

    @Override
    public boolean onEvent(Event<CustomerDao> event) {
        log.info("Data:{}", event.getData());
        CustomerDao customerDao = event.getData();
        if (customerDao == null) {
            log.info("Customer Data Null");
            return false;
        }
        CustomerDao existing = customerRepository.findByUUID(customerDao.getUuid());
        if (existing != null) {
            customerDao.setId(existing.getId());
            customerDao.setUuid(existing.getUuid());
            customerRepository.update(customerDao);
            return true;
        }
        return false;
    }
}
