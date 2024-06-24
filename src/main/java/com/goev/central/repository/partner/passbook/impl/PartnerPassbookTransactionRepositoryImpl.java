package com.goev.central.repository.partner.passbook.impl;

import com.goev.central.dao.partner.passbook.PartnerPassbookTransactionDao;
import com.goev.central.repository.partner.passbook.PartnerPassbookTransactionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerPassbookTransactionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerPassbookTransactions.PARTNER_PASSBOOK_TRANSACTIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerPassbookTransactionRepositoryImpl implements PartnerPassbookTransactionRepository {

    private final DSLContext context;

    @Override
    public PartnerPassbookTransactionDao save(PartnerPassbookTransactionDao partnerPassbookTransaction) {
        PartnerPassbookTransactionsRecord partnerPassbookTransactionsRecord = context.newRecord(PARTNER_PASSBOOK_TRANSACTIONS, partnerPassbookTransaction);
        partnerPassbookTransactionsRecord.store();
        partnerPassbookTransaction.setId(partnerPassbookTransactionsRecord.getId());
        partnerPassbookTransaction.setUuid(partnerPassbookTransactionsRecord.getUuid());
        partnerPassbookTransaction.setCreatedBy(partnerPassbookTransactionsRecord.getCreatedBy());
        partnerPassbookTransaction.setUpdatedBy(partnerPassbookTransactionsRecord.getUpdatedBy());
        partnerPassbookTransaction.setCreatedOn(partnerPassbookTransactionsRecord.getCreatedOn());
        partnerPassbookTransaction.setUpdatedOn(partnerPassbookTransactionsRecord.getUpdatedOn());
        partnerPassbookTransaction.setIsActive(partnerPassbookTransactionsRecord.getIsActive());
        partnerPassbookTransaction.setState(partnerPassbookTransactionsRecord.getState());
        partnerPassbookTransaction.setApiSource(partnerPassbookTransactionsRecord.getApiSource());
        partnerPassbookTransaction.setNotes(partnerPassbookTransactionsRecord.getNotes());
        return partnerPassbookTransaction;
    }

    @Override
    public PartnerPassbookTransactionDao update(PartnerPassbookTransactionDao partnerPassbookTransaction) {
        PartnerPassbookTransactionsRecord partnerPassbookTransactionsRecord = context.newRecord(PARTNER_PASSBOOK_TRANSACTIONS, partnerPassbookTransaction);
        partnerPassbookTransactionsRecord.update();


        partnerPassbookTransaction.setCreatedBy(partnerPassbookTransactionsRecord.getCreatedBy());
        partnerPassbookTransaction.setUpdatedBy(partnerPassbookTransactionsRecord.getUpdatedBy());
        partnerPassbookTransaction.setCreatedOn(partnerPassbookTransactionsRecord.getCreatedOn());
        partnerPassbookTransaction.setUpdatedOn(partnerPassbookTransactionsRecord.getUpdatedOn());
        partnerPassbookTransaction.setIsActive(partnerPassbookTransactionsRecord.getIsActive());
        partnerPassbookTransaction.setState(partnerPassbookTransactionsRecord.getState());
        partnerPassbookTransaction.setApiSource(partnerPassbookTransactionsRecord.getApiSource());
        partnerPassbookTransaction.setNotes(partnerPassbookTransactionsRecord.getNotes());
        return partnerPassbookTransaction;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_PASSBOOK_TRANSACTIONS)
                .set(PARTNER_PASSBOOK_TRANSACTIONS.STATE, RecordState.DELETED.name())
                .where(PARTNER_PASSBOOK_TRANSACTIONS.ID.eq(id))
                .and(PARTNER_PASSBOOK_TRANSACTIONS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_PASSBOOK_TRANSACTIONS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerPassbookTransactionDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_PASSBOOK_TRANSACTIONS).where(PARTNER_PASSBOOK_TRANSACTIONS.UUID.eq(uuid))
                .and(PARTNER_PASSBOOK_TRANSACTIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerPassbookTransactionDao.class);
    }

    @Override
    public PartnerPassbookTransactionDao findById(Integer id) {
        return context.selectFrom(PARTNER_PASSBOOK_TRANSACTIONS).where(PARTNER_PASSBOOK_TRANSACTIONS.ID.eq(id))
                .and(PARTNER_PASSBOOK_TRANSACTIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerPassbookTransactionDao.class);
    }

    @Override
    public List<PartnerPassbookTransactionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_PASSBOOK_TRANSACTIONS).where(PARTNER_PASSBOOK_TRANSACTIONS.ID.in(ids)).fetchInto(PartnerPassbookTransactionDao.class);
    }

    @Override
    public List<PartnerPassbookTransactionDao> findAllActive() {
        return context.selectFrom(PARTNER_PASSBOOK_TRANSACTIONS).fetchInto(PartnerPassbookTransactionDao.class);
    }
}
