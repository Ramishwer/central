package com.goev.central.service.incentive;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.incentive.IncentiveModelDto;

public interface IncentiveModelService {
    PaginatedResponseDto<IncentiveModelDto> getIncentiveModels();

    IncentiveModelDto createIncentiveModel(IncentiveModelDto incentiveModelDto);

    IncentiveModelDto updateIncentiveModel(String incentiveModelUUID, IncentiveModelDto incentiveModelDto);

    IncentiveModelDto getIncentiveModelDetails(String incentiveModelUUID);

    Boolean deleteIncentiveModel(String incentiveModelUUID);
}
