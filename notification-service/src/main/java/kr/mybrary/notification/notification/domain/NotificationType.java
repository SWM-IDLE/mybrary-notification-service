package kr.mybrary.notification.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NotificationType {

    FOLLOW("FOLLOW"),
    ALL("ALL");

    private final String type;
}
