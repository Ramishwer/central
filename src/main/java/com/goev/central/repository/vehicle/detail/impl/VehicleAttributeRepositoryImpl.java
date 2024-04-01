package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleAttributeDao;
import com.goev.central.repository.vehicle.detail.VehicleAttributeRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleAttributesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleAttributes.VEHICLE_ATTRIBUTES;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleAttributeRepositoryImpl implements VehicleAttributeRepository {
    private final DSLContext context;


    @Override
    public VehicleAttributeDao save(VehicleAttributeDao attribute) {
        VehicleAttributesRecord vehicleAttributesRecord = context.newRecord(VEHICLE_ATTRIBUTES, attribute);
        vehicleAttributesRecord.store();
        attribute.setId(vehicleAttributesRecord.getId());
        attribute.setUuid(vehicleAttributesRecord.getUuid());
        return attribute;
    }

    @Override
    public VehicleAttributeDao update(VehicleAttributeDao attribute) {
        VehicleAttributesRecord vehicleAttributesRecord = context.newRecord(VEHICLE_ATTRIBUTES, attribute);
        vehicleAttributesRecord.update();
        return attribute;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_ATTRIBUTES).set(VEHICLE_ATTRIBUTES.STATE, RecordState.DELETED.name()).where(VEHICLE_ATTRIBUTES.ID.eq(id)).execute();
    }

    @Override
    public VehicleAttributeDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_ATTRIBUTES).where(VEHICLE_ATTRIBUTES.UUID.eq(uuid)).fetchAnyInto(VehicleAttributeDao.class);
    }

    @Override
    public VehicleAttributeDao findById(Integer id) {
        return context.selectFrom(VEHICLE_ATTRIBUTES).where(VEHICLE_ATTRIBUTES.ID.eq(id)).fetchAnyInto(VehicleAttributeDao.class);
    }

    @Override
    public List<VehicleAttributeDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_ATTRIBUTES).where(VEHICLE_ATTRIBUTES.ID.in(ids)).fetchInto(VehicleAttributeDao.class);
    }

    @Override
    public List<VehicleAttributeDao> findAll() {
        return context.selectFrom(VEHICLE_ATTRIBUTES).fetchInto(VehicleAttributeDao.class);
    }
}
