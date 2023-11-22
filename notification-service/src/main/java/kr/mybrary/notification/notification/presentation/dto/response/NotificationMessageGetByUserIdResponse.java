package kr.mybrary.notification.notification.presentation.dto.response;

import java.time.format.DateTimeFormatter;
import java.util.List;
import kr.mybrary.notification.notification.persistence.NotificationMessage;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NotificationMessageGetByUserIdResponse {

    List<NotificationMessageGetElement> messages;

    @Getter
    @Builder
    public static class NotificationMessageGetElement {

        private String userId;
        private String message;
        private String sourceUserId;
        private String type;
        private String publishedAt;

        public static NotificationMessageGetElement of(NotificationMessage notificationMessage) {
            return NotificationMessageGetElement.builder()
                    .userId(notificationMessage.getUserId())
                    .message(notificationMessage.getMessage())
                    .sourceUserId(notificationMessage.getSourceUserId())
                    .type(notificationMessage.getType())
                    .publishedAt(notificationMessage.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    .build();
        }
    }
}
