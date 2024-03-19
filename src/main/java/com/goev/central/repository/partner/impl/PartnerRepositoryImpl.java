package com.goev.central.repository.partner.impl;

import com.goev.central.dao.partner.PartnerDao;
import com.goev.central.repository.partner.PartnerRepository;
import com.goev.record.central.tables.records.PartnersRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Partners.PARTNERS;

@Slf4j
@Repository
public class PartnerRepositoryImpl implements PartnerRepository {

    @Autowired
    private DSLContext context;
    @Override
    public PartnerDao save(PartnerDao partner) {
        PartnersRecord record =  context.newRecord(PARTNERS,partner);
        record.store();
        partner.setId(record.getId());
        return partner;
    }

    @Override
    public PartnerDao update(PartnerDao partner) {
        PartnersRecord record =  context.newRecord(PARTNERS,partner);
        record.update();
        return partner;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNERS).set(PARTNERS.STATE,"DELETED").where(PARTNERS.ID.eq(id)).execute();
    }

    @Override
    public PartnerDao findByUUID(String uuid) {
        return context.selectFrom(PARTNERS).where(PARTNERS.UUID.eq(uuid)).fetchAnyInto(PartnerDao.class);
    }

    @Override
    public PartnerDao findById(Integer id) {
        return context.selectFrom(PARTNERS).where(PARTNERS.ID.eq(id)).fetchAnyInto(PartnerDao.class);
    }

    @Override
    public List<PartnerDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNERS).where(PARTNERS.ID.in(ids)).fetchInto(PartnerDao.class);
    }

    @Override
    public List<PartnerDao> findAll() {
        return context.selectFrom(PARTNERS).fetchInto(PartnerDao.class);
    }
}
