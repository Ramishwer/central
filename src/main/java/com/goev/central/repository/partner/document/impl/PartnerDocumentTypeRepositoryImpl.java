package com.goev.central.repository.partner.document.impl;

import com.goev.central.dao.partner.document.PartnerDocumentTypeDao;
import com.goev.central.repository.partner.document.PartnerDocumentTypeRepository;
import com.goev.central.utilities.EventExecutorUtils;
import com.goev.central.utilities.RequestContext;
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
    private final EventExecutorUtils eventExecutor;

    @Override
    public PartnerDocumentTypeDao save(PartnerDocumentTypeDao partnerDocumentType) {
        PartnerDocumentTypesRecord partnersDocumentTypeRecord = context.newRecord(PARTNER_DOCUMENT_TYPES, partnerDocumentType);
        partnersDocumentTypeRecord.store();
        partnerDocumentType.setId(partnersDocumentTypeRecord.getId());
        partnerDocumentType.setUuid(partnersDocumentTypeRecord.getUuid());
        partnerDocumentType.setCreatedBy(partnersDocumentTypeRecord.getCreatedBy());
        partnerDocumentType.setUpdatedBy(partnersDocumentTypeRecord.getUpdatedBy());
        partnerDocumentType.setCreatedOn(partnersDocumentTypeRecord.getCreatedOn());
        partnerDocumentType.setUpdatedOn(partnersDocumentTypeRecord.getUpdatedOn());
        partnerDocumentType.setIsActive(partnersDocumentTypeRecord.getIsActive());
        partnerDocumentType.setState(partnersDocumentTypeRecord.getState());
        partnerDocumentType.setApiSource(partnersDocumentTypeRecord.getApiSource());
        partnerDocumentType.setNotes(partnersDocumentTypeRecord.getNotes());

        if ("API".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerDocumentTypeSaveEvent", partnerDocumentType);

        return partnerDocumentType;
    }

    @Override
    public PartnerDocumentTypeDao update(PartnerDocumentTypeDao partnerDocumentType) {
        PartnerDocumentTypesRecord partnersDocumentTypeRecord = context.newRecord(PARTNER_DOCUMENT_TYPES, partnerDocumentType);
        partnersDocumentTypeRecord.update();


        partnerDocumentType.setCreatedBy(partnersDocumentTypeRecord.getCreatedBy());
        partnerDocumentType.setUpdatedBy(partnersDocumentTypeRecord.getUpdatedBy());
        partnerDocumentType.setCreatedOn(partnersDocumentTypeRecord.getCreatedOn());
        partnerDocumentType.setUpdatedOn(partnersDocumentTypeRecord.getUpdatedOn());
        partnerDocumentType.setIsActive(partnersDocumentTypeRecord.getIsActive());
        partnerDocumentType.setState(partnersDocumentTypeRecord.getState());
        partnerDocumentType.setApiSource(partnersDocumentTypeRecord.getApiSource());
        partnerDocumentType.setNotes(partnersDocumentTypeRecord.getNotes());
        if ("API".equals(RequestContext.getRequestSource()))
            eventExecutor.fireEvent("PartnerDocumentTypeUpdateEvent", partnerDocumentType);

        return partnerDocumentType;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_DOCUMENT_TYPES).set(PARTNER_DOCUMENT_TYPES.STATE, RecordState.DELETED.name())
                .where(PARTNER_DOCUMENT_TYPES.ID.eq(id))
                .and(PARTNER_DOCUMENT_TYPES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_DOCUMENT_TYPES.IS_ACTIVE.eq(true))
                .execute();

        PartnerDocumentTypesRecord partnersDocumentTypeRecord = context.selectFrom(PARTNER_DOCUMENT_TYPES)
                .where(PARTNER_DOCUMENT_TYPES.ID.eq(id))
                .and(PARTNER_DOCUMENT_TYPES.STATE.eq(RecordState.ACTIVE.name())).fetchOne();

        if (partnersDocumentTypeRecord != null)
            eventExecutor.fireEvent("PartnerDocumentTypeUpdateEvent", partnersDocumentTypeRecord);

    }

    @Override
    public PartnerDocumentTypeDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_DOCUMENT_TYPES)
                .where(PARTNER_DOCUMENT_TYPES.UUID.eq(uuid))
                .and(PARTNER_DOCUMENT_TYPES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDocumentTypeDao.class);
    }

    @Override
    public PartnerDocumentTypeDao findById(Integer id) {
        return context.selectFrom(PARTNER_DOCUMENT_TYPES)
                .where(PARTNER_DOCUMENT_TYPES.ID.eq(id))
                .and(PARTNER_DOCUMENT_TYPES.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerDocumentTypeDao.class);
    }

    @Override
    public List<PartnerDocumentTypeDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_DOCUMENT_TYPES)
                .where(PARTNER_DOCUMENT_TYPES.ID.in(ids))
                .and(PARTNER_DOCUMENT_TYPES.IS_ACTIVE.eq(true))
                .fetchInto(PartnerDocumentTypeDao.class);
    }

    @Override
    public List<PartnerDocumentTypeDao> findAllActive() {
        return context.selectFrom(PARTNER_DOCUMENT_TYPES)
                .where(PARTNER_DOCUMENT_TYPES.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_DOCUMENT_TYPES.IS_ACTIVE.eq(true))
                .fetchInto(PartnerDocumentTypeDao.class);
    }

    @Override
    public List<PartnerDocumentTypeDao> findAllDeleted() {
        return context.selectFrom(PARTNER_DOCUMENT_TYPES)
                .where(PARTNER_DOCUMENT_TYPES.STATE.eq(RecordState.DELETED.name()))
                .and(PARTNER_DOCUMENT_TYPES.IS_ACTIVE.eq(true))
                .fetchInto(PartnerDocumentTypeDao.class);
    }


}