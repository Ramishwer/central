package com.goev.central.repository.vehicle.ticket.impl;

import com.goev.central.dao.vehicle.ticket.VehicleTicketDao;
import com.goev.central.repository.vehicle.ticket.VehicleTicketRepository;
import com.goev.lib.enums.RecordState;
import com.goev.record.central.tables.records.VehicleTicketsRecord;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.goev.record.central.tables.VehicleTickets.VEHICLE_TICKETS;

@Slf4j
@Repository
@AllArgsConstructor
public class VehicleTicketRepositoryImpl implements VehicleTicketRepository {

    private final DSLContext context;

    @Override
    public VehicleTicketDao save(VehicleTicketDao vehicleTicket) {
        VehicleTicketsRecord vehicleTicketsRecord = context.newRecord(VEHICLE_TICKETS, vehicleTicket);
        vehicleTicketsRecord.store();
        vehicleTicket.setId(vehicleTicketsRecord.getId());
        vehicleTicket.setUuid(vehicleTicket.getUuid());
        return vehicleTicket;
    }

    @Override
    public VehicleTicketDao update(VehicleTicketDao vehicleTicket) {
        VehicleTicketsRecord vehicleTicketsRecord = context.newRecord(VEHICLE_TICKETS, vehicleTicket);
        vehicleTicketsRecord.update();
        return vehicleTicket;
    }

    @Override
    public void delete(Integer id) {
        context.update(VEHICLE_TICKETS).set(VEHICLE_TICKETS.STATE, RecordState.DELETED.name()).where(VEHICLE_TICKETS.ID.eq(id)).execute();
    }

    @Override
    public VehicleTicketDao findByUUID(String uuid) {
        return context.selectFrom(VEHICLE_TICKETS).where(VEHICLE_TICKETS.UUID.eq(uuid)).fetchAnyInto(VehicleTicketDao.class);
    }

    @Override
    public VehicleTicketDao findById(Integer id) {
        return context.selectFrom(VEHICLE_TICKETS).where(VEHICLE_TICKETS.ID.eq(id)).fetchAnyInto(VehicleTicketDao.class);
    }

    @Override
    public List<VehicleTicketDao> findAllByIds(List<Integer> ids) {
        return context.selectFrom(VEHICLE_TICKETS).where(VEHICLE_TICKETS.ID.in(ids)).fetchInto(VehicleTicketDao.class);
    }

    @Override
    public List<VehicleTicketDao> findAll() {
        return context.selectFrom(VEHICLE_TICKETS).fetchInto(VehicleTicketDao.class);
    }
}
