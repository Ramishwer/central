package com.goev.central.repository.vehicle.transfer.impl;

import com.goev.central.dao.vehicle.transfer.VehicleAssetTransferDetailDao;
import com.goev.central.repository.vehicle.transfer.VehicleAssetTransferDetailRepository;
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
        assetTransferDetail.setCreatedBy(vehicleAssetTransferDetailsRecord.getCreatedBy());
        assetTransferDetail.setUpdatedBy(vehicleAssetTransferDetailsRecord.getUpdatedBy());
        assetTransferDetail.setCreatedOn(vehicleAssetTransferDetailsRecord.getCreatedOn());
        assetTransferDetail.setUpdatedOn(vehicleAssetTransferDetailsRecord.getUpdatedOn());
        assetTransferDetail.setIsActive(vehicleAssetTransferDetailsRecord.getIsActive());
        assetTransferDetail.setState(vehicleAssetTransferDetailsRecord.getState());
        assetTransferDetail.setApiSource(vehicleAssetTransferDetailsRecord.getApiSource());
        assetTransferDetail.setNotes(vehicleAssetTransferDetailsRecord.getNotes());
        return assetTransferDetail;
    }

    @Override
    public VehicleAssetTransferDetailDao update(VehicleAssetTransferDetailDao assetTransferDetail) {
        VehicleAssetTransferDetailsRecord vehicleAssetTransferDetailsRecord = context.newRecord(VEHICLE_ASSET_TRANSFER_DETAILS, assetTransferDetail);
        vehicleAssetTransferDetailsRecord.update();


        assetTransferDetail.setCreatedBy(vehicleAssetTransferDetailsRecord.getCreatedBy());
        assetTransferDetail.setUpdatedBy(vehicleAssetTransferDetailsRecord.getUpdatedBy());
        assetTransferDetail.setCreatedOn(vehicleAssetTransferDetailsRecord.getCreatedOn());
        assetTransferDetail.setUpdatedOn(vehicleAssetTransferDetailsRecord.getUpdatedOn());
        assetTransferDetail.setIsActive(vehicleAssetTransferDetailsRecord.getIsActive());
        assetTransferDetail.setState(vehicleAssetTransferDetailsRecord.getState());
        assetTransferDetail.setApiSource(vehicleAssetTransferDetailsRecord.getApiSource());
        assetTransferDetail.setNotes(vehicleAssetTransferDetailsRecord.getNotes());
        return assetTransferDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_ASSET_TRANSFER_DETAILS)
                .set(VEHICLE_ASSET_TRANSFER_DETAILS.STATE, RecordState.DELETED.name())
                .where(VEHICLE_ASSET_TRANSFER_DETAILS.ID.eq(id))
                .and(VEHICLE_ASSET_TRANSFER_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLE_ASSET_TRANSFER_DETAILS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public VehicleAssetTransferDetailDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_ASSET_TRANSFER_DETAILS).where(VEHICLE_ASSET_TRANSFER_DETAILS.UUID.eq(uuid))
                .and(VEHICLE_ASSET_TRANSFER_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleAssetTransferDetailDao.class);
    }

    @Override
    public VehicleAssetTransferDetailDao findById(Integer id) {
        return context.selectFrom(VEHICLE_ASSET_TRANSFER_DETAILS).where(VEHICLE_ASSET_TRANSFER_DETAILS.ID.eq(id))
                .and(VEHICLE_ASSET_TRANSFER_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleAssetTransferDetailDao.class);
    }

    @Override
    public List<VehicleAssetTransferDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_ASSET_TRANSFER_DETAILS).where(VEHICLE_ASSET_TRANSFER_DETAILS.ID.in(ids)).fetchInto(VehicleAssetTransferDetailDao.class);
    }

    @Override
    public List<VehicleAssetTransferDetailDao> findAllActive() {
        return context.selectFrom(VEHICLE_ASSET_TRANSFER_DETAILS).fetchInto(VehicleAssetTransferDetailDao.class);
    }
}
