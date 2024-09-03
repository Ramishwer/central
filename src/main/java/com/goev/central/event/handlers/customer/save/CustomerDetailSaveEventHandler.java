package com.goev.central.event.handlers.customer.save;


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
public class CustomerDetailSaveEventHandler extends EventHandler<CustomerDetailDao> {

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
        if (existing == null) {
            customerDetailRepository.save(customerDetailDao);
            return true;
        }
        return false;
    }
}
