package kr.mybrary.notification.notification.presentation;

import kr.mybrary.notification.global.dto.response.SuccessResponse;
import kr.mybrary.notification.notification.domain.NotificationMessageService;
import kr.mybrary.notification.notification.domain.NotificationService;
import kr.mybrary.notification.notification.domain.dto.request.NotificationMessageGetByUserIdServiceRequest;
import kr.mybrary.notification.notification.domain.dto.request.NotificationSendToAllServiceRequest;
import kr.mybrary.notification.notification.presentation.dto.request.NotificationSendToAllRequest;
import kr.mybrary.notification.notification.presentation.dto.response.NotificationMessageGetByUserIdResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class NotificationController {

    private final NotificationService notificationService;
    private final NotificationMessageService notificationMessageService;

    @PostMapping("/for-all-users")
    public ResponseEntity<SuccessResponse<Void>> sendToAll(
            @RequestBody NotificationSendToAllRequest request) {

        notificationService.sendNotificationAllUser(NotificationSendToAllServiceRequest.of(request));
        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK.toString(), "모든 유저에게 푸시 알림을 보냅니다.", null));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<SuccessResponse<NotificationMessageGetByUserIdResponse>> findAllByUserId(
            @PathVariable String userId) {

        NotificationMessageGetByUserIdServiceRequest serviceRequest = NotificationMessageGetByUserIdServiceRequest.of(userId);

        return ResponseEntity.ok(SuccessResponse.of(HttpStatus.OK.toString(), "유저의 모든 푸시 알림을 조회했습니다.",
                notificationMessageService.findAllByUserId(serviceRequest)));
    }
}
