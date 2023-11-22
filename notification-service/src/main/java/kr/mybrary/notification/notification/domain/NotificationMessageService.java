package kr.mybrary.notification.notification.domain;

import java.util.List;
import kr.mybrary.notification.notification.domain.dto.request.NotificationMessageGetByUserIdServiceRequest;
import kr.mybrary.notification.notification.persistence.NotificationMessage;
import kr.mybrary.notification.notification.persistence.repository.NotificationMessageRepository;
import kr.mybrary.notification.notification.presentation.dto.response.NotificationMessageGetByUserIdResponse;
import kr.mybrary.notification.notification.presentation.dto.response.NotificationMessageGetByUserIdResponse.NotificationMessageGetElement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class NotificationMessageService {

    private final NotificationMessageRepository notificationMessageRepository;

    public void save(NotificationMessage notificationMessage) {
        notificationMessageRepository.save(notificationMessage);
    }

    @Transactional(readOnly = true)
    public NotificationMessageGetByUserIdResponse findAllByUserId(NotificationMessageGetByUserIdServiceRequest request) {
        List<NotificationMessage> notificationMessages = notificationMessageRepository.findByUserId(
                request.getUserId());

        return NotificationMessageGetByUserIdResponse.builder()
                .messages(notificationMessages.stream()
                        .map(NotificationMessageGetElement::of)
                        .toList())
                .build();
    }
}
