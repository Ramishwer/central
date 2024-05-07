package com.goev.central.service.customer.segment;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.customer.segment.CustomerSegmentDto;

public interface CustomerSegmentService {
    PaginatedResponseDto<CustomerSegmentDto> getCustomerSegments();

    CustomerSegmentDto createCustomerSegment(CustomerSegmentDto customerSegmentDto);

    CustomerSegmentDto updateCustomerSegment(String customerSegmentUUID, CustomerSegmentDto customerSegmentDto);

    CustomerSegmentDto getCustomerSegmentDetails(String customerSegmentUUID);

    Boolean deleteCustomerSegment(String customerSegmentUUID);
}
