package com.goev.central.service.vehicle.ticket.impl;

import com.goev.central.dao.vehicle.ticket.VehicleTicketDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.vehicle.ticket.VehicleTicketDto;
import com.goev.central.repository.vehicle.ticket.VehicleTicketRepository;
import com.goev.central.service.vehicle.ticket.VehicleTicketService;
import com.goev.lib.exceptions.ResponseException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class VehicleTicketServiceImpl implements VehicleTicketService {

    private final VehicleTicketRepository vehicleTicketRepository;

    @Override
    public PaginatedResponseDto<VehicleTicketDto> getVehicleTickets(String vehicleUUID) {
        PaginatedResponseDto<VehicleTicketDto> result = PaginatedResponseDto.<VehicleTicketDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<VehicleTicketDao> vehicleTicketDaos = vehicleTicketRepository.findAll();
        if (CollectionUtils.isEmpty(vehicleTicketDaos))
            return result;

        for (VehicleTicketDao vehicleTicketDao : vehicleTicketDaos) {
            result.getElements().add(VehicleTicketDto.builder()
                    .uuid(vehicleTicketDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public VehicleTicketDto createVehicleTicket(String vehicleUUID, VehicleTicketDto vehicleTicketDto) {

        VehicleTicketDao vehicleTicketDao = new VehicleTicketDao();
        vehicleTicketDao = vehicleTicketRepository.save(vehicleTicketDao);
        if (vehicleTicketDao == null)
            throw new ResponseException("Error in saving vehicleTicket vehicleTicket");
        return VehicleTicketDto.builder()
                .uuid(vehicleTicketDao.getUuid()).build();
    }

    @Override
    public VehicleTicketDto updateVehicleTicket(String vehicleUUID, String vehicleTicketUUID, VehicleTicketDto vehicleTicketDto) {
        VehicleTicketDao vehicleTicketDao = vehicleTicketRepository.findByUUID(vehicleTicketUUID);
        if (vehicleTicketDao == null)
            throw new ResponseException("No vehicleTicket  found for Id :" + vehicleTicketUUID);
        VehicleTicketDao newVehicleTicketDao = new VehicleTicketDao();
       

        newVehicleTicketDao.setId(vehicleTicketDao.getId());
        newVehicleTicketDao.setUuid(vehicleTicketDao.getUuid());
        vehicleTicketDao = vehicleTicketRepository.update(newVehicleTicketDao);
        if (vehicleTicketDao == null)
            throw new ResponseException("Error in updating details vehicleTicket ");
        return VehicleTicketDto.builder()
                .uuid(vehicleTicketDao.getUuid()).build();
    }

    @Override
    public VehicleTicketDto getVehicleTicketDetails(String vehicleUUID, String vehicleTicketUUID) {
        VehicleTicketDao vehicleTicketDao = vehicleTicketRepository.findByUUID(vehicleTicketUUID);
        if (vehicleTicketDao == null)
            throw new ResponseException("No vehicleTicket  found for Id :" + vehicleTicketUUID);
        return VehicleTicketDto.builder()
                .uuid(vehicleTicketDao.getUuid()).build();
    }

    @Override
    public Boolean deleteVehicleTicket(String vehicleUUID, String vehicleTicketUUID) {
        VehicleTicketDao vehicleTicketDao = vehicleTicketRepository.findByUUID(vehicleTicketUUID);
        if (vehicleTicketDao == null)
            throw new ResponseException("No vehicleTicket  found for Id :" + vehicleTicketUUID);
        vehicleTicketRepository.delete(vehicleTicketDao.getId());
        return true;
    }
}
