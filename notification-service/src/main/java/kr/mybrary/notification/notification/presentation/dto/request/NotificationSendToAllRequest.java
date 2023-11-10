package kr.mybrary.notification.notification.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationSendToAllRequest {

    private String title;
    private String body;

}
