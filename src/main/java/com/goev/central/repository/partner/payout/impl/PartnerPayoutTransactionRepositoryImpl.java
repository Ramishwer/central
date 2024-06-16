package com.goev.central.repository.partner.payout.impl;

import com.goev.central.dao.partner.payout.PartnerPayoutTransactionDao;
import com.goev.central.repository.partner.payout.PartnerPayoutTransactionRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerPayoutTransactionsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerPayoutTransactions.PARTNER_PAYOUT_TRANSACTIONS;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerPayoutTransactionRepositoryImpl implements PartnerPayoutTransactionRepository {

    private final DSLContext context;

    @Override
    public PartnerPayoutTransactionDao save(PartnerPayoutTransactionDao partnerPayoutTransaction) {
        PartnerPayoutTransactionsRecord partnerPayoutTransactionsRecord = context.newRecord(PARTNER_PAYOUT_TRANSACTIONS, partnerPayoutTransaction);
        partnerPayoutTransactionsRecord.store();
        partnerPayoutTransaction.setId(partnerPayoutTransactionsRecord.getId());
        partnerPayoutTransaction.setUuid(partnerPayoutTransaction.getUuid());
        partnerPayoutTransaction.setCreatedBy(partnerPayoutTransaction.getCreatedBy());
        partnerPayoutTransaction.setUpdatedBy(partnerPayoutTransaction.getUpdatedBy());
        partnerPayoutTransaction.setCreatedOn(partnerPayoutTransaction.getCreatedOn());
        partnerPayoutTransaction.setUpdatedOn(partnerPayoutTransaction.getUpdatedOn());
        partnerPayoutTransaction.setIsActive(partnerPayoutTransaction.getIsActive());
        partnerPayoutTransaction.setState(partnerPayoutTransaction.getState());
        partnerPayoutTransaction.setApiSource(partnerPayoutTransaction.getApiSource());
        partnerPayoutTransaction.setNotes(partnerPayoutTransaction.getNotes());
        return partnerPayoutTransaction;
    }

    @Override
    public PartnerPayoutTransactionDao update(PartnerPayoutTransactionDao partnerPayoutTransaction) {
        PartnerPayoutTransactionsRecord partnerPayoutTransactionsRecord = context.newRecord(PARTNER_PAYOUT_TRANSACTIONS, partnerPayoutTransaction);
        partnerPayoutTransactionsRecord.update();


        partnerPayoutTransaction.setCreatedBy(partnerPayoutTransactionsRecord.getCreatedBy());
        partnerPayoutTransaction.setUpdatedBy(partnerPayoutTransactionsRecord.getUpdatedBy());
        partnerPayoutTransaction.setCreatedOn(partnerPayoutTransactionsRecord.getCreatedOn());
        partnerPayoutTransaction.setUpdatedOn(partnerPayoutTransactionsRecord.getUpdatedOn());
        partnerPayoutTransaction.setIsActive(partnerPayoutTransactionsRecord.getIsActive());
        partnerPayoutTransaction.setState(partnerPayoutTransactionsRecord.getState());
        partnerPayoutTransaction.setApiSource(partnerPayoutTransactionsRecord.getApiSource());
        partnerPayoutTransaction.setNotes(partnerPayoutTransactionsRecord.getNotes());
        return partnerPayoutTransaction;
    }

    @Override
    public void delete(Integer id) {
     context.update(PARTNER_PAYOUT_TRANSACTIONS)
     .set(PARTNER_PAYOUT_TRANSACTIONS.STATE,RecordState.DELETED.name())
     .where(PARTNER_PAYOUT_TRANSACTIONS.ID.eq(id))
     .and(PARTNER_PAYOUT_TRANSACTIONS.STATE.eq(RecordState.ACTIVE.name()))
     .and(PARTNER_PAYOUT_TRANSACTIONS.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public PartnerPayoutTransactionDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_PAYOUT_TRANSACTIONS).where(PARTNER_PAYOUT_TRANSACTIONS.UUID.eq(uuid))
                .and(PARTNER_PAYOUT_TRANSACTIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerPayoutTransactionDao.class);
    }

    @Override
    public PartnerPayoutTransactionDao findById(Integer id) {
        return context.selectFrom(PARTNER_PAYOUT_TRANSACTIONS).where(PARTNER_PAYOUT_TRANSACTIONS.ID.eq(id))
                .and(PARTNER_PAYOUT_TRANSACTIONS.IS_ACTIVE.eq(true))
                .fetchAnyInto(PartnerPayoutTransactionDao.class);
    }

    @Override
    public List<PartnerPayoutTransactionDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_PAYOUT_TRANSACTIONS).where(PARTNER_PAYOUT_TRANSACTIONS.ID.in(ids)).fetchInto(PartnerPayoutTransactionDao.class);
    }

    @Override
    public List<PartnerPayoutTransactionDao> findAllActive() {
        return context.selectFrom(PARTNER_PAYOUT_TRANSACTIONS).fetchInto(PartnerPayoutTransactionDao.class);
    }

    @Override
    public List<PartnerPayoutTransactionDao> findAllByPartnerPayoutId(Integer id) {
        return context.selectFrom(PARTNER_PAYOUT_TRANSACTIONS).where(PARTNER_PAYOUT_TRANSACTIONS.PARTNER_PAYOUT_ID.eq(id)).fetchInto(PartnerPayoutTransactionDao.class);
    }
}
