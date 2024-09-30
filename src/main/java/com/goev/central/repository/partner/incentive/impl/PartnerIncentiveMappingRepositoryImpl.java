package com.goev.central.repository.partner.incentive.impl;

import com.goev.central.dao.partner.incentive.PartnerIncentiveMappingDao;
import com.goev.central.repository.partner.incentive.PartnerIncentiveMappingRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerIncentiveMappingsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerIncentiveMappings.PARTNER_INCENTIVE_MAPPINGS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerIncentiveMappingRepositoryImpl implements PartnerIncentiveMappingRepository {

    private final DSLContext context;

    @Override
    public PartnerIncentiveMappingDao save(PartnerIncentiveMappingDao partnerIncentiveMappingDao) {
        PartnerIncentiveMappingsRecord partnerIncentiveMappingsRecord = context.newRecord(PARTNER_INCENTIVE_MAPPINGS, partnerIncentiveMappingDao);
        partnerIncentiveMappingsRecord.store();
        partnerIncentiveMappingDao.setId(partnerIncentiveMappingsRecord.getId());
        partnerIncentiveMappingDao.setUuid(partnerIncentiveMappingsRecord.getUuid());
        partnerIncentiveMappingDao.setCreatedBy(partnerIncentiveMappingsRecord.getCreatedBy());
        partnerIncentiveMappingDao.setUpdatedBy(partnerIncentiveMappingsRecord.getUpdatedBy());
        partnerIncentiveMappingDao.setCreatedOn(partnerIncentiveMappingsRecord.getCreatedOn());
        partnerIncentiveMappingDao.setUpdatedOn(partnerIncentiveMappingsRecord.getUpdatedOn());
        partnerIncentiveMappingDao.setIsActive(partnerIncentiveMappingsRecord.getIsActive());
        partnerIncentiveMappingDao.setState(partnerIncentiveMappingsRecord.getState());
        partnerIncentiveMappingDao.setApiSource(partnerIncentiveMappingsRecord.getApiSource());
        partnerIncentiveMappingDao.setNotes(partnerIncentiveMappingsRecord.getNotes());
        return partnerIncentiveMappingDao;
    }

    @Override
    public PartnerIncentiveMappingDao update(PartnerIncentiveMappingDao partnerIncentiveMappingDao) {
        PartnerIncentiveMappingsRecord partnerIncentiveMappingsRecord = context.newRecord(PARTNER_INCENTIVE_MAPPINGS, partnerIncentiveMappingDao);
        partnerIncentiveMappingsRecord.update();


        partnerIncentiveMappingDao.setCreatedBy(partnerIncentiveMappingsRecord.getCreatedBy());
        partnerIncentiveMappingDao.setUpdatedBy(partnerIncentiveMappingsRecord.getUpdatedBy());
        partnerIncentiveMappingDao.setCreatedOn(partnerIncentiveMappingsRecord.getCreatedOn());
        partnerIncentiveMappingDao.setUpdatedOn(partnerIncentiveMappingsRecord.getUpdatedOn());
        partnerIncentiveMappingDao.setIsActive(partnerIncentiveMappingsRecord.getIsActive());
        partnerIncentiveMappingDao.setState(partnerIncentiveMappingsRecord.getState());
        partnerIncentiveMappingDao.setApiSource(partnerIncentiveMappingsRecord.getApiSource());
        partnerIncentiveMappingDao.setNotes(partnerIncentiveMappingsRecord.getNotes());
        return partnerIncentiveMappingDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_INCENTIVE_MAPPINGS)
                .set(PARTNER_INCENTIVE_MAPPINGS.STATE, RecordState.DELETED.name())
                .where(PARTNER_INCENTIVE_MAPPINGS.ID.eq(id))
                .and(PARTNER_INCENTIVE_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_INCENTIVE_MAPPINGS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerIncentiveMappingDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_INCENTIVE_MAPPINGS).where(PARTNER_INCENTIVE_MAPPINGS.UUID.eq(uuid))
                .and(PARTNER_INCENTIVE_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerIncentiveMappingDao.class);
    }

    @Override
    public PartnerIncentiveMappingDao findById(Integer id) {
        return context.selectFrom(PARTNER_INCENTIVE_MAPPINGS).where(PARTNER_INCENTIVE_MAPPINGS.ID.eq(id))
                .and(PARTNER_INCENTIVE_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerIncentiveMappingDao.class);
    }

    @Override
    public List<PartnerIncentiveMappingDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_INCENTIVE_MAPPINGS).where(PARTNER_INCENTIVE_MAPPINGS.ID.in(ids)).fetchInto(PartnerIncentiveMappingDao.class);
    }

    @Override
    public List<PartnerIncentiveMappingDao> findAllActive() {
        return context.selectFrom(PARTNER_INCENTIVE_MAPPINGS)
                .where(PARTNER_INCENTIVE_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_INCENTIVE_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerIncentiveMappingDao.class);
    }

    @Override
    public List<PartnerIncentiveMappingDao> findAllByPartnerId(Integer partnerId) {
        return context.selectFrom(PARTNER_INCENTIVE_MAPPINGS).where(PARTNER_INCENTIVE_MAPPINGS.PARTNER_ID.eq(partnerId))
                .and(PARTNER_INCENTIVE_MAPPINGS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_INCENTIVE_MAPPINGS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerIncentiveMappingDao.class);
    }
}
