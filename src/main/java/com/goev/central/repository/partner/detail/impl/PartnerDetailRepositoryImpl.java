package com.goev.central.repository.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerDetailDao;
import com.goev.central.repository.partner.detail.PartnerDetailRepository;
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

    @Override
    public PartnerDetailDao save(PartnerDetailDao detail) {
        PartnerDetailsRecord partnerDetailsRecord = context.newRecord(PARTNER_DETAILS, detail);
        partnerDetailsRecord.store();
        detail.setId(partnerDetailsRecord.getId());
        detail.setUuid(partnerDetailsRecord.getUuid());
        return detail;
    }

    @Override
    public PartnerDetailDao update(PartnerDetailDao detail) {
        PartnerDetailsRecord partnerDetailsRecord = context.newRecord(PARTNER_DETAILS, detail);
        partnerDetailsRecord.update();
        return detail;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_DETAILS).set(PARTNER_DETAILS.STATE, RecordState.DELETED.name()).where(PARTNER_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public PartnerDetailDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_DETAILS).where(PARTNER_DETAILS.UUID.eq(uuid)).fetchAnyInto(PartnerDetailDao.class);
    }

    @Override
    public PartnerDetailDao findById(Integer id) {
        return context.selectFrom(PARTNER_DETAILS).where(PARTNER_DETAILS.ID.eq(id)).fetchAnyInto(PartnerDetailDao.class);
    }

    @Override
    public List<PartnerDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_DETAILS).where(PARTNER_DETAILS.ID.in(ids)).fetchInto(PartnerDetailDao.class);
    }

    @Override
    public List<PartnerDetailDao> findAll() {
        return context.selectFrom(PARTNER_DETAILS).fetchInto(PartnerDetailDao.class);
    }
}
