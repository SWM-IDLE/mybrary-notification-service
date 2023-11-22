package kr.mybrary.notification.notification.persistence.repository;

import java.util.List;
import kr.mybrary.notification.notification.persistence.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long> {

    List<NotificationMessage> findByUserId(String userId);
}
