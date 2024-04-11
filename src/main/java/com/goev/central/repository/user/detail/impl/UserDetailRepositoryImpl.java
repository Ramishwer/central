package com.goev.central.repository.user.detail.impl;

import com.goev.central.dao.user.detail.UserDetailDao;
import com.goev.central.repository.user.detail.UserDetailRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.UserDetailsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.UserDetails.USER_DETAILS;

@Repository
@AllArgsConstructor
@Slf4j
public class UserDetailRepositoryImpl implements UserDetailRepository {
    private final DSLContext context;

    @Override
    public UserDetailDao save(UserDetailDao userDetails) {
        UserDetailsRecord userDetailsRecord = context.newRecord(USER_DETAILS, userDetails);
        userDetailsRecord.store();
        userDetails.setId(userDetailsRecord.getId());
        userDetails.setUuid(userDetails.getUuid());
        return userDetails;
    }

    @Override
    public UserDetailDao update(UserDetailDao userDetails) {
        UserDetailsRecord userDetailsRecord = context.newRecord(USER_DETAILS, userDetails);
        userDetailsRecord.update();
        return userDetails;
    }

    @Override
    public void delete(Integer id) {
        context.update(USER_DETAILS).set(USER_DETAILS.STATE, RecordState.DELETED.name()).where(USER_DETAILS.ID.eq(id)).execute();
    }

    @Override
    public UserDetailDao findByUUID(String uuid) {
        return context.selectFrom(USER_DETAILS).where(USER_DETAILS.UUID.eq(uuid)).fetchAnyInto(UserDetailDao.class);
    }

    @Override
    public UserDetailDao findById(Integer id) {
        return context.selectFrom(USER_DETAILS).where(USER_DETAILS.ID.eq(id)).fetchAnyInto(UserDetailDao.class);
    }

    @Override
    public List<UserDetailDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(USER_DETAILS).where(USER_DETAILS.ID.in(ids)).fetchInto(UserDetailDao.class);
    }

    @Override
    public List<UserDetailDao> findAll() {
        return context.selectFrom(USER_DETAILS).fetchInto(UserDetailDao.class);
    }
}
