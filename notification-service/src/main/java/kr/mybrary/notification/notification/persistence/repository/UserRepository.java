package kr.mybrary.notification.notification.persistence.repository;

import java.util.Optional;
import kr.mybrary.notification.notification.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserToken(String userToken);
}
