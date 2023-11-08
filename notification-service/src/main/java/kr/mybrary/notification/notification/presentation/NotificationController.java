package kr.mybrary.notification.notification.presentation;

import kr.mybrary.notification.global.dto.response.SuccessResponse;
import kr.mybrary.notification.notification.domain.NotificationService;
import kr.mybrary.notification.notification.domain.dto.NotificationSendServiceRequest;
import lombok.RequiredArgsConstructor;
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

    @PostMapping()
    public ResponseEntity<SuccessResponse<Void>> sendNotification(@RequestBody NotificationSendServiceRequest dto) {

        notificationService.sendNotificationByToken(dto);
        return ResponseEntity.ok(SuccessResponse.of("SUCCESS", "성공", null));
    }

}
