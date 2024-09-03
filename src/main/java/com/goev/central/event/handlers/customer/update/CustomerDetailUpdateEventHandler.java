package com.goev.central.event.handlers.customer.update;


import com.goev.central.dao.customer.detail.CustomerDetailDao;
import com.goev.central.repository.customer.detail.CustomerDetailRepository;
import com.goev.lib.event.core.Event;
import com.goev.lib.event.core.EventHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class CustomerDetailUpdateEventHandler extends EventHandler<CustomerDetailDao> {

    private final CustomerDetailRepository customerDetailRepository;

    @Override
    public boolean onEvent(Event<CustomerDetailDao> event) {
        log.info("Data:{}", event.getData());
        CustomerDetailDao customerDetailDao = event.getData();
        if (customerDetailDao == null) {
            log.info("Customer Data Null");
            return false;
        }
        CustomerDetailDao existing = customerDetailRepository.findByUUID(customerDetailDao.getUuid());
        if (existing != null) {
            customerDetailDao.setId(existing.getId());
            customerDetailDao.setUuid(existing.getUuid());
            customerDetailRepository.update(customerDetailDao);
            return true;
        }
        return false;
    }
}
