package com.goev.central.repository.customer.app;

import com.goev.central.dao.customer.app.CustomerAppSupportedLanguageDao;

import java.util.List;

public interface CustomerAppSupportedLanguageRepository {
    CustomerAppSupportedLanguageDao save(CustomerAppSupportedLanguageDao partner);
    CustomerAppSupportedLanguageDao update(CustomerAppSupportedLanguageDao partner);
    void delete(Integer id);
    CustomerAppSupportedLanguageDao findByUUID(String uuid);
    CustomerAppSupportedLanguageDao findById(Integer id);
    List<CustomerAppSupportedLanguageDao> findAllByIds(List<Integer> ids);
    List<CustomerAppSupportedLanguageDao> findAll();
}