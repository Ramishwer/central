package com.goev.central.repository.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerDao;
import com.goev.central.enums.partner.PartnerOnboardingStatus;
import com.goev.central.repository.partner.detail.PartnerRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnersRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Partners.PARTNERS;

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
                .fetchAnyInto(PartnerDao.class);
    }

    @Override
    public List<PartnerDao> findAllByOnboardingStatus(String onboardingStatus) {

        return context.selectFrom(PARTNERS)
                .where(PARTNERS.ONBOARDING_STATUS.in(onboardingStatus))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerDao.class);
    }

    @Override
    public List<PartnerDao> findAllByStatus(List<String> status) {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.ONBOARDING_STATUS.in(PartnerOnboardingStatus.ONBOARDED.name()))
                .and(PARTNERS.STATUS.in(status))
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerDao.class);
    }

    @Override
    public List<PartnerDao> findAllByStatusAndShiftIdNotNull(List<String> status) {
        return context.selectFrom(PARTNERS)
                .where(PARTNERS.ONBOARDING_STATUS.in(PartnerOnboardingStatus.ONBOARDED.name()))
                .and(PARTNERS.STATUS.in(status))
                .and(PARTNERS.PARTNER_SHIFT_ID.isNotNull())
                .and(PARTNERS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerDao.class);
    }
}
