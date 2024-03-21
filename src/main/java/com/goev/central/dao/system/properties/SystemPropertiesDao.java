package com.goev.central.dao.system.properties;

import com.goev.central.dto.system.properties.SystemPropertiesDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SystemPropertiesDao extends BaseDao<Integer, SystemPropertiesDto> {
    private String propertyName;
    private String propertyDescription;
    private String propertyType;
    private String propertyValue;
    @Override
    public BaseDao<Integer, SystemPropertiesDto> fromDto(SystemPropertiesDto systemPropertiesDto) {
        return null;
    }

    @Override
    public String toJson() {
        return null;
    }

    @Override
    public SystemPropertiesDto toDto() {
        return null;
    }
}
