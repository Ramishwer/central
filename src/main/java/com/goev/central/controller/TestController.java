package com.goev.central.controller;

import com.goev.central.config.SpringContext;
import com.goev.central.dto.user.UserViewDto;
import com.goev.central.scheduler.PartnerPayoutTransactionScheduler;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
public class TestController {


    @GetMapping("/api/v1/calculate/payout")
    public ResponseDto<Boolean> calculatePayoutForTime(@RequestParam Long executionTime) {
        PartnerPayoutTransactionScheduler scheduler  =SpringContext.getBean(PartnerPayoutTransactionScheduler.class);
        scheduler.calculatePayout(new DateTime(executionTime));
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200,true );
    }

}
