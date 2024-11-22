package com.goev.central.controller.earning;

import com.goev.central.dto.earning.PartnerEarningDto;
import com.goev.central.dto.earning.PartnerFixedEarningTransactionDto;
import com.goev.central.service.earning.PartnerEarningService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/earning-management")
@AllArgsConstructor
public class PartnerFixedEarningController {

    private final PartnerEarningService partnerEarningService;

    @GetMapping("/earning/partner/{partner-uuid}/transactions")
    public ResponseDto<List<PartnerFixedEarningTransactionDto>> getEarningRules(@PathVariable(value = "partner-uuid") String partnerUuid) {
        List<PartnerFixedEarningTransactionDto> transactions = partnerEarningService.getPartnerEarningTransactions(partnerUuid);
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, transactions);
    }
}
