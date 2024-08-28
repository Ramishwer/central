package com.goev.central.event.handlers.customer.save;


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
public class CustomerSaveEventHandler extends EventHandler<CustomerDao> {

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
        if (existing == null) {
            customerRepository.save(customerDao);
            return true;
        }
        return false;
    }
}
