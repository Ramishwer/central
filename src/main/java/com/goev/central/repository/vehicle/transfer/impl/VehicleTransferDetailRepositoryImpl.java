package com.goev.central.repository.vehicle.transfer.impl;

import com.goev.central.dao.vehicle.transfer.VehicleTransferDetailDao;
import com.goev.central.repository.vehicle.transfer.VehicleTransferDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleTransferDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleTransferDetails.VEHICLE_TRANSFER_DETAILS;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleTransferDetailRepositoryImpl implements VehicleTransferDetailRepository {
    private final DSLContext context;


    @Override
    public VehicleTransferDetailDao save(VehicleTransferDetailDao transferDetail) {
        VehicleTransferDetailsRecord vehicleTransferDetailsRecord = context.newRecord(VEHICLE_TRANSFER_DETAILS, transferDetail);
        vehicleTransferDetailsRecord.store();
        transferDetail.setId(vehicleTransferDetailsRecord.getId());
        transferDetail.setUuid(vehicleTransferDetailsRecord.getUuid());
        return transferDetail;
    }

    @Override
    public VehicleTransferDetailDao update(VehicleTransferDetailDao transferDetail) {
        VehicleTransferDetailsRecord vehicleTransferDetailsRecord = context.newRecord(VEHICLE_TRANSFER_DETAILS, transferDetail);
        vehicleTransferDetailsRecord.update();
        return transferDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_TRANSFER_DETAILS).set(VEHICLE_TRANSFER_DETAILS.STATE, RecordState.DELETED.name()).where(VEHICLE_TRANSFER_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public VehicleTransferDetailDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_TRANSFER_DETAILS).where(VEHICLE_TRANSFER_DETAILS.UUID.eq(uuid)).fetchAnyInto(VehicleTransferDetailDao.class);
    }

    @Override
    public VehicleTransferDetailDao findById(Integer id) {
        return context.selectFrom(VEHICLE_TRANSFER_DETAILS).where(VEHICLE_TRANSFER_DETAILS.ID.eq(id)).fetchAnyInto(VehicleTransferDetailDao.class);
    }

    @Override
    public List<VehicleTransferDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_TRANSFER_DETAILS).where(VEHICLE_TRANSFER_DETAILS.ID.in(ids)).fetchInto(VehicleTransferDetailDao.class);
    }

    @Override
    public List<VehicleTransferDetailDao> findAll() {
        return context.selectFrom(VEHICLE_TRANSFER_DETAILS).fetchInto(VehicleTransferDetailDao.class);
    }

    @Override
    public List<VehicleTransferDetailDao> findAllByVehicleId(Integer vehicleId) {
        return context.selectFrom(VEHICLE_TRANSFER_DETAILS).where(VEHICLE_TRANSFER_DETAILS.VEHICLE_ID.eq(vehicleId)).fetchInto(VehicleTransferDetailDao.class);
    }
}
