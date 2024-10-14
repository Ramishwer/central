package com.goev.central.repository.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.enums.partner.PartnerOnboardingStatus;
import com.goev.central.enums.partner.PartnerStatus;
import com.goev.central.enums.partner.PartnerSubStatus;
import com.goev.central.enums.vehicle.VehicleOnboardingStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.lib.utilities.ApplicationContext;
import com.goev.record.central.tables.records.PartnersRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.Tables.*;
import static com.goev.record.central.tables.Partners.PARTNERS;
import static com.goev.record.central.tables.Vehicles.VEHICLES;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerRepositoryImpl implements PartnerRepository {

    private final DSLContext context;

    private final EventExecutorUtils eventExecutor;

    @Override
    public PartnerDao save(PartnerDao partner) {
        PartnersRecord partnersRecord = context.newRecord(PARTNERS, partner);
        partnersRecord.store();

        partner.setId(partnersRecord.getId());
        partner.setUuid(partnersRecord.getUuid());
        partner.setCreatedBy(partnersRecord.getCreatedBy());
        partner.setUpdatedBy(partnersRecord.getUpdatedBy());
        partner.setCreatedOn(partnersRecord.getCreatedOn());
        partner.setUpdatedOn(partnersRecord.getUpdatedOn());
        partner.setIsActive(partnersRecord.getIsActive());
        partner.setState(partnersRecord.getState());
        partner.setApiSource(partnersRecord.getApiSource());
        partner.setNotes(partnersRecord.getNotes());

        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerSaveEvent", partner);

        return partner;
    }

    @Override
    public PartnerDao update(PartnerDao partner) {
        PartnersRecord partnersRecord = context.newRecord(PARTNERS, partner);
        partnersRecord.update();


        partner.setCreatedBy(partnersRecord.getCreatedBy());
        partner.setUpdatedBy(partnersRecord.getUpdatedBy());
        partner.setCreatedOn(partnersRecord.getCreatedOn());
        partner.setUpdatedOn(partnersRecord.getUpdatedOn());
        partner.setIsActive(partnersRecord.getIsActive());
        partner.setState(partnersRecord.getState());
        partner.setApiSource(partnersRecord.getApiSource());
        partner.setNotes(partnersRecord.getNotes());
        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerUpdateEvent", partner);
        return partner;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNERS).set(PARTNERS.STATE, RecordState.DELETED.name()).where(PARTNERS.ID.eq(id))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .and(PARTNERS.STATE.eq(RecordState.ACTIVE.name()))
                .execute();
    }

    @Override
    public PartnerDao findByUUID(String uuid) {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.UUID.eq(uuid))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDao.class);
    }

    @Override
    public PartnerDao findById(Integer id) {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.ID.eq(id))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDao.class);
    }

    @Override
    public List<PartnerDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.ID.in(ids))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerDao.class);
    }

    @Override
    public List<PartnerDao> findAllActive() {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerDao.class);
    }

    @Override
    public PartnerDao findByPhoneNumber(String phoneNumber) {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.PHONE_NUMBER.eq(phoneNumber))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .and(PARTNERS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchAnyInto(PartnerDao.class);
    }

    @Override
    public List<PartnerDao> findAllByOnboardingStatus(String onboardingStatus) {

        return context.selectFrom(PARTNERS)
                .where(PARTNERS.ONBOARDING_STATUS.in(onboardingStatus))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .and(PARTNERS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchInto(PartnerDao.class);
    }

    @Override
    public List<PartnerDao> findAllByStatus(List<String> status) {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.ONBOARDING_STATUS.in(PartnerOnboardingStatus.ONBOARDED.name()))
                .and(PARTNERS.STATUS.in(status))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .and(PARTNERS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchInto(PartnerDao.class);
    }

    @Override
    public List<PartnerDao> findAllByStatusAndShiftIdNotNull(List<String> status) {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.ONBOARDING_STATUS.in(PartnerOnboardingStatus.ONBOARDED.name()))
                .and(PARTNERS.STATUS.in(status))
                .and(PARTNERS.PARTNER_SHIFT_ID.isNotNull())
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .and(PARTNERS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchInto(PartnerDao.class);
    }

    @Override
    public PartnerDao findByVehicleId(Integer vehicleId) {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.VEHICLE_ID.eq(vehicleId))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .and(PARTNERS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchAnyInto(PartnerDao.class);
    }

    @Override
    public List<PartnerDao> findAllUnAssignedPartners() {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.VEHICLE_ID.isNull())
                .and(PARTNERS.STATUS.eq(PartnerStatus.ON_DUTY.name()))
                .and(PARTNERS.SUB_STATUS.eq(PartnerSubStatus.VEHICLE_NOT_ALLOTTED.name()))
                .and(PARTNERS.ONBOARDING_STATUS.in(PartnerOnboardingStatus.ONBOARDED.name()))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .and(PARTNERS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchInto(PartnerDao.class);
    }


    @Override
    public List<PartnerDao> findAllEligiblePartnersForBusinessSegment(Integer businessSegmentId) {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.VEHICLE_ID.isNotNull())
                .and(PARTNERS.ID.in(
                        context.select(PARTNER_SEGMENT_MAPPINGS.PARTNER_ID)
                                .from(PARTNER_SEGMENT_MAPPINGS)
                                .where(PARTNER_SEGMENT_MAPPINGS.PARTNER_SEGMENT_ID.in(
                                       context.select(BUSINESS_SEGMENT_MAPPINGS.PARTNER_SEGMENT_ID)
                                               .from(BUSINESS_SEGMENT_MAPPINGS)
                                               .where(BUSINESS_SEGMENT_MAPPINGS.BUSINESS_SEGMENT_ID.eq(businessSegmentId))
                                               .and(BUSINESS_SEGMENT_MAPPINGS.PARTNER_SEGMENT_ID.isNotNull())
                                               .and(BUSINESS_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                                               .and(BUSINESS_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))

                                ))
                                .and(PARTNER_SEGMENT_MAPPINGS.IS_ACTIVE.eq(true))
                                .and(PARTNER_SEGMENT_MAPPINGS.PARTNER_ID.isNotNull())
                                .and(PARTNER_SEGMENT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                ))
                .and(PARTNERS.STATUS.eq(PartnerStatus.ONLINE.name()))
                .and(PARTNERS.SUB_STATUS.eq(PartnerSubStatus.NO_BOOKING.name()))
                .and(PARTNERS.ONBOARDING_STATUS.in(PartnerOnboardingStatus.ONBOARDED.name()))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .and(PARTNERS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchInto(PartnerDao.class);
    }

    @Override
    public void updateBookingDetails(Integer partnerId, String bookingDetails) {
        context.update(PARTNERS)
                .set(PARTNERS.BOOKING_DETAILS,bookingDetails)
                .set(PARTNERS.UPDATED_BY, ApplicationContext.getAuthUUID())
                .set(PARTNERS.UPDATED_ON, DateTime.now())
                .set(PARTNERS.API_SOURCE, ApplicationContext.getApplicationSource())
                .where(PARTNERS.ID.eq(partnerId))
                .and(PARTNERS.IS_ACTIVE.eq(true)).execute();

        PartnerDao partner = context.selectFrom(PARTNERS)
                .where(PARTNERS.ID.eq(partnerId))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDao.class);
        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerUpdateEvent", partner);
    }

    @Override
    public void updateStats(Integer partnerId, String stats) {
        context.update(PARTNERS)
                .set(PARTNERS.STATS,stats)
                .set(PARTNERS.UPDATED_BY, ApplicationContext.getAuthUUID())
                .set(PARTNERS.UPDATED_ON, DateTime.now())
                .set(PARTNERS.API_SOURCE, ApplicationContext.getApplicationSource())
                .where(PARTNERS.ID.eq(partnerId))
                .and(PARTNERS.IS_ACTIVE.eq(true)).execute();

        PartnerDao partner = context.selectFrom(PARTNERS)
                .where(PARTNERS.ID.eq(partnerId))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDao.class);
        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerUpdateEvent", partner);
    }

    @Override
    public void updateVehicleDetails(Integer partnerId, String vehicleDetails) {
        context.update(PARTNERS)
                .set(PARTNERS.VEHICLE_DETAILS,vehicleDetails)
                .set(PARTNERS.UPDATED_BY, ApplicationContext.getAuthUUID())
                .set(PARTNERS.UPDATED_ON, DateTime.now())
                .set(PARTNERS.API_SOURCE, ApplicationContext.getApplicationSource())
                .where(PARTNERS.ID.eq(partnerId))
                .and(PARTNERS.IS_ACTIVE.eq(true)).execute();

        PartnerDao partner = context.selectFrom(PARTNERS)
                .where(PARTNERS.ID.eq(partnerId))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDao.class);
        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerUpdateEvent", partner);
    }
}
