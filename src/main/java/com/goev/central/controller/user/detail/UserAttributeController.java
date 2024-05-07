package com.goev.central.controller.user.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.detail.UserAttributeDto;
import com.goev.central.service.user.detail.UserAttributeService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user-management")
@AllArgsConstructor
public class UserAttributeController {

    private final UserAttributeService userAttributeService;

    @GetMapping("/users/{user-uuid}/attributes")
    public ResponseDto<PaginatedResponseDto<UserAttributeDto>> getUserAttributes(
            @PathVariable(value = "user-uuid") String userUUID,
            @RequestParam("count") Integer count,
            @RequestParam("start") Integer start,
            @RequestParam("from") Long from,
            @RequestParam("to") Long to,
            @RequestParam("lastUUID") String lastElementUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userAttributeService.getUserAttributes(userUUID));
    }


    @GetMapping("/users/{user-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<UserAttributeDto> getUserAttributeDetails(@PathVariable(value = "user-uuid") String userUUID, @PathVariable(value = "attribute-uuid") String attributeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userAttributeService.getUserAttributeDetails(userUUID, attributeUUID));
    }


    @PostMapping("/users/{user-uuid}/attributes")
    public ResponseDto<UserAttributeDto> createUserAttribute(@PathVariable(value = "user-uuid") String userUUID, @RequestBody UserAttributeDto userAttributeDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userAttributeService.createUserAttribute(userUUID, userAttributeDto));
    }

    @PutMapping("/users/{user-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<UserAttributeDto> updateUserAttribute(@PathVariable(value = "user-uuid") String userUUID, @PathVariable(value = "attribute-uuid") String attributeUUID, @RequestBody UserAttributeDto userAttributeDto) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userAttributeService.updateUserAttribute(userUUID, attributeUUID, userAttributeDto));
    }

    @DeleteMapping("/users/{user-uuid}/attributes/{attribute-uuid}")
    public ResponseDto<Boolean> deleteUserAttribute(@PathVariable(value = "user-uuid") String userUUID, @PathVariable(value = "attribute-uuid") String attributeUUID) {
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(), 200, userAttributeService.deleteUserAttribute(userUUID, attributeUUID));
    }
}
