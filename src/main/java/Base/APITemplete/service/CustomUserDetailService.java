package Base.APITemplete.service;

import Base.APITemplete.model.User;
import Base.APITemplete.repository.UserRepository;
import Base.APITemplete.util.EncryptionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByLoginId(username);
        return new org.springframework.security.core.userdetails.User(user.getLoginId(), EncryptionUtil.decrypt(user.getPassword()), new ArrayList<>());
    }
}
