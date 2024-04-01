package com.goev.central.repository.partner.document.impl;

import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
import com.goev.central.repository.partner.document.PartnerDocumentTypeRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerDocumentTypesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerDocumentTypes.PARTNER_DOCUMENT_TYPES;

@Repository
@AllArgsConstructor
@Slf4j
public class PartnerDocumentTypeRepositoryImpl implements PartnerDocumentTypeRepository {
    private final DSLContext context;

    @Override
    public PartnerDocumentTypeDao save(PartnerDocumentTypeDao partnerDocumentType) {
        PartnerDocumentTypesRecord partnersDocumentTypeRecord = context.newRecord(PARTNER_DOCUMENT_TYPES, partnerDocumentType);
        partnersDocumentTypeRecord.store();
        partnerDocumentType.setId(partnersDocumentTypeRecord.getId());
        partnerDocumentType.setUuid(partnersDocumentTypeRecord.getUuid());
        return partnerDocumentType;
    }

    @Override
    public PartnerDocumentTypeDao update(PartnerDocumentTypeDao partnerDocumentType) {
        PartnerDocumentTypesRecord partnersDocumentTypeRecord = context.newRecord(PARTNER_DOCUMENT_TYPES, partnerDocumentType);
        partnersDocumentTypeRecord.update();
        return partnerDocumentType;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_DOCUMENT_TYPES).set(PARTNER_DOCUMENT_TYPES.STATE, RecordState.DELETED.name()).where(PARTNER_DOCUMENT_TYPES.ID.eq(id)).execute();
    }

    @Override
    public PartnerDocumentTypeDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_DOCUMENT_TYPES).where(PARTNER_DOCUMENT_TYPES.UUID.eq(uuid)).fetchAnyInto(PartnerDocumentTypeDao.class);
    }

    @Override
    public PartnerDocumentTypeDao findById(Integer id) {
        return context.selectFrom(PARTNER_DOCUMENT_TYPES).where(PARTNER_DOCUMENT_TYPES.ID.eq(id)).fetchAnyInto(PartnerDocumentTypeDao.class);
    }

    @Override
    public List<PartnerDocumentTypeDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_DOCUMENT_TYPES).where(PARTNER_DOCUMENT_TYPES.ID.in(ids)).fetchInto(PartnerDocumentTypeDao.class);
    }

    @Override
    public List<PartnerDocumentTypeDao> findAll() {
        return context.selectFrom(PARTNER_DOCUMENT_TYPES).fetchInto(PartnerDocumentTypeDao.class);
    }


}