package com.goev.central.controller.user.detail;

import com.goev.central.dto.common.PaginatedResponseDto;
import com.goev.central.dto.user.detail.UserSessionDto;
import com.goev.central.service.user.detail.UserSessionService;
import com.goev.lib.dto.ResponseDto;
import com.goev.lib.dto.StatusDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/user-management")
@AllArgsConstructor
public class UserSessionController {

    private final UserSessionService userSessionService;

    @GetMapping("/users/{user-uuid}/sessions")
    public ResponseDto<PaginatedResponseDto<UserSessionDto>> getUserSessions(@PathVariable(value = "user-uuid")String userUUID,
            @RequestParam("count")Integer count,
                                                                                 @RequestParam("start")Integer start,
                                                                                 @RequestParam("from")Long from,
                                                                                 @RequestParam("to")Long to,
                                                                                 @RequestParam("lastUUID") String lastElementUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, userSessionService.getUserSessions(userUUID));
    }

    

    @GetMapping("/users/{user-uuid}/sessions/{session-uuid}")
    public ResponseDto<UserSessionDto> getUserSessionDetails(@PathVariable(value = "user-uuid")String userUUID,
                                                                   @PathVariable(value = "session-uuid")String sessionUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, userSessionService.getUserSessionDetails(userUUID,sessionUUID));
    }
    
    @DeleteMapping("/users/{user-uuid}/sessions/{session-uuid}")
    public ResponseDto<Boolean> deleteUserSession(@PathVariable(value = "user-uuid")String userUUID,@PathVariable(value = "session-uuid")String sessionUUID){
        return new ResponseDto<>(StatusDto.builder().message("SUCCESS").build(),200, userSessionService.deleteUserSession(userUUID,sessionUUID));
    }
}
