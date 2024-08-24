package com.goev.central.repository.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerAccountDetailDao;
import com.goev.central.repository.partner.detail.PartnerAccountDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerAccountDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerAccountDetails.PARTNER_ACCOUNT_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerAccountDetailRepositoryImpl implements PartnerAccountDetailRepository {

    private final DSLContext context;

    @Override
    public PartnerAccountDetailDao save(PartnerAccountDetailDao accountDetail) {
        PartnerAccountDetailsRecord partnerAccountDetailsRecord = context.newRecord(PARTNER_ACCOUNT_DETAILS, accountDetail);
        partnerAccountDetailsRecord.store();
        accountDetail.setId(partnerAccountDetailsRecord.getId());
        accountDetail.setUuid(partnerAccountDetailsRecord.getUuid());
        accountDetail.setCreatedBy(partnerAccountDetailsRecord.getCreatedBy());
        accountDetail.setUpdatedBy(partnerAccountDetailsRecord.getUpdatedBy());
        accountDetail.setCreatedOn(partnerAccountDetailsRecord.getCreatedOn());
        accountDetail.setUpdatedOn(partnerAccountDetailsRecord.getUpdatedOn());
        accountDetail.setIsActive(partnerAccountDetailsRecord.getIsActive());
        accountDetail.setState(partnerAccountDetailsRecord.getState());
        accountDetail.setApiSource(partnerAccountDetailsRecord.getApiSource());
        accountDetail.setNotes(partnerAccountDetailsRecord.getNotes());
        return accountDetail;
    }

    @Override
    public PartnerAccountDetailDao update(PartnerAccountDetailDao accountDetail) {
        PartnerAccountDetailsRecord partnerAccountDetailsRecord = context.newRecord(PARTNER_ACCOUNT_DETAILS, accountDetail);
        partnerAccountDetailsRecord.update();


        accountDetail.setCreatedBy(partnerAccountDetailsRecord.getCreatedBy());
        accountDetail.setUpdatedBy(partnerAccountDetailsRecord.getUpdatedBy());
        accountDetail.setCreatedOn(partnerAccountDetailsRecord.getCreatedOn());
        accountDetail.setUpdatedOn(partnerAccountDetailsRecord.getUpdatedOn());
        accountDetail.setIsActive(partnerAccountDetailsRecord.getIsActive());
        accountDetail.setState(partnerAccountDetailsRecord.getState());
        accountDetail.setApiSource(partnerAccountDetailsRecord.getApiSource());
        accountDetail.setNotes(partnerAccountDetailsRecord.getNotes());
        return accountDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_ACCOUNT_DETAILS)
                .set(PARTNER_ACCOUNT_DETAILS.STATE, RecordState.DELETED.name())
                .where(PARTNER_ACCOUNT_DETAILS.ID.eq(id))
                .and(PARTNER_ACCOUNT_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_ACCOUNT_DETAILS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerAccountDetailDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_ACCOUNT_DETAILS).where(PARTNER_ACCOUNT_DETAILS.UUID.eq(uuid))
                .and(PARTNER_ACCOUNT_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerAccountDetailDao.class);
    }

    @Override
    public PartnerAccountDetailDao findById(Integer id) {
        return context.selectFrom(PARTNER_ACCOUNT_DETAILS).where(PARTNER_ACCOUNT_DETAILS.ID.eq(id))
                .and(PARTNER_ACCOUNT_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerAccountDetailDao.class);
    }

    @Override
    public List<PartnerAccountDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_ACCOUNT_DETAILS).where(PARTNER_ACCOUNT_DETAILS.ID.in(ids)).fetchInto(PartnerAccountDetailDao.class);
    }

    @Override
    public List<PartnerAccountDetailDao> findAllActive() {
        return context.selectFrom(PARTNER_ACCOUNT_DETAILS).fetchInto(PartnerAccountDetailDao.class);
    }

    @Override
    public List<PartnerAccountDetailDao> findAllByPartnerId(Integer partnerId) {
        return context.selectFrom(PARTNER_ACCOUNT_DETAILS)
                .where(PARTNER_ACCOUNT_DETAILS.PARTNER_ID.in(partnerId))
                .and(PARTNER_ACCOUNT_DETAILS.IS_ACTIVE.eq(true))
                .and(PARTNER_ACCOUNT_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchInto(PartnerAccountDetailDao.class);
    }
}
