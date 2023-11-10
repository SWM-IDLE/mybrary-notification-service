package kr.mybrary.notification.notification.domain.dto.request;

import kr.mybrary.notification.notification.presentation.dto.request.NotificationSendToAllRequest;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationSendToAllServiceRequest {

    private String title;
    private String body;

    public static NotificationSendToAllServiceRequest of(NotificationSendToAllRequest request) {
        return NotificationSendToAllServiceRequest.builder()
                .title(request.getTitle())
                .body(request.getBody())
                .build();
    }
}
