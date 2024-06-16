package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleLeasingAgencyDao;
import com.goev.central.repository.vehicle.detail.VehicleLeasingAgencyRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleLeasingAgenciesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleLeasingAgencies.VEHICLE_LEASING_AGENCIES;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleLeasingAgencyRepositoryImpl implements VehicleLeasingAgencyRepository {
    private final DSLContext context;

    @Override
    public VehicleLeasingAgencyDao save(VehicleLeasingAgencyDao agency) {
        VehicleLeasingAgenciesRecord vehicleLeasingAgenciesRecord = context.newRecord(VEHICLE_LEASING_AGENCIES, agency);
        vehicleLeasingAgenciesRecord.store();
        agency.setId(vehicleLeasingAgenciesRecord.getId());
        agency.setUuid(vehicleLeasingAgenciesRecord.getUuid());
        agency.setCreatedBy(vehicleLeasingAgenciesRecord.getCreatedBy());
        agency.setUpdatedBy(vehicleLeasingAgenciesRecord.getUpdatedBy());
        agency.setCreatedOn(vehicleLeasingAgenciesRecord.getCreatedOn());
        agency.setUpdatedOn(vehicleLeasingAgenciesRecord.getUpdatedOn());
        agency.setIsActive(vehicleLeasingAgenciesRecord.getIsActive());
        agency.setState(vehicleLeasingAgenciesRecord.getState());
        agency.setApiSource(vehicleLeasingAgenciesRecord.getApiSource());
        agency.setNotes(vehicleLeasingAgenciesRecord.getNotes());
        return agency;
    }

    @Override
    public VehicleLeasingAgencyDao update(VehicleLeasingAgencyDao agency) {
        VehicleLeasingAgenciesRecord vehicleLeasingAgenciesRecord = context.newRecord(VEHICLE_LEASING_AGENCIES, agency);
        vehicleLeasingAgenciesRecord.update();


        agency.setCreatedBy(vehicleLeasingAgenciesRecord.getCreatedBy());
        agency.setUpdatedBy(vehicleLeasingAgenciesRecord.getUpdatedBy());
        agency.setCreatedOn(vehicleLeasingAgenciesRecord.getCreatedOn());
        agency.setUpdatedOn(vehicleLeasingAgenciesRecord.getUpdatedOn());
        agency.setIsActive(vehicleLeasingAgenciesRecord.getIsActive());
        agency.setState(vehicleLeasingAgenciesRecord.getState());
        agency.setApiSource(vehicleLeasingAgenciesRecord.getApiSource());
        agency.setNotes(vehicleLeasingAgenciesRecord.getNotes());
        return agency;
    }

    @Override
    public void delete(Integer id) {
     context.update(VEHICLE_LEASING_AGENCIES)
     .set(VEHICLE_LEASING_AGENCIES.STATE,RecordState.DELETED.name())
     .where(VEHICLE_LEASING_AGENCIES.ID.eq(id))
     .and(VEHICLE_LEASING_AGENCIES.STATE.eq(RecordState.ACTIVE.name()))
     .and(VEHICLE_LEASING_AGENCIES.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public VehicleLeasingAgencyDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_LEASING_AGENCIES).where(VEHICLE_LEASING_AGENCIES.UUID.eq(uuid))
                .and(VEHICLE_LEASING_AGENCIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleLeasingAgencyDao.class);
    }

    @Override
    public VehicleLeasingAgencyDao findById(Integer id) {
        return context.selectFrom(VEHICLE_LEASING_AGENCIES).where(VEHICLE_LEASING_AGENCIES.ID.eq(id))
                .and(VEHICLE_LEASING_AGENCIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleLeasingAgencyDao.class);
    }

    @Override
    public List<VehicleLeasingAgencyDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_LEASING_AGENCIES).where(VEHICLE_LEASING_AGENCIES.ID.in(ids)).fetchInto(VehicleLeasingAgencyDao.class);
    }

    @Override
    public List<VehicleLeasingAgencyDao> findAllActive() {
        return context.selectFrom(VEHICLE_LEASING_AGENCIES).fetchInto(VehicleLeasingAgencyDao.class);
    }
}
