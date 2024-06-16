package com.goev.central.repository.system.language;

import com.goev.central.dao.system.language.SystemSupportedLanguageDao;

import java.util.List;

public interface SystemSupportedLanguageRepository {
    SystemSupportedLanguageDao save(SystemSupportedLanguageDao language);

    SystemSupportedLanguageDao update(SystemSupportedLanguageDao language);

    void delete(Integer id);

    SystemSupportedLanguageDao findByUUID(String uuid);

    SystemSupportedLanguageDao findById(Integer id);

    List<SystemSupportedLanguageDao> findAllByIds(List<Integer> ids);

    List<SystemSupportedLanguageDao> findAllActive();
}
