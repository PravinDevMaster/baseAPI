package Base.APITemplete.service.impl;

import Base.APITemplete.model.User;
import Base.APITemplete.repository.UserRepository;
import Base.APITemplete.service.UserService;
import Base.APITemplete.util.DateUtil;
import Base.APITemplete.util.EncryptionUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User postUser(User user) {
        // Validate if the loginId already exist
        if(userRepository.findByLoginId(user.getLoginId()) != null) {
            return null;
        } else {
            // Add the user if the loginId is unique
            user.setCreatedDate(DateUtil.getCurrentSqlDate());
            user.setPassword(EncryptionUtil.encrypt(user.getPassword()));
            User saveUser = userRepository.save(user);
            saveUser.setPassword(EncryptionUtil.decrypt(saveUser.getPassword()));
            return saveUser;
        }

    }

    @Override
    public User putUser(User user, Long id) {
        User existingUser = userRepository.getById(id);
        existingUser.setLoginId(user.getLoginId());
        existingUser.setPassword(existingUser.getPassword());
        existingUser.setModifiedDate(DateUtil.getCurrentSqlDate());
        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User validateUser(String loginId, String password) {
        User user = userRepository.findByLoginId(loginId);
        if(user != null && EncryptionUtil.decrypt(user.getPassword()).equals(EncryptionUtil.decrypt(password))){
            return user; //Valid credential
        } else {
            return null; //Invalid credential
        }
    }

    @Override
    public User changePassword(String loginId, String password) {
        User existingUser = userRepository.findByLoginId(loginId);
        if (existingUser != null) {
            existingUser.setPassword(EncryptionUtil.encrypt(password));
            existingUser.setModifiedDate(DateUtil.getCurrentSqlDate());
            return userRepository.save(existingUser);
        } else  {
            return null;
        }
    }


}
