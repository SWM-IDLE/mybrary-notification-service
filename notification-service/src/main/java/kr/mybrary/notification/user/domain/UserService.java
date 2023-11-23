package kr.mybrary.notification.user.domain;

import java.util.List;
import kr.mybrary.notification.user.domain.dto.request.UserDeviceTokenRegisterServiceRequest;
import kr.mybrary.notification.user.persistence.User;
import kr.mybrary.notification.user.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public User findByUserToken(String userToken) {
        return userRepository.findByUserToken(userToken).orElseGet(
                () -> {
                    log.error("해당 유저를 찾을 수 없습니다. userToken = {}", userToken);
                    return userRepository.save(User.builder().userToken(userToken).userDeviceToken(null).build());
                }
        );
    }

    @Transactional(readOnly = true)
    public List<String> findAllUserDeviceToken() {
        return userRepository.findAll()
                .stream()
                .filter(user -> user.getUserDeviceToken() != null && !user.getUserDeviceToken().isEmpty())
                .map(User::getUserDeviceToken)
                .toList();
    }

    public void registerUserDeviceToken(UserDeviceTokenRegisterServiceRequest request) {
        userRepository.findByUserToken(request.getUserToken())
                .ifPresentOrElse(
                        user -> user.updateUserDeviceToken(request.getUserDeviceToken()),
                        () -> userRepository.save(
                                User.builder()
                                    .userToken(request.getUserToken())
                                    .userDeviceToken(request.getUserDeviceToken())
                                    .build()
                        )
                );
    }
}
