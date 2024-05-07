package com.goev.central.repository.customer.detail.impl;

import com.goev.central.dao.customer.detail.CustomerDeviceDao;
import com.goev.central.repository.customer.detail.CustomerDeviceRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.CustomerDevicesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.CustomerDevices.CUSTOMER_DEVICES;

@Slf4j
@Repository
@AllArgsConstructor
public class CustomerDeviceRepositoryImpl implements CustomerDeviceRepository {

    private final DSLContext context;

    @Override
    public CustomerDeviceDao save(CustomerDeviceDao customerDevice) {
        CustomerDevicesRecord customerDevicesRecord = context.newRecord(CUSTOMER_DEVICES, customerDevice);
        customerDevicesRecord.store();
        customerDevice.setId(customerDevicesRecord.getId());
        customerDevice.setUuid(customerDevice.getUuid());
        return customerDevice;
    }

    @Override
    public CustomerDeviceDao update(CustomerDeviceDao customerDevice) {
        CustomerDevicesRecord customerDevicesRecord = context.newRecord(CUSTOMER_DEVICES, customerDevice);
        customerDevicesRecord.update();
        return customerDevice;
    }

    @Override
    public void delete(Integer id) {
        context.update(CUSTOMER_DEVICES).set(CUSTOMER_DEVICES.STATE, RecordState.DELETED.name()).where(CUSTOMER_DEVICES.ID.eq(id)).execute();
    }

    @Override
    public CustomerDeviceDao findByUUID(String uuid) {
        return context.selectFrom(CUSTOMER_DEVICES).where(CUSTOMER_DEVICES.UUID.eq(uuid)).fetchAnyInto(CustomerDeviceDao.class);
    }

    @Override
    public CustomerDeviceDao findById(Integer id) {
        return context.selectFrom(CUSTOMER_DEVICES).where(CUSTOMER_DEVICES.ID.eq(id)).fetchAnyInto(CustomerDeviceDao.class);
    }

    @Override
    public List<CustomerDeviceDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(CUSTOMER_DEVICES).where(CUSTOMER_DEVICES.ID.in(ids)).fetchInto(CustomerDeviceDao.class);
    }

    @Override
    public List<CustomerDeviceDao> findAll() {
        return context.selectFrom(CUSTOMER_DEVICES).fetchInto(CustomerDeviceDao.class);
    }
}
