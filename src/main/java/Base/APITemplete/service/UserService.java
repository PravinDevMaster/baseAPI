package Base.APITemplete.service;

import Base.APITemplete.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();
    Optional<User> getById(Long id);
    User postUser(User user);
    User putUser(User user, Long id);
    void deleteUser(Long id);
    User validateUser(String loginId, String password);
    User changePassword(String loginId, String password);
}
