package com.goev.central.repository.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerReferenceDao;
import com.goev.central.repository.partner.detail.PartnerReferenceRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerReferencesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerReferences.PARTNER_REFERENCES;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerReferenceRepositoryImpl implements PartnerReferenceRepository {

    private final DSLContext context;

    @Override
    public PartnerReferenceDao save(PartnerReferenceDao reference) {
        PartnerReferencesRecord partnerReferencesRecord = context.newRecord(PARTNER_REFERENCES, reference);
        partnerReferencesRecord.store();
        reference.setId(partnerReferencesRecord.getId());
        reference.setUuid(partnerReferencesRecord.getUuid());
        reference.setCreatedBy(partnerReferencesRecord.getCreatedBy());
        reference.setUpdatedBy(partnerReferencesRecord.getUpdatedBy());
        reference.setCreatedOn(partnerReferencesRecord.getCreatedOn());
        reference.setUpdatedOn(partnerReferencesRecord.getUpdatedOn());
        reference.setIsActive(partnerReferencesRecord.getIsActive());
        reference.setState(partnerReferencesRecord.getState());
        reference.setApiSource(partnerReferencesRecord.getApiSource());
        reference.setNotes(partnerReferencesRecord.getNotes());
        return reference;
    }

    @Override
    public PartnerReferenceDao update(PartnerReferenceDao reference) {
        PartnerReferencesRecord partnerReferencesRecord = context.newRecord(PARTNER_REFERENCES, reference);
        partnerReferencesRecord.update();


        reference.setCreatedBy(partnerReferencesRecord.getCreatedBy());
        reference.setUpdatedBy(partnerReferencesRecord.getUpdatedBy());
        reference.setCreatedOn(partnerReferencesRecord.getCreatedOn());
        reference.setUpdatedOn(partnerReferencesRecord.getUpdatedOn());
        reference.setIsActive(partnerReferencesRecord.getIsActive());
        reference.setState(partnerReferencesRecord.getState());
        reference.setApiSource(partnerReferencesRecord.getApiSource());
        reference.setNotes(partnerReferencesRecord.getNotes());
        return reference;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_REFERENCES)
                .set(PARTNER_REFERENCES.STATE, RecordState.DELETED.name())
                .where(PARTNER_REFERENCES.ID.eq(id))
                .and(PARTNER_REFERENCES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_REFERENCES.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerReferenceDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_REFERENCES).where(PARTNER_REFERENCES.UUID.eq(uuid))
                .and(PARTNER_REFERENCES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerReferenceDao.class);
    }

    @Override
    public PartnerReferenceDao findById(Integer id) {
        return context.selectFrom(PARTNER_REFERENCES).where(PARTNER_REFERENCES.ID.eq(id))
                .and(PARTNER_REFERENCES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerReferenceDao.class);
    }

    @Override
    public List<PartnerReferenceDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_REFERENCES).where(PARTNER_REFERENCES.ID.in(ids)).fetchInto(PartnerReferenceDao.class);
    }

    @Override
    public List<PartnerReferenceDao> findAllActive() {
        return context.selectFrom(PARTNER_REFERENCES).fetchInto(PartnerReferenceDao.class);
    }

    @Override
    public List<PartnerReferenceDao> findAllByPartnerId(Integer partnerId) {
        return context.selectFrom(PARTNER_REFERENCES)
                .where(PARTNER_REFERENCES.PARTNER_ID.in(partnerId))
                .fetchInto(PartnerReferenceDao.class);

    }
}
