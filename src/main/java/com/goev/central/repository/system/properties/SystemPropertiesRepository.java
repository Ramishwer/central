package com.goev.central.repository.system.properties;

import com.goev.central.dao.system.properties.SystemPropertiesDao;

import java.util.Map;

public interface SystemPropertiesRepository {
    Map<String, SystemPropertiesDao> getPropertyMap();
}
