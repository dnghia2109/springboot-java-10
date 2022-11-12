package vn.techmaster.bookonline.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import vn.techmaster.bookonline.entity.User;
import vn.techmaster.bookonline.repository.UserRepository;

@Service
public class UserDetailServiceCustom implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            return new UserDetailsCustom(user);
        }
        throw new UsernameNotFoundException("User with email " + email + " not found");
    }
}
