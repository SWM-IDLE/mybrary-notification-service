package kr.mybrary.notification.notification.domain.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FollowRequestMessage {

    private String sourceUserId;

    private String targetUserId;
    private String targetUserNickname;
    private String targetUserProfileImageUrl;
}
