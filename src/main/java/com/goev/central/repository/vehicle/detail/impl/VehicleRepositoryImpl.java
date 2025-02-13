package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.dao.vehicle.detail.VehicleDao;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.enums.partner.PartnerSubStatus;
import com.goev.central.enums.vehicle.VehicleOnboardingStatus;
import com.goev.central.enums.vehicle.VehicleStatus;
import com.goev.central.repository.vehicle.detail.VehicleRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.lib.utilities.ApplicationContext;
import com.goev.record.central.tables.records.VehiclesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.Tables.*;
import static com.goev.record.central.tables.Partners.PARTNERS;
import static com.goev.record.central.tables.Vehicles.VEHICLES;
import static com.goev.record.central.tables.Vehicles.VEHICLES;
import static com.goev.record.central.tables.Vehicles.VEHICLES;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleRepositoryImpl implements VehicleRepository {
    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public VehicleDao save(VehicleDao vehicle) {
        VehiclesRecord vehiclesRecord = context.newRecord(VEHICLES, vehicle);
        vehiclesRecord.store();
        vehicle.setId(vehiclesRecord.getId());
        vehicle.setUuid(vehiclesRecord.getUuid());
        vehicle.setCreatedBy(vehiclesRecord.getCreatedBy());
        vehicle.setUpdatedBy(vehiclesRecord.getUpdatedBy());
        vehicle.setCreatedOn(vehiclesRecord.getCreatedOn());
        vehicle.setUpdatedOn(vehiclesRecord.getUpdatedOn());
        vehicle.setIsActive(vehiclesRecord.getIsActive());
        vehicle.setState(vehiclesRecord.getState());
        vehicle.setApiSource(vehiclesRecord.getApiSource());
        vehicle.setNotes(vehiclesRecord.getNotes());

        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("VehicleSaveEvent", vehicle);
        return vehicle;
    }

    @Override
    public VehicleDao update(VehicleDao vehicle) {
        VehiclesRecord vehiclesRecord = context.newRecord(VEHICLES, vehicle);
        vehiclesRecord.update();


        vehicle.setCreatedBy(vehiclesRecord.getCreatedBy());
        vehicle.setUpdatedBy(vehiclesRecord.getUpdatedBy());
        vehicle.setCreatedOn(vehiclesRecord.getCreatedOn());
        vehicle.setUpdatedOn(vehiclesRecord.getUpdatedOn());
        vehicle.setIsActive(vehiclesRecord.getIsActive());
        vehicle.setState(vehiclesRecord.getState());
        vehicle.setApiSource(vehiclesRecord.getApiSource());
        vehicle.setNotes(vehiclesRecord.getNotes());

        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("VehicleUpdateEvent", vehicle);
        return vehicle;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLES)
                .set(VEHICLES.STATE, RecordState.DELETED.name())
                .where(VEHICLES.ID.eq(id))
                .and(VEHICLES.STATE.eq(RecordState.ACTIVE.name()))
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public VehicleDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLES).where(VEHICLES.UUID.eq(uuid))
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleDao.class);
    }

    @Override
    public VehicleDao findById(Integer id) {
        return context.selectFrom(VEHICLES).where(VEHICLES.ID.eq(id))
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleDao.class);
    }

    @Override
    public List<VehicleDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLES).where(VEHICLES.ID.in(ids)).fetchInto(VehicleDao.class);
    }

    @Override
    public List<VehicleDao> findAllActive() {
        return context.selectFrom(VEHICLES).fetchInto(VehicleDao.class);
    }

    @Override
    public VehicleDao findByPlateNumber(String plateNumber) {
        return context.selectFrom(VEHICLES).where(VEHICLES.PLATE_NUMBER.eq(plateNumber)).fetchAnyInto(VehicleDao.class);
    }

    @Override
    public List<VehicleDao> findAllByOnboardingStatus(String onboardingStatus) {
        return context.selectFrom(VEHICLES)
                .where(VEHICLES.ONBOARDING_STATUS.in(onboardingStatus))
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .fetchInto(VehicleDao.class);
    }

    @Override
    public List<VehicleDao> findAllByStatus(String status) {
        return context.selectFrom(VEHICLES)
                .where(VEHICLES.ONBOARDING_STATUS.in(VehicleOnboardingStatus.ONBOARDED.name()))
                .and(VEHICLES.STATUS.eq(status))
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .fetchInto(VehicleDao.class);
    }

    @Override
    public List<VehicleDao> findAllActiveWithPartnerId() {
        return context.selectFrom(VEHICLES)
                .where(VEHICLES.ONBOARDING_STATUS.in(VehicleOnboardingStatus.ONBOARDED.name()))
                .and(VEHICLES.PARTNER_ID.isNotNull())
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .fetchInto(VehicleDao.class);
    }

    @Override
    public List<VehicleDao> findEligibleVehicleForPartnerId(Integer partnerId) {
        return context.selectFrom(VEHICLES)
                .where(VEHICLES.ONBOARDING_STATUS.in(VehicleOnboardingStatus.ONBOARDED.name()))
                .and(VEHICLES.ID.in(
                        context.select(VEHICLE_SEGMENT_MAPPINGS.VEHICLE_ID)
                                .from(VEHICLE_SEGMENT_MAPPINGS)
                                .where(VEHICLE_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                                .and(VEHICLE_SEGMENT_MAPPINGS.VEHICLE_SEGMENT_ID.in(
                                        context.select(PARTNER_SEGMENT_MAPPINGS.VEHICLE_SEGMENT_ID)
                                                .from(PARTNER_SEGMENT_MAPPINGS)
                                                .where(PARTNER_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                                                .and(PARTNER_SEGMENT_MAPPINGS.VEHICLE_SEGMENT_ID.isNotNull())
                                                .and(PARTNER_SEGMENT_MAPPINGS.PARTNER_SEGMENT_ID.in(
                                                        context.select(PARTNER_SEGMENT_MAPPINGS.PARTNER_SEGMENT_ID)
                                                                .from(PARTNER_SEGMENT_MAPPINGS)
                                                                .where(PARTNER_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                                                                .and(PARTNER_SEGMENT_MAPPINGS.PARTNER_ID.eq(partnerId))
                                                ))

                                ))
                ))
                .and(VEHICLES.STATUS.eq(VehicleStatus.AVAILABLE.name()))
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .and(VEHICLES.STATE.eq(RecordState.ACTIVE.name()))
                .fetchInto(VehicleDao.class);
    }

    @Override
    public void updateStats(Integer vehicleId, String stats) {
        context.update(VEHICLES)
                .set(VEHICLES.STATS,stats)
                .set(VEHICLES.UPDATED_BY, ApplicationContext.getAuthUUID())
                .set(VEHICLES.UPDATED_ON, DateTime.now())
                .set(VEHICLES.API_SOURCE, ApplicationContext.getApplicationSource())
                .where(VEHICLES.ID.eq(vehicleId))
                .and(VEHICLES.IS_ACTIVE.eq(true)).execute();

        VehicleDao vehicleDao = context.selectFrom(VEHICLES)
                .where(VEHICLES.ID.eq(vehicleId))
                .and(VEHICLES.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleDao.class);
        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("VehicleUpdateEvent", vehicleDao);
    }
}
