package kr.mybrary.notification.user.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDeviceTokenRegisterServiceRequest {

    private String userToken;
    private String userDeviceToken;

    public static UserDeviceTokenRegisterServiceRequest of(String userToken, String userDeviceToken) {
        return UserDeviceTokenRegisterServiceRequest.builder()
                .userToken(userToken)
                .userDeviceToken(userDeviceToken)
                .build();
    }

}
