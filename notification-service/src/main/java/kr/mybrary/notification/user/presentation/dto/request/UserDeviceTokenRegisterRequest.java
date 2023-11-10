package kr.mybrary.notification.user.presentation.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDeviceTokenRegisterRequest {

    private String userDeviceToken;

}
