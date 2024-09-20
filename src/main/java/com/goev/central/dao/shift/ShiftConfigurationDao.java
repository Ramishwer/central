package com.goev.central.dao.shift;

import com.goev.central.dto.shift.ShiftConfigurationDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ShiftConfigurationDao extends BaseDao {
    private Integer shiftId;
    private String estimatedIn;
    private String estimatedOut;
    private String day;
    private String minimumIn;
    private String maximumIn;
    private String minimumOut;
    private String maximumOut;
    private String autoOut;
    private Integer payoutModelId;
    private String startRules;
    private String endRules;

    public static ShiftConfigurationDao fromDto(ShiftConfigurationDto shiftConfigurationDto,Integer shiftId,Integer payoutModelId) {
        ShiftConfigurationDao shiftConfigurationDao = new ShiftConfigurationDao();

        shiftConfigurationDao.setUuid(shiftConfigurationDto.getUuid());
        shiftConfigurationDao.setShiftId(shiftId);
        shiftConfigurationDao.setEstimatedIn(shiftConfigurationDto.getEstimatedIn());
        shiftConfigurationDao.setEstimatedOut(shiftConfigurationDto.getEstimatedOut());
        shiftConfigurationDao.setMaximumIn(shiftConfigurationDto.getMaximumIn());
        shiftConfigurationDao.setMinimumIn(shiftConfigurationDto.getMinimumIn());
        shiftConfigurationDao.setMinimumOut(shiftConfigurationDto.getMinimumOut());
        shiftConfigurationDao.setMaximumOut(shiftConfigurationDto.getMaximumOut());
        shiftConfigurationDao.setPayoutModelId(payoutModelId);
        shiftConfigurationDao.setDay(shiftConfigurationDto.getDay());
        shiftConfigurationDao.setAutoOut(shiftConfigurationDto.getAutoOut());

        return shiftConfigurationDao;
    }
}


