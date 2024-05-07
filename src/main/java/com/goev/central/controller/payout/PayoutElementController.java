package com.goev.central.controller.payout;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.payout.PayoutElementDto;
import com.goev.central.service.payout.PayoutElementService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/payout-management")
@AllArgsConstructor
public class PayoutElementController {

    private final PayoutElementService payoutElementService;

    @GetMapping("/payout-elements")
    public ResponseDto<PaginatedResponseDto<PayoutElementDto>> getPayoutElements(@RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, payoutElementService.getPayoutElements());
    }

    

    @GetMapping("/payout-elements/{element-uuid}")
    public ResponseDto<PayoutElementDto> getPayoutElementDetails(@PathVariable(value = "element-uuid")String elementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, payoutElementService.getPayoutElementDetails(elementUUID));
    }


    @PostMapping("/payout-elements")
    public ResponseDto<PayoutElementDto> createPayoutElement(@RequestBody PayoutElementDto payoutElementDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, payoutElementService.createPayoutElement(payoutElementDto));
    }

    @PutMapping("/payout-elements/{element-uuid}")
    public ResponseDto<PayoutElementDto> updatePayoutElement(@PathVariable(value = "element-uuid")String elementUUID, @RequestBody PayoutElementDto payoutElementDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, payoutElementService.updatePayoutElement(elementUUID,payoutElementDto));
    }
    @DeleteMapping("/payout-elements/{element-uuid}")
    public ResponseDto<Boolean> deletePayoutElement(@PathVariable(value = "element-uuid")String elementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, payoutElementService.deletePayoutElement(elementUUID));
    }
}
