package com.goev.central.dao.vehicle;

import com.goev.central.dao.user.UserDao;
import com.goev.central.dto.vehicle.VehicleDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleDao extends BaseDao<Integer, VehicleDto> {
    private String plateNumber;
    private String state;

    @Override
    public VehicleDao fromDto(VehicleDto vehicleDto) {
        VehicleDao result = new VehicleDao();
        result.setPlateNumber(vehicleDto.getPlateNumber());
        result.setState(vehicleDto.getState());
        result.setUuid(vehicleDto.getUuid());
        return result;
    }
}
