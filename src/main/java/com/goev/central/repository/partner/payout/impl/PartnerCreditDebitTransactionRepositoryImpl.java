package com.goev.central.repository.partner.payout.impl;

import com.goev.central.dao.partner.payout.PartnerCreditDebitTransactionDao;
import com.goev.central.repository.partner.payout.PartnerCreditDebitTransactionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerCreditDebitTransactionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerCreditDebitTransactions.PARTNER_CREDIT_DEBIT_TRANSACTIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerCreditDebitTransactionRepositoryImpl implements PartnerCreditDebitTransactionRepository {

    private final DSLContext context;

    @Override
    public PartnerCreditDebitTransactionDao save(PartnerCreditDebitTransactionDao partnerCreditDebitTransaction) {
        PartnerCreditDebitTransactionsRecord partnerCreditDebitTransactionsRecord = context.newRecord(PARTNER_CREDIT_DEBIT_TRANSACTIONS, partnerCreditDebitTransaction);
        partnerCreditDebitTransactionsRecord.store();
        partnerCreditDebitTransaction.setId(partnerCreditDebitTransactionsRecord.getId());
        partnerCreditDebitTransaction.setUuid(partnerCreditDebitTransactionsRecord.getUuid());
        partnerCreditDebitTransaction.setCreatedBy(partnerCreditDebitTransactionsRecord.getCreatedBy());
        partnerCreditDebitTransaction.setUpdatedBy(partnerCreditDebitTransactionsRecord.getUpdatedBy());
        partnerCreditDebitTransaction.setCreatedOn(partnerCreditDebitTransactionsRecord.getCreatedOn());
        partnerCreditDebitTransaction.setUpdatedOn(partnerCreditDebitTransactionsRecord.getUpdatedOn());
        partnerCreditDebitTransaction.setIsActive(partnerCreditDebitTransactionsRecord.getIsActive());
        partnerCreditDebitTransaction.setState(partnerCreditDebitTransactionsRecord.getState());
        partnerCreditDebitTransaction.setApiSource(partnerCreditDebitTransactionsRecord.getApiSource());
        partnerCreditDebitTransaction.setNotes(partnerCreditDebitTransactionsRecord.getNotes());
        return partnerCreditDebitTransaction;
    }

    @Override
    public PartnerCreditDebitTransactionDao update(PartnerCreditDebitTransactionDao partnerCreditDebitTransaction) {
        PartnerCreditDebitTransactionsRecord partnerCreditDebitTransactionsRecord = context.newRecord(PARTNER_CREDIT_DEBIT_TRANSACTIONS, partnerCreditDebitTransaction);
        partnerCreditDebitTransactionsRecord.update();


        partnerCreditDebitTransaction.setCreatedBy(partnerCreditDebitTransactionsRecord.getCreatedBy());
        partnerCreditDebitTransaction.setUpdatedBy(partnerCreditDebitTransactionsRecord.getUpdatedBy());
        partnerCreditDebitTransaction.setCreatedOn(partnerCreditDebitTransactionsRecord.getCreatedOn());
        partnerCreditDebitTransaction.setUpdatedOn(partnerCreditDebitTransactionsRecord.getUpdatedOn());
        partnerCreditDebitTransaction.setIsActive(partnerCreditDebitTransactionsRecord.getIsActive());
        partnerCreditDebitTransaction.setState(partnerCreditDebitTransactionsRecord.getState());
        partnerCreditDebitTransaction.setApiSource(partnerCreditDebitTransactionsRecord.getApiSource());
        partnerCreditDebitTransaction.setNotes(partnerCreditDebitTransactionsRecord.getNotes());
        return partnerCreditDebitTransaction;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_CREDIT_DEBIT_TRANSACTIONS)
                .set(PARTNER_CREDIT_DEBIT_TRANSACTIONS.STATE, RecordState.DELETED.name())
                .where(PARTNER_CREDIT_DEBIT_TRANSACTIONS.ID.eq(id))
                .and(PARTNER_CREDIT_DEBIT_TRANSACTIONS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_CREDIT_DEBIT_TRANSACTIONS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public PartnerCreditDebitTransactionDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_CREDIT_DEBIT_TRANSACTIONS).where(PARTNER_CREDIT_DEBIT_TRANSACTIONS.UUID.eq(uuid))
                .and(PARTNER_CREDIT_DEBIT_TRANSACTIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerCreditDebitTransactionDao.class);
    }

    @Override
    public PartnerCreditDebitTransactionDao findById(Integer id) {
        return context.selectFrom(PARTNER_CREDIT_DEBIT_TRANSACTIONS).where(PARTNER_CREDIT_DEBIT_TRANSACTIONS.ID.eq(id))
                .and(PARTNER_CREDIT_DEBIT_TRANSACTIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerCreditDebitTransactionDao.class);
    }

    @Override
    public List<PartnerCreditDebitTransactionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_CREDIT_DEBIT_TRANSACTIONS).where(PARTNER_CREDIT_DEBIT_TRANSACTIONS.ID.in(ids)).fetchInto(PartnerCreditDebitTransactionDao.class);
    }

    @Override
    public List<PartnerCreditDebitTransactionDao> findAllActive() {
        return context.selectFrom(PARTNER_CREDIT_DEBIT_TRANSACTIONS).fetchInto(PartnerCreditDebitTransactionDao.class);
    }

    @Override
    public List<PartnerCreditDebitTransactionDao> findAllByPartnerPayoutIdAndPartnerId(Integer payoutId,Integer partnerId) {
        return context.selectFrom(PARTNER_CREDIT_DEBIT_TRANSACTIONS)
                .where(PARTNER_CREDIT_DEBIT_TRANSACTIONS.PARTNER_PAYOUT_ID.eq(payoutId))
                .and(PARTNER_CREDIT_DEBIT_TRANSACTIONS.PARTNER_ID.eq(partnerId))
                .and(PARTNER_CREDIT_DEBIT_TRANSACTIONS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_CREDIT_DEBIT_TRANSACTIONS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerCreditDebitTransactionDao.class);
    }

    @Override
    public List<PartnerCreditDebitTransactionDao> findAllByPartnerPayoutIdAndPartnerIdAndTransactionTimeBetween(Integer payoutId, Integer partnerId, DateTime start, DateTime end) {
        return context.selectFrom(PARTNER_CREDIT_DEBIT_TRANSACTIONS)
                .where(PARTNER_CREDIT_DEBIT_TRANSACTIONS.PARTNER_PAYOUT_ID.eq(payoutId))
                .and(PARTNER_CREDIT_DEBIT_TRANSACTIONS.PARTNER_ID.eq(partnerId))
                .and(PARTNER_CREDIT_DEBIT_TRANSACTIONS.TRANSACTION_TIME.between(start,end))
                .and(PARTNER_CREDIT_DEBIT_TRANSACTIONS.STATE.eq(RecordState.ACTIVE.name()))
                .and(PARTNER_CREDIT_DEBIT_TRANSACTIONS.IS_ACTIVE.eq(true))
                .fetchInto(PartnerCreditDebitTransactionDao.class);
    }


}
