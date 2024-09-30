package com.goev.central.dto.vehicle.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.constant.ApplicationConstants;
import com.goev.central.dao.vehicle.transfer.VehicleTransferDetailDao;
import com.goev.central.dto.location.LocationDto;
import com.goev.central.dto.vehicle.VehicleViewDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleTransferDto {
    private String uuid;
    private VehicleViewDto vehicleDetails;
    private String transferType;
    private TransferUserDetailsDto transferFrom;
    private TransferUserDetailsDto transferTo;
    private TransferUserDetailsDto approvedBy;
    private String status;
    private LocationDto transferLocationDetails;
    private Integer odometerReading;
    private Integer socReading;

    public static VehicleTransferDto fromDao(VehicleTransferDetailDao detailDao, VehicleViewDto vehicleViewDto) {
        VehicleTransferDto result = VehicleTransferDto.builder()
                .odometerReading(detailDao.getOdometerReading())
                .vehicleDetails(vehicleViewDto)
                .status(detailDao.getStatus())
                .socReading(detailDao.getSocReading())
                .transferType(detailDao.getTransferType())
                .uuid(detailDao.getUuid()).build();
        if (detailDao.getTransferFrom() != null)
            result.setTransferFrom(ApplicationConstants.GSON.fromJson(detailDao.getTransferFrom(), TransferUserDetailsDto.class));
        if (detailDao.getTransferTo() != null)
            result.setTransferTo(ApplicationConstants.GSON.fromJson(detailDao.getTransferTo(), TransferUserDetailsDto.class));
        if (detailDao.getTransferLocationDetails() != null)
            result.setTransferLocationDetails(ApplicationConstants.GSON.fromJson(detailDao.getTransferLocationDetails(), LocationDto.class));
        if (detailDao.getApprovedBy() != null)
            result.setApprovedBy(ApplicationConstants.GSON.fromJson(detailDao.getApprovedBy(), TransferUserDetailsDto.class));


        return result;
    }
}
