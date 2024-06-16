package com.goev.central.service.partner.ticket.impl;

import com.goev.central.dao.partner.ticket.PartnerTicketDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.partner.ticket.PartnerTicketDto;
import com.goev.central.repository.partner.ticket.PartnerTicketRepository;
import com.goev.central.service.partner.ticket.PartnerTicketService;
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
public class PartnerTicketServiceImpl implements PartnerTicketService {

    private final PartnerTicketRepository partnerTicketRepository;

    @Override
    public PaginatedResponseDto<PartnerTicketDto> getPartnerTickets(String partnerUUID) {
        PaginatedResponseDto<PartnerTicketDto> result = PaginatedResponseDto.<PartnerTicketDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<PartnerTicketDao> partnerTicketDaos = partnerTicketRepository.findAllActive();
        if (CollectionUtils.isEmpty(partnerTicketDaos))
            return result;

        for (PartnerTicketDao partnerTicketDao : partnerTicketDaos) {
            result.getElements().add(PartnerTicketDto.builder()
                    .uuid(partnerTicketDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public PartnerTicketDto createPartnerTicket(String partnerUUID, PartnerTicketDto partnerTicketDto) {

        PartnerTicketDao partnerTicketDao = new PartnerTicketDao();
        partnerTicketDao = partnerTicketRepository.save(partnerTicketDao);
        if (partnerTicketDao == null)
            throw new ResponseException("Error in saving partnerTicket partnerTicket");
        return PartnerTicketDto.builder()
                .uuid(partnerTicketDao.getUuid()).build();
    }

    @Override
    public PartnerTicketDto updatePartnerTicket(String partnerUUID, String partnerTicketUUID, PartnerTicketDto partnerTicketDto) {
        PartnerTicketDao partnerTicketDao = partnerTicketRepository.findByUUID(partnerTicketUUID);
        if (partnerTicketDao == null)
            throw new ResponseException("No partnerTicket  found for Id :" + partnerTicketUUID);
        PartnerTicketDao newPartnerTicketDao = new PartnerTicketDao();


        newPartnerTicketDao.setId(partnerTicketDao.getId());
        newPartnerTicketDao.setUuid(partnerTicketDao.getUuid());
        partnerTicketDao = partnerTicketRepository.update(newPartnerTicketDao);
        if (partnerTicketDao == null)
            throw new ResponseException("Error in updating details partnerTicket ");
        return PartnerTicketDto.builder()
                .uuid(partnerTicketDao.getUuid()).build();
    }

    @Override
    public PartnerTicketDto getPartnerTicketDetails(String partnerUUID, String partnerTicketUUID) {
        PartnerTicketDao partnerTicketDao = partnerTicketRepository.findByUUID(partnerTicketUUID);
        if (partnerTicketDao == null)
            throw new ResponseException("No partnerTicket  found for Id :" + partnerTicketUUID);
        return PartnerTicketDto.builder()
                .uuid(partnerTicketDao.getUuid()).build();
    }

    @Override
    public Boolean deletePartnerTicket(String partnerUUID, String partnerTicketUUID) {
        PartnerTicketDao partnerTicketDao = partnerTicketRepository.findByUUID(partnerTicketUUID);
        if (partnerTicketDao == null)
            throw new ResponseException("No partnerTicket  found for Id :" + partnerTicketUUID);
        partnerTicketRepository.delete(partnerTicketDao.getId());
        return true;
    }
}
