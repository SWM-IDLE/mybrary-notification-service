package kr.mybrary.notification.notification.presentation;

import kr.mybrary.notification.global.dto.response.SuccessResponse;
import kr.mybrary.notification.notification.domain.NotificationService;
import kr.mybrary.notification.notification.domain.dto.request.NotificationSendToAllServiceRequest;
import kr.mybrary.notification.notification.presentation.dto.request.NotificationSendToAllRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {


    private final NotificationService notificationService;

    @PostMapping("/for-all-users")
    public ResponseEntity<SuccessResponse<Void>> sendToAll(
            @RequestBody NotificationSendToAllRequest request) {

        notificationService.sendNotificationAllUser(NotificationSendToAllServiceRequest.of(request));
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK.toString(), "모든 유저에게 푸시 알림을 보냅니다.", null));
    }
}
