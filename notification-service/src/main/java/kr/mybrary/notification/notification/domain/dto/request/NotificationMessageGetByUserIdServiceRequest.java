package kr.mybrary.notification.notification.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationMessageGetByUserIdServiceRequest {

    private String userId;

    public static NotificationMessageGetByUserIdServiceRequest of(String userId) {
        return NotificationMessageGetByUserIdServiceRequest.builder()
                .userId(userId)
                .build();
    }
}
