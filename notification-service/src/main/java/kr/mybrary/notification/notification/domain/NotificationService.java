package kr.mybrary.notification.notification.domain;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import io.awspring.cloud.sqs.annotation.SqsListener;
import java.util.List;
import java.util.Map;
import kr.mybrary.notification.notification.domain.dto.request.NotificationSendToAllServiceRequest;
import kr.mybrary.notification.user.domain.UserService;
import kr.mybrary.notification.user.persistence.User;
import kr.mybrary.notification.notification.domain.dto.request.NotificationSendServiceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserService userService;

    @Transactional(readOnly = true)
    @SqsListener(value = "${cloud.aws.sqs.queue.name}")
    public void sendNotificationByToken(@Payload NotificationSendServiceRequest request, @Headers Map<String, String> headers) {

        String userToken = request.getTargetUserToken();
        User user = userService.findByUserToken(userToken);

        if (user.getUserDeviceToken() != null) {
            Notification notification = createNotification(request);
            Message message = createMessage(user, notification);

            try {
                firebaseMessaging.send(message);
                log.info("알림을 성공적으로 전송했습니다. targetUserId = {}", userToken);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
                log.error("알림 보내기를 실패하였습니다. targetUserId = {}", userToken);
            }

        } else {
            log.error("서버에 유저의 FirebaseToken이 존재하지 않습니다. targetUserId = {}", userToken);
        }
    }

    public void sendNotificationAllUser(NotificationSendToAllServiceRequest request) {

        List<String> allUserDeviceToken = userService.findAllUserDeviceToken();

        Notification notification = Notification.builder()
                .setTitle(request.getTitle())
                .setBody(request.getBody())
                .build();

        MulticastMessage message = MulticastMessage.builder()
                .setNotification(notification)
                .addAllTokens(allUserDeviceToken)
                .build();

        try {
            firebaseMessaging.sendEachForMulticast(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            log.error("알림 보내기를 실패하였습니다.");
        }
    }

    private Message createMessage(User user, Notification notification) {
        return Message.builder()
                .setToken(user.getUserDeviceToken())
                .setNotification(notification)
                .build();
    }

    private Notification createNotification(NotificationSendServiceRequest dto) {
        return Notification.builder()
                .setTitle(dto.getTitle())
                .setBody(dto.getBody())
                .build();
    }
}
