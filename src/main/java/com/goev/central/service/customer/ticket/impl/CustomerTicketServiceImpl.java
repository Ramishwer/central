package com.goev.central.service.customer.ticket.impl;

import com.goev.central.dao.customer.ticket.CustomerTicketDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.ticket.CustomerTicketDto;
import com.goev.central.repository.customer.ticket.CustomerTicketRepository;
import com.goev.central.service.customer.ticket.CustomerTicketService;
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
public class CustomerTicketServiceImpl implements CustomerTicketService {

    private final CustomerTicketRepository customerTicketRepository;

    @Override
    public PaginatedResponseDto<CustomerTicketDto> getCustomerTickets(String customerUUID) {
        PaginatedResponseDto<CustomerTicketDto> result = PaginatedResponseDto.<CustomerTicketDto>builder().elements(new ArrayList<>()).build();
        List<CustomerTicketDao> customerTicketDaos = customerTicketRepository.findAllActive();
        if (CollectionUtils.isEmpty(customerTicketDaos))
            return result;

        for (CustomerTicketDao customerTicketDao : customerTicketDaos) {
            result.getElements().add(CustomerTicketDto.builder()
                    .uuid(customerTicketDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public CustomerTicketDto createCustomerTicket(String customerUUID, CustomerTicketDto customerTicketDto) {

        CustomerTicketDao customerTicketDao = new CustomerTicketDao();
        customerTicketDao = customerTicketRepository.save(customerTicketDao);
        if (customerTicketDao == null)
            throw new ResponseException("Error in saving customerTicket customerTicket");
        return CustomerTicketDto.builder()
                .uuid(customerTicketDao.getUuid()).build();
    }

    @Override
    public CustomerTicketDto updateCustomerTicket(String customerUUID, String customerTicketUUID, CustomerTicketDto customerTicketDto) {
        CustomerTicketDao customerTicketDao = customerTicketRepository.findByUUID(customerTicketUUID);
        if (customerTicketDao == null)
            throw new ResponseException("No customerTicket  found for Id :" + customerTicketUUID);
        CustomerTicketDao newCustomerTicketDao = new CustomerTicketDao();


        newCustomerTicketDao.setId(customerTicketDao.getId());
        newCustomerTicketDao.setUuid(customerTicketDao.getUuid());
        customerTicketDao = customerTicketRepository.update(newCustomerTicketDao);
        if (customerTicketDao == null)
            throw new ResponseException("Error in updating details customerTicket ");
        return CustomerTicketDto.builder()
                .uuid(customerTicketDao.getUuid()).build();
    }

    @Override
    public CustomerTicketDto getCustomerTicketDetails(String customerUUID, String customerTicketUUID) {
        CustomerTicketDao customerTicketDao = customerTicketRepository.findByUUID(customerTicketUUID);
        if (customerTicketDao == null)
            throw new ResponseException("No customerTicket  found for Id :" + customerTicketUUID);
        return CustomerTicketDto.builder()
                .uuid(customerTicketDao.getUuid()).build();
    }

    @Override
    public Boolean deleteCustomerTicket(String customerUUID, String customerTicketUUID) {
        CustomerTicketDao customerTicketDao = customerTicketRepository.findByUUID(customerTicketUUID);
        if (customerTicketDao == null)
            throw new ResponseException("No customerTicket  found for Id :" + customerTicketUUID);
        customerTicketRepository.delete(customerTicketDao.getId());
        return true;
    }
}
