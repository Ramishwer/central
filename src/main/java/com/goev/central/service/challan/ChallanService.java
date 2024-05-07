package com.goev.central.service.challan;

import com.goev.central.dto.challan.ChallanDto;
import com.goev.central.dto.common.PaginatedResponseDto;

public interface ChallanService {
    PaginatedResponseDto<ChallanDto> getChallans();

    ChallanDto createChallan(ChallanDto challanDto);

    ChallanDto updateChallan(String challanUUID, ChallanDto challanDto);

    ChallanDto getChallanDetails(String challanUUID);

    Boolean deleteChallan(String challanUUID);
}
