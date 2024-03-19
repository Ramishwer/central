package com.goev.central.dao.vehicle;

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
    private String vinNumber;
    private String manufacturer;
    private String model;
    private String year;
    private String state;

    @Override
    public VehicleDao fromDto(VehicleDto vehicleDto) {
        return null;
    }
}
