package com.goev.central.repository.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerDetailDao;
import com.goev.central.repository.partner.detail.PartnerDetailRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerDetails.PARTNER_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerDetailRepositoryImpl implements PartnerDetailRepository {

    private final DSLContext context;
    private final EventExecutorUtils eventExecutor;

    @Override
    public PartnerDetailDao save(PartnerDetailDao detail) {
        PartnerDetailsRecord partnerDetailsRecord = context.newRecord(PARTNER_DETAILS, detail);
        partnerDetailsRecord.store();
        detail.setId(partnerDetailsRecord.getId());
        detail.setUuid(partnerDetailsRecord.getUuid());
        detail.setCreatedBy(partnerDetailsRecord.getCreatedBy());
        detail.setUpdatedBy(partnerDetailsRecord.getUpdatedBy());
        detail.setCreatedOn(partnerDetailsRecord.getCreatedOn());
        detail.setUpdatedOn(partnerDetailsRecord.getUpdatedOn());
        detail.setIsActive(partnerDetailsRecord.getIsActive());
        detail.setState(partnerDetailsRecord.getState());
        detail.setApiSource(partnerDetailsRecord.getApiSource());
        detail.setNotes(partnerDetailsRecord.getNotes());
        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerDetailSaveEvent", detail);
        return detail;
    }

    @Override
    public PartnerDetailDao update(PartnerDetailDao detail) {
        PartnerDetailsRecord partnerDetailsRecord = context.newRecord(PARTNER_DETAILS, detail);
        partnerDetailsRecord.update();


        detail.setCreatedBy(partnerDetailsRecord.getCreatedBy());
        detail.setUpdatedBy(partnerDetailsRecord.getUpdatedBy());
        detail.setCreatedOn(partnerDetailsRecord.getCreatedOn());
        detail.setUpdatedOn(partnerDetailsRecord.getUpdatedOn());
        detail.setIsActive(partnerDetailsRecord.getIsActive());
        detail.setState(partnerDetailsRecord.getState());
        detail.setApiSource(partnerDetailsRecord.getApiSource());
        detail.setNotes(partnerDetailsRecord.getNotes());

        if (!"EVENT".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerDetailUpdateEvent", detail);

        return detail;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_DETAILS).set(PARTNER_DETAILS.STATE, RecordState.DELETED.name())
                .where(PARTNER_DETAILS.ID.eq(id))
                .and(PARTNER_DETAILS.IS_ACTIVE.eq(true))
                .and(PARTNER_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .execute();
    }

    @Override
    public PartnerDetailDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_DETAILS)
                .where(PARTNER_DETAILS.UUID.eq(uuid))
                .and(PARTNER_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDetailDao.class);
    }

    @Override
    public PartnerDetailDao findById(Integer id) {
        return context.selectFrom(PARTNER_DETAILS)
                .where(PARTNER_DETAILS.ID.eq(id))
                .and(PARTNER_DETAILS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDetailDao.class);
    }

    @Override
    public List<PartnerDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_DETAILS)
                .where(PARTNER_DETAILS.ID.in(ids))
                .and(PARTNER_DETAILS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerDetailDao.class);
    }


    @Override
    public PartnerDetailDao findByAadhaarCardNumber(String aadhaarCardNumber) {
        return context.selectFrom(PARTNER_DETAILS)
                .where(PARTNER_DETAILS.AADHAAR_CARD_NUMBER.eq(aadhaarCardNumber))
                .and(PARTNER_DETAILS.IS_ACTIVE.eq(true))
                .and(PARTNER_DETAILS.STATE.eq(RecordState.ACTIVE.name()))
                .fetchOneInto(PartnerDetailDao.class);
    }
}
