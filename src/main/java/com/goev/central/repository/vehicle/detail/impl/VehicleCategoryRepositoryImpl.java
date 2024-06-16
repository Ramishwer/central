package com.goev.central.repository.vehicle.detail.impl;

import com.goev.central.dao.vehicle.detail.VehicleCategoryDao;
import com.goev.central.repository.vehicle.detail.VehicleCategoryRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleCategoriesRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleCategories.VEHICLE_CATEGORIES;

@Repository
@AllArgsConstructor
@Slf4j
public class VehicleCategoryRepositoryImpl implements VehicleCategoryRepository {
    private final DSLContext context;

    @Override
    public VehicleCategoryDao save(VehicleCategoryDao category) {
        VehicleCategoriesRecord vehicleCategoriesRecord = context.newRecord(VEHICLE_CATEGORIES, category);
        vehicleCategoriesRecord.store();
        category.setId(vehicleCategoriesRecord.getId());
        category.setUuid(vehicleCategoriesRecord.getUuid());
        category.setCreatedBy(vehicleCategoriesRecord.getCreatedBy());
        category.setUpdatedBy(vehicleCategoriesRecord.getUpdatedBy());
        category.setCreatedOn(vehicleCategoriesRecord.getCreatedOn());
        category.setUpdatedOn(vehicleCategoriesRecord.getUpdatedOn());
        category.setIsActive(vehicleCategoriesRecord.getIsActive());
        category.setState(vehicleCategoriesRecord.getState());
        category.setApiSource(vehicleCategoriesRecord.getApiSource());
        category.setNotes(vehicleCategoriesRecord.getNotes());
        return category;
    }

    @Override
    public VehicleCategoryDao update(VehicleCategoryDao category) {
        VehicleCategoriesRecord vehicleCategoriesRecord = context.newRecord(VEHICLE_CATEGORIES, category);
        vehicleCategoriesRecord.update();


        category.setCreatedBy(vehicleCategoriesRecord.getCreatedBy());
        category.setUpdatedBy(vehicleCategoriesRecord.getUpdatedBy());
        category.setCreatedOn(vehicleCategoriesRecord.getCreatedOn());
        category.setUpdatedOn(vehicleCategoriesRecord.getUpdatedOn());
        category.setIsActive(vehicleCategoriesRecord.getIsActive());
        category.setState(vehicleCategoriesRecord.getState());
        category.setApiSource(vehicleCategoriesRecord.getApiSource());
        category.setNotes(vehicleCategoriesRecord.getNotes());
        return category;
    }

    @Override
    public void delete(Integer id) {
     context.update(VEHICLE_CATEGORIES)
     .set(VEHICLE_CATEGORIES.STATE,RecordState.DELETED.name())
     .where(VEHICLE_CATEGORIES.ID.eq(id))
     .and(VEHICLE_CATEGORIES.STATE.eq(RecordState.ACTIVE.name()))
     .and(VEHICLE_CATEGORIES.IS_ACTIVE.eq(true))
     .execute();
    } 

    @Override
    public VehicleCategoryDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_CATEGORIES).where(VEHICLE_CATEGORIES.UUID.eq(uuid))
                .and(VEHICLE_CATEGORIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleCategoryDao.class);
    }

    @Override
    public VehicleCategoryDao findById(Integer id) {
        return context.selectFrom(VEHICLE_CATEGORIES).where(VEHICLE_CATEGORIES.ID.eq(id))
                .and(VEHICLE_CATEGORIES.IS_ACTIVE.eq(true))
                .fetchAnyInto(VehicleCategoryDao.class);
    }

    @Override
    public List<VehicleCategoryDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_CATEGORIES).where(VEHICLE_CATEGORIES.ID.in(ids)).fetchInto(VehicleCategoryDao.class);
    }

    @Override
    public List<VehicleCategoryDao> findAllActive() {
        return context.selectFrom(VEHICLE_CATEGORIES).fetchInto(VehicleCategoryDao.class);
    }
}
