package com.goev.central.repository.user.impl;

import com.goev.central.dao.user.UserDao;
import com.goev.central.dao.user.UserDao;
import com.goev.central.repository.user.UserRepository;
import com.goev.record.central.tables.records.UsersRecord;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.Users.USERS;

@Slf4j
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Autowired
    private DSLContext context;
    @Override
    public UserDao save(UserDao user) {
        UsersRecord record =  context.newRecord(USERS,user);
        record.store();
        user.setId(record.getId());
        return user;
    }

    @Override
    public UserDao update(UserDao user) {
        UsersRecord record =  context.newRecord(USERS,user);
        record.update();
        return user;
    }

    @Override
    public void delete(Integer id) {
        context.update(USERS).set(USERS.STATE,"DELETED").where(USERS.ID.eq(id)).execute();
    }

    @Override
    public UserDao findByUUID(String uuid) {
        return context.selectFrom(USERS).where(USERS.UUID.eq(uuid)).fetchAnyInto(UserDao.class);
    }

    @Override
    public UserDao findById(Integer id) {
        return context.selectFrom(USERS).where(USERS.ID.eq(id)).fetchAnyInto(UserDao.class);
    }

    @Override
    public List<UserDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(USERS).where(USERS.ID.in(ids)).fetchInto(UserDao.class);
    }

    @Override
    public List<UserDao> findAll() {
        return context.selectFrom(USERS).fetchInto(UserDao.class);
    }
}
