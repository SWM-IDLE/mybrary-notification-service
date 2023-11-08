package kr.mybrary.notification.notification.domain;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import kr.mybrary.notification.notification.domain.exception.UserNotFoundException;
import kr.mybrary.notification.notification.persistence.User;
import kr.mybrary.notification.notification.persistence.repository.UserRepository;
import kr.mybrary.notification.notification.domain.dto.NotificationSendServiceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NotificationService {

    private final FirebaseMessaging firebaseMessaging;
    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public void sendNotificationByToken(NotificationSendServiceRequest request) {

        String userToken = request.getTargetUserToken();
        User user = userRepository.findByUserToken(userToken)
                .orElseThrow(UserNotFoundException::new);

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
