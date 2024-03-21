package com.goev.central.repository.system.properties.impl;

import com.goev.central.dao.system.properties.SystemPropertiesDao;
import com.goev.central.repository.system.properties.SystemPropertiesRepository;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.Map;

import static com.goev.record.central.tables.SystemProperties.SYSTEM_PROPERTIES;

@Slf4j
@Repository
public class SystemPropertiesRepositoryImpl implements SystemPropertiesRepository {
    @Autowired
    private DSLContext context;

    @Override
    public Map<String, SystemPropertiesDao> getPropertyMap() {
        return context.select().from(SYSTEM_PROPERTIES)
                .where(SYSTEM_PROPERTIES.IS_ACTIVE.eq(true))
                .fetchMap(SYSTEM_PROPERTIES.PROPERTY_NAME, SystemPropertiesDao.class);
    }
}