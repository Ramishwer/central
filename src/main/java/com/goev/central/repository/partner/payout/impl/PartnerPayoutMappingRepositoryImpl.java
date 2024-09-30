package com.goev.central.repository.partner.payout.impl;

import com.goev.central.dao.partner.payout.PartnerPayoutMappingDao;
import com.goev.central.repository.partner.payout.PartnerPayoutMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerPayoutMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerPayoutMappings.PARTNER_PAYOUT_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerPayoutMappingRepositoryImpl implements PartnerPayoutMappingRepository {

    private final DSLContext context;

    @Override
    public PartnerPayoutMappingDao save(PartnerPayoutMappingDao partnerPayoutMappingDao) {
        PartnerPayoutMappingsRecord partnerPayoutMappingsRecord = context.newRecord(PARTNER_PAYOUT_MAPPINGS, partnerPayoutMappingDao);
        partnerPayoutMappingsRecord.store();
        partnerPayoutMappingDao.setId(partnerPayoutMappingsRecord.getId());
        partnerPayoutMappingDao.setUuid(partnerPayoutMappingsRecord.getUuid());
        partnerPayoutMappingDao.setCreatedBy(partnerPayoutMappingsRecord.getCreatedBy());
        partnerPayoutMappingDao.setUpdatedBy(partnerPayoutMappingsRecord.getUpdatedBy());
        partnerPayoutMappingDao.setCreatedOn(partnerPayoutMappingsRecord.getCreatedOn());
        partnerPayoutMappingDao.setUpdatedOn(partnerPayoutMappingsRecord.getUpdatedOn());
        partnerPayoutMappingDao.setIsActive(partnerPayoutMappingsRecord.getIsActive());
        partnerPayoutMappingDao.setState(partnerPayoutMappingsRecord.getState());
        partnerPayoutMappingDao.setApiSource(partnerPayoutMappingsRecord.getApiSource());
        partnerPayoutMappingDao.setNotes(partnerPayoutMappingsRecord.getNotes());
        return partnerPayoutMappingDao;
    }

    @Override
    public PartnerPayoutMappingDao update(PartnerPayoutMappingDao partnerPayoutMappingDao) {
        PartnerPayoutMappingsRecord partnerPayoutMappingsRecord = context.newRecord(PARTNER_PAYOUT_MAPPINGS, partnerPayoutMappingDao);
        partnerPayoutMappingsRecord.update();


        partnerPayoutMappingDao.setCreatedBy(partnerPayoutMappingsRecord.getCreatedBy());
        partnerPayoutMappingDao.setUpdatedBy(partnerPayoutMappingsRecord.getUpdatedBy());
        partnerPayoutMappingDao.setCreatedOn(partnerPayoutMappingsRecord.getCreatedOn());
        partnerPayoutMappingDao.setUpdatedOn(partnerPayoutMappingsRecord.getUpdatedOn());
        partnerPayoutMappingDao.setIsActive(partnerPayoutMappingsRecord.getIsActive());
        partnerPayoutMappingDao.setState(partnerPayoutMappingsRecord.getState());
        partnerPayoutMappingDao.setApiSource(partnerPayoutMappingsRecord.getApiSource());
        partnerPayoutMappingDao.setNotes(partnerPayoutMappingsRecord.getNotes());
        return partnerPayoutMappingDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_PAYOUT_MAPPINGS)
                .set(PARTNER_PAYOUT_MAPPINGS.STATE, RecordState.DELETED.name())
                .where(PARTNER_PAYOUT_MAPPINGS.ID.eq(id))
                .and(PARTNER_PAYOUT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_PAYOUT_MAPPINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerPayoutMappingDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_PAYOUT_MAPPINGS).where(PARTNER_PAYOUT_MAPPINGS.UUID.eq(uuid))
                .and(PARTNER_PAYOUT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerPayoutMappingDao.class);
    }

    @Override
    public PartnerPayoutMappingDao findById(Integer id) {
        return context.selectFrom(PARTNER_PAYOUT_MAPPINGS).where(PARTNER_PAYOUT_MAPPINGS.ID.eq(id))
                .and(PARTNER_PAYOUT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerPayoutMappingDao.class);
    }

    @Override
    public List<PartnerPayoutMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_PAYOUT_MAPPINGS).where(PARTNER_PAYOUT_MAPPINGS.ID.in(ids)).fetchInto(PartnerPayoutMappingDao.class);
    }

    @Override
    public List<PartnerPayoutMappingDao> findAllActive() {
        return context.selectFrom(PARTNER_PAYOUT_MAPPINGS)
                .where(PARTNER_PAYOUT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_PAYOUT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerPayoutMappingDao.class);
    }

    @Override
    public List<PartnerPayoutMappingDao> findAllByPartnerId(Integer partnerId) {
        return context.selectFrom(PARTNER_PAYOUT_MAPPINGS).where(PARTNER_PAYOUT_MAPPINGS.PARTNER_ID.eq(partnerId))
                .and(PARTNER_PAYOUT_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_PAYOUT_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerPayoutMappingDao.class);
    }
}
