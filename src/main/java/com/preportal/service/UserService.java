package com.preportal.service;

import com.preportal.model.CustomUserDetails;
import com.preportal.model.User;
import com.preportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;

    public User registerUser(User user) {
        User result = userRepository.findByEmail(user.getEmail());
        if (result == null) {
            user.setPassword(encoder.encode(user.getPassword()));
            user.setName(user.getName().toUpperCase());
            user.setCourse(user.getCourse().toUpperCase());
            user.setInstitute(user.getInstitute().toUpperCase());

            return userRepository.save(user);
        }
        return null;
    }

    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    public CustomUserDetails getUserDetails(User user) {
        CustomUserDetails ud = new CustomUserDetails();
        ud.setId(user.getId());
        ud.setName(user.getName());
        ud.setEmail(user.getEmail());
        ud.setInstitute(user.getInstitute());
        ud.setCourse(user.getCourse());
        return ud;
    }

}
