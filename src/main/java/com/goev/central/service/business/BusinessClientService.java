package com.goev.central.service.business;

import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface BusinessClientService {
    PaginatedResponseDto<BusinessClientDto> getClients();

    BusinessClientDto createClient(BusinessClientDto businessClientDto);

    BusinessClientDto updateClient(String clientUUID, BusinessClientDto businessClientDto);

    BusinessClientDto getClientDetails(String clientUUID);

    Boolean deleteClient(String clientUUID);
}
