package com.goev.central.dto.shift;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.shift.ShiftConfigurationDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShiftConfigurationDto {
    private String uuid;
    private ShiftDto shift;
    private String estimatedIn;
    private String estimatedOut;
    private String day;
    private String minimumIn;
    private String maximumIn;
    private String minimumOut;
    private String maximumOut;
    private String autoOut;
    private String startRules;
    private String endRules;

    public static ShiftConfigurationDto fromDao(ShiftConfigurationDao shiftConfigurationDao, ShiftDto shiftDto) {
        return ShiftConfigurationDto.builder()
                .uuid(shiftConfigurationDao.getUuid())
                .day(shiftConfigurationDao.getDay())
                .shift(shiftDto)
                .estimatedIn(shiftConfigurationDao.getEstimatedIn())
                .estimatedOut(shiftConfigurationDao.getEstimatedOut())
                .maximumIn(shiftConfigurationDao.getMaximumIn())
                .minimumIn(shiftConfigurationDao.getMinimumIn())
                .minimumOut(shiftConfigurationDao.getMinimumOut())
                .maximumOut(shiftConfigurationDao.getMaximumOut())
                .autoOut(shiftConfigurationDao.getAutoOut())
                .build();
    }
}



