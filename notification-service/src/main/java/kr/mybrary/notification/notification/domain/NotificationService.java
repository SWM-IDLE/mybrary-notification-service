package kr.mybrary.notification.notification.domain;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.MulticastMessage;
import com.google.firebase.messaging.Notification;
import io.awspring.cloud.sqs.annotation.SqsListener;
import java.util.List;
import java.util.Map;
import kr.mybrary.notification.notification.domain.dto.message.FollowRequestMessage;
import kr.mybrary.notification.notification.domain.dto.request.NotificationSendToAllServiceRequest;
import kr.mybrary.notification.notification.persistence.NotificationMessage;
import kr.mybrary.notification.notification.persistence.repository.NotificationMessageRepository;
import kr.mybrary.notification.user.domain.UserService;
import kr.mybrary.notification.user.persistence.User;
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
    private final NotificationMessageRepository notificationMessageRepository;

    @Transactional(readOnly = true)
    @SqsListener(value = "${cloud.aws.sqs.queue.follow}")
    public void notifyFollowRequest(@Payload FollowRequestMessage request, @Headers Map<String, String> headers) {

        String userToken = request.getSourceUserId();
        User user = userService.findByUserToken(userToken);

        if (user.getUserDeviceToken() != null) {

            Notification notification = Notification.builder()
                    .setTitle("팔로우 요청이 왔습니다.")
                    .setBody(String.format("%s님이 팔로우를 요청했습니다.", request.getTargetUserNickname()))
                    .build();
            Message message = createMessage(user, notification);

            try {
                firebaseMessaging.send(message);
                notificationMessageRepository.save(createFollowNotificationMessage(request));
                log.info("팔로우 알림을 성공적으로 전송했습니다. targetUserId = {}", userToken);
            } catch (FirebaseMessagingException e) {
                e.printStackTrace();
                log.error("팔로우 알림 보내기를 실패하였습니다. targetUserId = {}", userToken);
            }

        } else {
            log.error("서버에 유저의 FirebaseToken이 존재하지 않습니다. targetUserId = {}", userToken);
        }
    }

    private static NotificationMessage createFollowNotificationMessage(FollowRequestMessage request) {
        return NotificationMessage.builder()
                .userId(request.getSourceUserId())
                .sourceUserId(request.getSourceUserId())
                .message(String.format("%s님이 팔로우를 요청했습니다.", request.getTargetUserNickname()))
                .type("FOLLOW")
                .build();
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

}
