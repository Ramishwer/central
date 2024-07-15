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
        shiftConfigurationDao.setEstimatedIn(shiftConfigurationDao.getEstimatedIn());
        shiftConfigurationDao.setEstimatedOut(shiftConfigurationDao.getEstimatedOut());
        shiftConfigurationDao.setMaximumIn(shiftConfigurationDao.getMaximumIn());
        shiftConfigurationDao.setMinimumIn(shiftConfigurationDao.getMinimumIn());
        shiftConfigurationDao.setMinimumOut(shiftConfigurationDao.getMinimumOut());
        shiftConfigurationDao.setMaximumOut(shiftConfigurationDao.getMaximumOut());
        shiftConfigurationDao.setPayoutModelId(payoutModelId);
        shiftConfigurationDao.setDay(shiftConfigurationDao.getDay());
        shiftConfigurationDao.setAutoOut(shiftConfigurationDao.getAutoOut());

        return shiftConfigurationDao;
    }
}


