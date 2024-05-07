package com.goev.central.repository.partner.passbook.impl;

import com.goev.central.dao.partner.passbook.PartnerPassbookDetailDao;
import com.goev.central.repository.partner.passbook.PartnerPassbookDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerPassbookDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerPassbookDetails.PARTNER_PASSBOOK_DETAILS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerPassbookDetailRepositoryImpl implements PartnerPassbookDetailRepository {

    private final DSLContext context;

    @Override
    public PartnerPassbookDetailDao save(PartnerPassbookDetailDao partnerPassbookDetail) {
        PartnerPassbookDetailsRecord partnerPassbookDetailsRecord = context.newRecord(PARTNER_PASSBOOK_DETAILS, partnerPassbookDetail);
        partnerPassbookDetailsRecord.store();
        partnerPassbookDetail.setId(partnerPassbookDetailsRecord.getId());
        partnerPassbookDetail.setUuid(partnerPassbookDetail.getUuid());
        return partnerPassbookDetail;
    }

    @Override
    public PartnerPassbookDetailDao update(PartnerPassbookDetailDao partnerPassbookDetail) {
        PartnerPassbookDetailsRecord partnerPassbookDetailsRecord = context.newRecord(PARTNER_PASSBOOK_DETAILS, partnerPassbookDetail);
        partnerPassbookDetailsRecord.update();
        return partnerPassbookDetail;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_PASSBOOK_DETAILS).set(PARTNER_PASSBOOK_DETAILS.STATE, RecordState.DELETED.name()).where(PARTNER_PASSBOOK_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public PartnerPassbookDetailDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_PASSBOOK_DETAILS).where(PARTNER_PASSBOOK_DETAILS.UUID.eq(uuid)).fetchAnyInto(PartnerPassbookDetailDao.class);
    }

    @Override
    public PartnerPassbookDetailDao findById(Integer id) {
        return context.selectFrom(PARTNER_PASSBOOK_DETAILS).where(PARTNER_PASSBOOK_DETAILS.ID.eq(id)).fetchAnyInto(PartnerPassbookDetailDao.class);
    }

    @Override
    public List<PartnerPassbookDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_PASSBOOK_DETAILS).where(PARTNER_PASSBOOK_DETAILS.ID.in(ids)).fetchInto(PartnerPassbookDetailDao.class);
    }

    @Override
    public List<PartnerPassbookDetailDao> findAll() {
        return context.selectFrom(PARTNER_PASSBOOK_DETAILS).fetchInto(PartnerPassbookDetailDao.class);
    }

    @Override
    public PartnerPassbookDetailDao findByPartnerId(Integer id) {
        return context.selectFrom(PARTNER_PASSBOOK_DETAILS).where(PARTNER_PASSBOOK_DETAILS.PARTNER_ID.eq(id)).fetchAnyInto(PartnerPassbookDetailDao.class);
    }
}
