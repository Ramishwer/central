package com.goev.central.repository.partner.detail.impl;

import com.goev.central.dao.partner.detail.PartnerDeviceDao;
import com.goev.central.repository.partner.detail.PartnerDeviceRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.PartnerDevicesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.PartnerDevices.PARTNER_DEVICES;

@Slf4j
@Repository
@AllArgsConstructor
public class PartnerDeviceRepositoryImpl implements PartnerDeviceRepository {

    private final DSLContext context;

    @Override
    public PartnerDeviceDao save(PartnerDeviceDao device) {
        PartnerDevicesRecord partnerDevicesRecord = context.newRecord(PARTNER_DEVICES, device);
        partnerDevicesRecord.store();
        device.setId(partnerDevicesRecord.getId());
        device.setUuid(partnerDevicesRecord.getUuid());
        return device;
    }

    @Override
    public PartnerDeviceDao update(PartnerDeviceDao device) {
        PartnerDevicesRecord partnerDevicesRecord = context.newRecord(PARTNER_DEVICES, device);
        partnerDevicesRecord.update();
        return device;
    }

    @Override
    public void delete(Integer id) {
        context.update(PARTNER_DEVICES).set(PARTNER_DEVICES.STATE, RecordState.DELETED.name()).where(PARTNER_DEVICES.ID.eq(id)).execute();
    }

    @Override
    public PartnerDeviceDao findByUUID(String uuid) {
        return context.selectFrom(PARTNER_DEVICES).where(PARTNER_DEVICES.UUID.eq(uuid)).fetchAnyInto(PartnerDeviceDao.class);
    }

    @Override
    public PartnerDeviceDao findById(Integer id) {
        return context.selectFrom(PARTNER_DEVICES).where(PARTNER_DEVICES.ID.eq(id)).fetchAnyInto(PartnerDeviceDao.class);
    }

    @Override
    public List<PartnerDeviceDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(PARTNER_DEVICES).where(PARTNER_DEVICES.ID.in(ids)).fetchInto(PartnerDeviceDao.class);
    }

    @Override
    public List<PartnerDeviceDao> findAll() {
        return context.selectFrom(PARTNER_DEVICES).fetchInto(PartnerDeviceDao.class);
    }
}
