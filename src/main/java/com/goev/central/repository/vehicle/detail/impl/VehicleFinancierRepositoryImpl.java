package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleFinancierDao;
import com.goev.central.repository.vehicle.detail.VehicleFinancierRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleFinanciersRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleFinanciers.VEHICLE_FINANCIERS;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleFinancierRepositoryImpl implements VehicleFinancierRepository {
    private final DSLContext context;

    @Override
    public VehicleFinancierDao save(VehicleFinancierDao vehicleFinancier) {
        VehicleFinanciersRecord vehicleFinanciersRecord = context.newRecord(VEHICLE_FINANCIERS, vehicleFinancier);
        vehicleFinanciersRecord.store();
        vehicleFinancier.setId(vehicleFinanciersRecord.getId());
        vehicleFinancier.setUuid(vehicleFinancier.getUuid());
        return vehicleFinancier;
    }

    @Override
    public VehicleFinancierDao update(VehicleFinancierDao vehicleFinancier) {
        VehicleFinanciersRecord vehicleFinanciersRecord = context.newRecord(VEHICLE_FINANCIERS, vehicleFinancier);
        vehicleFinanciersRecord.update();
        return vehicleFinancier;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_FINANCIERS).set(VEHICLE_FINANCIERS.STATE, RecordState.DELETED.name()).where(VEHICLE_FINANCIERS.ID.eq(id)).execute();
    }

    @Override
    public VehicleFinancierDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_FINANCIERS).where(VEHICLE_FINANCIERS.UUID.eq(uuid)).fetchAnyInto(VehicleFinancierDao.class);
    }

    @Override
    public VehicleFinancierDao findById(Integer id) {
        return context.selectFrom(VEHICLE_FINANCIERS).where(VEHICLE_FINANCIERS.ID.eq(id)).fetchAnyInto(VehicleFinancierDao.class);
    }

    @Override
    public List<VehicleFinancierDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_FINANCIERS).where(VEHICLE_FINANCIERS.ID.in(ids)).fetchInto(VehicleFinancierDao.class);
    }

    @Override
    public List<VehicleFinancierDao> findAll() {
        return context.selectFrom(VEHICLE_FINANCIERS).fetchInto(VehicleFinancierDao.class);
    }
}
