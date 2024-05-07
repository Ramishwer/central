package com.goev.central.service.customer.detail.impl;

import com.goev.central.dao.customer.detail.CustomerDeviceDao;
import com.goev.central.dto.common.PageDto;
import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.detail.CustomerDeviceDto;
import com.goev.central.repository.customer.detail.CustomerDeviceRepository;
import com.goev.central.service.customer.detail.CustomerDeviceService;
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
public class CustomerDeviceServiceImpl implements CustomerDeviceService {

    private final CustomerDeviceRepository customerDeviceRepository;

    @Override
    public PaginatedResponseDto<CustomerDeviceDto> getCustomerDevices(String customerUUID) {
        PaginatedResponseDto<CustomerDeviceDto> result = PaginatedResponseDto.<CustomerDeviceDto>builder().pagination(PageDto.builder().currentPage(0).totalPages(0).build()).elements(new ArrayList<>()).build();
        List<CustomerDeviceDao> customerDeviceDaos = customerDeviceRepository.findAll();
        if (CollectionUtils.isEmpty(customerDeviceDaos))
            return result;

        for (CustomerDeviceDao customerDeviceDao : customerDeviceDaos) {
            result.getElements().add(CustomerDeviceDto.builder()
                    .uuid(customerDeviceDao.getUuid())
                    .build());
        }
        return result;
    }

    @Override
    public CustomerDeviceDto getCustomerDeviceDetails(String customerUUID, String customerDeviceUUID) {
        CustomerDeviceDao customerDeviceDao = customerDeviceRepository.findByUUID(customerDeviceUUID);
        if (customerDeviceDao == null)
            throw new ResponseException("No customerDevice  found for Id :" + customerDeviceUUID);
        return CustomerDeviceDto.builder()
                .uuid(customerDeviceDao.getUuid()).build();
    }

    @Override
    public Boolean deleteCustomerDevice(String customerUUID, String customerDeviceUUID) {
        CustomerDeviceDao customerDeviceDao = customerDeviceRepository.findByUUID(customerDeviceUUID);
        if (customerDeviceDao == null)
            throw new ResponseException("No customerDevice  found for Id :" + customerDeviceUUID);
        customerDeviceRepository.delete(customerDeviceDao.getId());
        return true;
    }
}
