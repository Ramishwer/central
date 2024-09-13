package com.goev.central.repository.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerSegmentDao;
import com.goev.central.repository.partner.detail.PartnerSegmentRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerSegmentsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerSegments.PARTNER_SEGMENTS;

@Repository
@AllArgsConstructor
@Slf4j
public class PartnerSegmentRepositoryImpl implements PartnerSegmentRepository {
    private final DSLContext context;

    @Override
    public PartnerSegmentDao save(PartnerSegmentDao segment) {
        PartnerSegmentsRecord partnerSegmentsRecord = context.newRecord(PARTNER_SEGMENTS, segment);
        partnerSegmentsRecord.store();
        segment.setId(partnerSegmentsRecord.getId());
        segment.setUuid(partnerSegmentsRecord.getUuid());
        segment.setCreatedBy(partnerSegmentsRecord.getCreatedBy());
        segment.setUpdatedBy(partnerSegmentsRecord.getUpdatedBy());
        segment.setCreatedOn(partnerSegmentsRecord.getCreatedOn());
        segment.setUpdatedOn(partnerSegmentsRecord.getUpdatedOn());
        segment.setIsActive(partnerSegmentsRecord.getIsActive());
        segment.setState(partnerSegmentsRecord.getState());
        segment.setApiSource(partnerSegmentsRecord.getApiSource());
        segment.setNotes(partnerSegmentsRecord.getNotes());
        return segment;
    }

    @Override
    public PartnerSegmentDao update(PartnerSegmentDao segment) {
        PartnerSegmentsRecord partnerSegmentsRecord = context.newRecord(PARTNER_SEGMENTS, segment);
        partnerSegmentsRecord.update();


        segment.setCreatedBy(partnerSegmentsRecord.getCreatedBy());
        segment.setUpdatedBy(partnerSegmentsRecord.getUpdatedBy());
        segment.setCreatedOn(partnerSegmentsRecord.getCreatedOn());
        segment.setUpdatedOn(partnerSegmentsRecord.getUpdatedOn());
        segment.setIsActive(partnerSegmentsRecord.getIsActive());
        segment.setState(partnerSegmentsRecord.getState());
        segment.setApiSource(partnerSegmentsRecord.getApiSource());
        segment.setNotes(partnerSegmentsRecord.getNotes());
        return segment;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_SEGMENTS)
                .set(PARTNER_SEGMENTS.STATE, RecordState.DELETED.name())
                .where(PARTNER_SEGMENTS.ID.eq(id))
                .and(PARTNER_SEGMENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_SEGMENTS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerSegmentDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_SEGMENTS).where(PARTNER_SEGMENTS.UUID.eq(uuid))
                .and(PARTNER_SEGMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerSegmentDao.class);
    }

    @Override
    public PartnerSegmentDao findById(Integer id) {
        return context.selectFrom(PARTNER_SEGMENTS).where(PARTNER_SEGMENTS.ID.eq(id))
                .and(PARTNER_SEGMENTS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerSegmentDao.class);
    }

    @Override
    public List<PartnerSegmentDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_SEGMENTS).where(PARTNER_SEGMENTS.ID.in(ids)).fetchInto(PartnerSegmentDao.class);
    }

    @Override
    public List<PartnerSegmentDao> findAllActive() {
        return context.selectFrom(PARTNER_SEGMENTS)
                .where(PARTNER_SEGMENTS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_SEGMENTS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerSegmentDao.class);
    }
}
