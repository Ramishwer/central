package com.goev.central.controller.business;

import com.goev.central.dto.common.PaginatedResponseDto;

import com.goev.central.dto.business.BusinessClientDto;
import com.goev.central.service.business.BusinessClientService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/business-management")
@AllArgsConstructor
public class BusinessClientController {

    private final BusinessClientService businessClientService;

    @GetMapping("/clients")
    public ResponseDto<PaginatedResponseDto<BusinessClientDto>> getClients(@RequestParam(value = "count",required = false) Integer count,
                                                                           @RequestParam(value = "start", required = false) Integer start,
                                                                           @RequestParam(value = "from", required = false) Long from,
                                                                           @RequestParam(value = "to", required = false) Long to,
                                                                           @RequestParam(value = "lastUUID", required = false) String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, businessClientService.getClients());
    }
    @PostMapping("/clients")
    public ResponseDto<BusinessClientDto> createClient(@RequestBody BusinessClientDto businessClientDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, businessClientService.createClient(businessClientDto));
    }

    @PutMapping("/clients/{client-uuid}")
    public ResponseDto<BusinessClientDto> updateClient(@PathVariable(value = "client-uuid")String clientUUID, @RequestBody BusinessClientDto businessClientDto){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, businessClientService.updateClient(clientUUID,businessClientDto));
    }

    @GetMapping("/clients/{client-uuid}")
    public ResponseDto<BusinessClientDto> getClientDetails(@PathVariable(value = "client-uuid")String clientUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, businessClientService.getClientDetails(clientUUID));
    }

    @DeleteMapping("/clients/{client-uuid}")
    public ResponseDto<Boolean> deleteClient(@PathVariable(value = "client-uuid")String clientUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, businessClientService.deleteClient(clientUUID));
    }
}
