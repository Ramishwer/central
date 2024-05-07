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
        return challanDao;
    }

    @Override
    public ChallanDao update(ChallanDao challanDao) {
        ChallansRecord challansRecord = context.newRecord(CHALLANS, challanDao);
        challansRecord.update();
        return challanDao;
    }

    @Override
    public void delete(Integer id) {
        context.update(CHALLANS).set(CHALLANS.STATE, RecordState.DELETED.name()).where(CHALLANS.ID.eq(id)).execute();
    }

    @Override
    public ChallanDao findByUUID(String uuid) {
        return context.selectFrom(CHALLANS).where(CHALLANS.UUID.eq(uuid)).fetchAnyInto(ChallanDao.class);
    }

    @Override
    public ChallanDao findById(Integer id) {
        return context.selectFrom(CHALLANS).where(CHALLANS.ID.eq(id)).fetchAnyInto(ChallanDao.class);
    }

    @Override
    public List<ChallanDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CHALLANS).where(CHALLANS.ID.in(ids)).fetchInto(ChallanDao.class);
    }

    @Override
    public List<ChallanDao> findAll() {
        return context.selectFrom(CHALLANS).fetchInto(ChallanDao.class);
    }
}
