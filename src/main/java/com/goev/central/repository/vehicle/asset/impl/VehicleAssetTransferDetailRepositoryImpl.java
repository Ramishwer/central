package com.goev.central.repository.vehicle.asset.impl;

import com.goev.central.dao.vehicle.asset.VehicleAssetTransferDetailDao;
import com.goev.central.repository.vehicle.asset.VehicleAssetTransferDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleAssetTransferDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleAssetTransferDetails.VEHICLE_ASSET_TRANSFER_DETAILS;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleAssetTransferDetailRepositoryImpl implements VehicleAssetTransferDetailRepository {
    private final DSLContext context;


    @Override
    public VehicleAssetTransferDetailDao save(VehicleAssetTransferDetailDao assetTransferDetail) {
        VehicleAssetTransferDetailsRecord vehicleAssetTransferDetailsRecord = context.newRecord(VEHICLE_ASSET_TRANSFER_DETAILS, assetTransferDetail);
        vehicleAssetTransferDetailsRecord.store();
        assetTransferDetail.setId(vehicleAssetTransferDetailsRecord.getId());
        assetTransferDetail.setUuid(vehicleAssetTransferDetailsRecord.getUuid());
        return assetTransferDetail;
    }

    @Override
    public VehicleAssetTransferDetailDao update(VehicleAssetTransferDetailDao assetTransferDetail) {
        VehicleAssetTransferDetailsRecord vehicleAssetTransferDetailsRecord = context.newRecord(VEHICLE_ASSET_TRANSFER_DETAILS, assetTransferDetail);
        vehicleAssetTransferDetailsRecord.update();
        return assetTransferDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_ASSET_TRANSFER_DETAILS).set(VEHICLE_ASSET_TRANSFER_DETAILS.STATE, RecordState.DELETED.name()).where(VEHICLE_ASSET_TRANSFER_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public VehicleAssetTransferDetailDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_ASSET_TRANSFER_DETAILS).where(VEHICLE_ASSET_TRANSFER_DETAILS.UUID.eq(uuid)).fetchAnyInto(VehicleAssetTransferDetailDao.class);
    }

    @Override
    public VehicleAssetTransferDetailDao findById(Integer id) {
        return context.selectFrom(VEHICLE_ASSET_TRANSFER_DETAILS).where(VEHICLE_ASSET_TRANSFER_DETAILS.ID.eq(id)).fetchAnyInto(VehicleAssetTransferDetailDao.class);
    }

    @Override
    public List<VehicleAssetTransferDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_ASSET_TRANSFER_DETAILS).where(VEHICLE_ASSET_TRANSFER_DETAILS.ID.in(ids)).fetchInto(VehicleAssetTransferDetailDao.class);
    }

    @Override
    public List<VehicleAssetTransferDetailDao> findAll() {
        return context.selectFrom(VEHICLE_ASSET_TRANSFER_DETAILS).fetchInto(VehicleAssetTransferDetailDao.class);
    }
}
