package com.goev.central.dao.vehicle;

import com.goev.central.dao.user.UserDao;
import com.goev.central.dto.customer.CustomerDto;
import com.goev.central.dto.vehicle.VehicleDto;
import com.goev.lib.dao.BaseDao;
import lombok.*;

import static com.goev.central.constant.ApplicationConstants.GSON;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleDao extends BaseDao<Integer, VehicleDto> {
    private String plateNumber;
    private String state;
    private String vinNumber;
    private String motorNumber;
    private String modelName;
    private String modelYear;
    private String manufacturerName;
    private Integer vehicleDetailsId;


    @Override
    public VehicleDao fromDto(VehicleDto vehicleDto) {
        VehicleDao result = new VehicleDao();
        result.setPlateNumber(vehicleDto.getPlateNumber());
        result.setModelName(vehicleDto.getModel());
        result.setModelYear(vehicleDto.getYear());
        result.setVinNumber(vehicleDto.getVinNumber());
        result.setManufacturerName(vehicleDto.getManufacturer());
        result.setState(vehicleDto.getState());
        result.setUuid(vehicleDto.getUuid());
        return result;
    }

    @Override
    public String toJson() {
        return GSON.toJson(this);
    }

    @Override
    public VehicleDto toDto() {
        return VehicleDto.builder()
                .model(this.modelName)
                .manufacturer(this.manufacturerName)
                .year(this.modelYear)
                .uuid(this.uuid)
                .plateNumber(this.plateNumber)
                .vinNumber(this.vinNumber)
                .state(this.state)
                .build();
    }
}
