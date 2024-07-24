package com.goev.central.dao.vehicle.transfer;

import com.goev.lib.dao.BaseDao;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dto.vehicle.transfer.VehicleTransferDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class VehicleTransferDetailDao extends BaseDao {
    private Integer vehicleId;
    private String transferType;
    private String transferFrom;
    private String transferTo;
    private String approvedBy;
    private String approvedByUserId;
    private String status;
    private String transferDetails;
    private String transferLocationDetails;
    private Integer transferLocationId;
    private Integer odometerReading;
    private Integer socReading;

    public static VehicleTransferDetailDao fromDto(VehicleTransferDto vehicleTransferDto, Integer vehicleId) {
        VehicleTransferDetailDao result = new VehicleTransferDetailDao();
        result.setUuid(vehicleTransferDto.getUuid());
        result.setVehicleId(vehicleId);
        result.setTransferType(vehicleTransferDto.getTransferType());
        if(vehicleTransferDto.getTransferFrom() !=null)
            result.setTransferFrom(ApplicationConstants.GSON.toJson(vehicleTransferDto.getTransferFrom()));
        if(vehicleTransferDto.getTransferTo() !=null)
            result.setTransferTo(ApplicationConstants.GSON.toJson(vehicleTransferDto.getTransferTo()));

        if(vehicleTransferDto.getApprovedBy() !=null)
            result.setTransferTo(ApplicationConstants.GSON.toJson(vehicleTransferDto.getApprovedBy()));
        result.setStatus(vehicleTransferDto.getStatus());
        if(vehicleTransferDto.getTransferLocationDetails()!=null)
            result.setTransferLocationDetails(ApplicationConstants.GSON.toJson(vehicleTransferDto.getTransferLocationDetails()));
        result.setOdometerReading(vehicleTransferDto.getOdometerReading());
        result.setSocReading(vehicleTransferDto.getSocReading());
        return result;
    }
}
