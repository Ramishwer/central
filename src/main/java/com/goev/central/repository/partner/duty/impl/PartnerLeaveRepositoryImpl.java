package com.goev.central.repository.partner.duty.impl;

import com.goev.central.dao.partner.duty.PartnerLeaveDao;
import com.goev.central.repository.partner.duty.PartnerLeaveRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerLeavesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerLeaves.PARTNER_LEAVES;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerLeaveRepositoryImpl implements PartnerLeaveRepository {

    private final DSLContext context;

    @Override
    public PartnerLeaveDao save(PartnerLeaveDao partnerLeaveDao) {
        PartnerLeavesRecord partnerLeavesRecord = context.newRecord(PARTNER_LEAVES, partnerLeaveDao);
        partnerLeavesRecord.store();
        partnerLeaveDao.setId(partnerLeavesRecord.getId());
        partnerLeaveDao.setUuid(partnerLeavesRecord.getUuid());
        partnerLeaveDao.setCreatedBy(partnerLeavesRecord.getCreatedBy());
        partnerLeaveDao.setUpdatedBy(partnerLeavesRecord.getUpdatedBy());
        partnerLeaveDao.setCreatedOn(partnerLeavesRecord.getCreatedOn());
        partnerLeaveDao.setUpdatedOn(partnerLeavesRecord.getUpdatedOn());
        partnerLeaveDao.setIsActive(partnerLeavesRecord.getIsActive());
        partnerLeaveDao.setState(partnerLeavesRecord.getState());
        partnerLeaveDao.setApiSource(partnerLeavesRecord.getApiSource());
        partnerLeaveDao.setNotes(partnerLeavesRecord.getNotes());
        return partnerLeaveDao;
    }

    @Override
    public PartnerLeaveDao update(PartnerLeaveDao partnerLeaveDao) {
        PartnerLeavesRecord partnerLeavesRecord = context.newRecord(PARTNER_LEAVES, partnerLeaveDao);
        partnerLeavesRecord.update();


        partnerLeaveDao.setCreatedBy(partnerLeavesRecord.getCreatedBy());
        partnerLeaveDao.setUpdatedBy(partnerLeavesRecord.getUpdatedBy());
        partnerLeaveDao.setCreatedOn(partnerLeavesRecord.getCreatedOn());
        partnerLeaveDao.setUpdatedOn(partnerLeavesRecord.getUpdatedOn());
        partnerLeaveDao.setIsActive(partnerLeavesRecord.getIsActive());
        partnerLeaveDao.setState(partnerLeavesRecord.getState());
        partnerLeaveDao.setApiSource(partnerLeavesRecord.getApiSource());
        partnerLeaveDao.setNotes(partnerLeavesRecord.getNotes());
        return partnerLeaveDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_LEAVES)
                .set(PARTNER_LEAVES.STATE, RecordState.DELETED.name())
                .where(PARTNER_LEAVES.ID.eq(id))
                .and(PARTNER_LEAVES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_LEAVES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerLeaveDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_LEAVES).where(PARTNER_LEAVES.UUID.eq(uuid))
                .and(PARTNER_LEAVES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerLeaveDao.class);
    }

    @Override
    public PartnerLeaveDao findById(Integer id) {
        return context.selectFrom(PARTNER_LEAVES).where(PARTNER_LEAVES.ID.eq(id))
                .and(PARTNER_LEAVES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerLeaveDao.class);
    }

    @Override
    public List<PartnerLeaveDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_LEAVES).where(PARTNER_LEAVES.ID.in(ids)).fetchInto(PartnerLeaveDao.class);
    }

    @Override
    public List<PartnerLeaveDao> findAllActive() {
        return context.selectFrom(PARTNER_LEAVES).fetchInto(PartnerLeaveDao.class);
    }

    @Override
    public List<PartnerLeaveDao> findAllByPartnerId(Integer id) {
        return context.selectFrom(PARTNER_LEAVES).where(PARTNER_LEAVES.PARTNER_ID.eq(id)).fetchInto(PartnerLeaveDao.class);
    }
}
