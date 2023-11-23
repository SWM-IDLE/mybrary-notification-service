package kr.mybrary.notification.user.presentation;

import kr.mybrary.notification.global.dto.response.SuccessResponse;
import kr.mybrary.notification.user.domain.UserService;
import kr.mybrary.notification.user.domain.dto.request.UserDeviceTokenRegisterServiceRequest;
import kr.mybrary.notification.user.presentation.dto.request.UserDeviceTokenRegisterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/device-token")
    public ResponseEntity<SuccessResponse<Void>> registerDeviceToken(
            @RequestHeader("USER-ID") String loginId,
            @RequestBody UserDeviceTokenRegisterRequest request) {

        UserDeviceTokenRegisterServiceRequest serviceRequest = UserDeviceTokenRegisterServiceRequest.of(loginId,
                request.getUserDeviceToken());

        userService.registerUserDeviceToken(serviceRequest);
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.CREATED.toString(), "유저의 디바이스 토큰을 등록했습니다.", null));
    }

}
