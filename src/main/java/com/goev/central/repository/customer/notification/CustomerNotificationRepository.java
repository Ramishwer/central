package com.goev.central.repository.customer.notification;

import com.goev.central.dao.customer.notification.CustomerNotificationDao;

import java.util.List;

public interface CustomerNotificationRepository {
    CustomerNotificationDao save(CustomerNotificationDao partner);

    CustomerNotificationDao update(CustomerNotificationDao partner);

    void delete(Integer id);

    CustomerNotificationDao findByUUID(String uuid);

    CustomerNotificationDao findById(Integer id);

    List<CustomerNotificationDao> findAllByIds(List<Integer> ids);

    List<CustomerNotificationDao> findAllActive();
}