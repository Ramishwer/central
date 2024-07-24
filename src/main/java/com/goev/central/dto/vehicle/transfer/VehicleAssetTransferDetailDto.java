package com.goev.central.dto.vehicle.transfer;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.goev.central.dao.vehicle.transfer.VehicleAssetTransferDetailDao;
import com.goev.central.dto.asset.AssetDto;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleAssetTransferDetailDto {
    private String uuid;
    private AssetDto asset;
    private String status;

    public static VehicleAssetTransferDetailDto fromDao(VehicleAssetTransferDetailDao assetTransferDetailDao, AssetDto assetDto) {
        return VehicleAssetTransferDetailDto.builder()
                .uuid(assetTransferDetailDao.getUuid())
                .status(assetTransferDetailDao.getStatus())
                .asset(assetDto)
                .build();
    }
}
