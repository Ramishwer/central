package com.goev.central.repository.challan.impl;

import com.goev.central.dao.challan.ChallanDao;
import com.goev.central.repository.challan.ChallanRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.ChallansRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Challans.CHALLANS;

@Slf4j
@Repository
@AllArgsConstructor
public class ChallanRepositoryImpl implements ChallanRepository {

    private final DSLContext context;

    @Override
    public ChallanDao save(ChallanDao challanDao) {
        ChallansRecord challansRecord = context.newRecord(CHALLANS, challanDao);
        challansRecord.store();
        challanDao.setId(challansRecord.getId());
        challanDao.setUuid(challansRecord.getUuid());
        challanDao.setCreatedBy(challansRecord.getCreatedBy());
        challanDao.setUpdatedBy(challansRecord.getUpdatedBy());
        challanDao.setCreatedOn(challansRecord.getCreatedOn());
        challanDao.setUpdatedOn(challansRecord.getUpdatedOn());
        challanDao.setIsActive(challansRecord.getIsActive());
        challanDao.setState(challansRecord.getState());
        challanDao.setApiSource(challansRecord.getApiSource());
        challanDao.setNotes(challansRecord.getNotes());
        return challanDao;
    }

    @Override
    public ChallanDao update(ChallanDao challanDao) {
        ChallansRecord challansRecord = context.newRecord(CHALLANS, challanDao);
        challansRecord.update();


        challanDao.setCreatedBy(challansRecord.getCreatedBy());
        challanDao.setUpdatedBy(challansRecord.getUpdatedBy());
        challanDao.setCreatedOn(challansRecord.getCreatedOn());
        challanDao.setUpdatedOn(challansRecord.getUpdatedOn());
        challanDao.setIsActive(challansRecord.getIsActive());
        challanDao.setState(challansRecord.getState());
        challanDao.setApiSource(challansRecord.getApiSource());
        challanDao.setNotes(challansRecord.getNotes());
        return challanDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(CHALLANS)
                .set(CHALLANS.STATE, RecordState.DELETED.name())
                .where(CHALLANS.ID.eq(id))
                .and(CHALLANS.STATE.eq(RecordState.ACTIVE.name()))
                .and(CHALLANS.IS_ACTIVE.eq(true))
                .execute();
    }

    @Override
    public ChallanDao findByUUID(String uuid) {
        return context.selectFrom(CHALLANS).where(CHALLANS.UUID.eq(uuid))
                .and(CHALLANS.IS_ACTIVE.eq(true))
                .fetchAnyInto(ChallanDao.class);
    }

    @Override
    public ChallanDao findById(Integer id) {
        return context.selectFrom(CHALLANS).where(CHALLANS.ID.eq(id))
                .and(CHALLANS.IS_ACTIVE.eq(true))
                .fetchAnyInto(ChallanDao.class);
    }

    @Override
    public List<ChallanDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CHALLANS).where(CHALLANS.ID.in(ids)).fetchInto(ChallanDao.class);
    }

    @Override
    public List<ChallanDao> findAllActive() {
        return context.selectFrom(CHALLANS).fetchInto(ChallanDao.class);
    }
}
