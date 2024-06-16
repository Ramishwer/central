package com.goev.central.repository.customer.notification;

import com.goev.central.dao.customer.notification.CustomerNotificationTemplateDao;

import java.util.List;

public interface CustomerNotificationTemplateRepository {
    CustomerNotificationTemplateDao save(CustomerNotificationTemplateDao partner);

    CustomerNotificationTemplateDao update(CustomerNotificationTemplateDao partner);

    void delete(Integer id);

    CustomerNotificationTemplateDao findByUUID(String uuid);

    CustomerNotificationTemplateDao findById(Integer id);

    List<CustomerNotificationTemplateDao> findAllByIds(List<Integer> ids);

    List<CustomerNotificationTemplateDao> findAllActive();
}