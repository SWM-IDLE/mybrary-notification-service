package kr.mybrary.notification.notification.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationSendServiceRequest {

    private String targetUserToken;
    private String title;
    private String body;

}
