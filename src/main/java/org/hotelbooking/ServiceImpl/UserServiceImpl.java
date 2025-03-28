package org.hotelbooking.ServiceImpl;

import jakarta.security.auth.message.AuthException;
import org.hotelbooking.dto.UserDto;
import org.hotelbooking.models.User;
import org.hotelbooking.repository.UserRepository;
import org.hotelbooking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

        @Override
        public User registerUser(UserDto signupRequest) {
            if(userRepository.existsByUsername(signupRequest.getUsername())) {
                throw new RuntimeException("Username is already in use");
            }
            if(userRepository.existsByEmail(signupRequest.getEmail())) {
                throw new RuntimeException("Email is already in use");
            }

            System.out.println("Raw password received: " + signupRequest.getPassword());

            if (signupRequest.getPassword() == null) {
                throw new IllegalArgumentException("Password cannot be null");
            }


            User user = new User();
            user.setUsername(signupRequest.getUsername());
            user.setEmail(signupRequest.getEmail());
            user.setPassword(signupRequest.getPassword());

            return userRepository.save(user);
        }
    @Override
    public User loginUser(UserDto userDto) throws AuthException {
        if (userDto.getEmail() == null || userDto.getEmail().isBlank()) {
            throw new AuthException("Email cannot be empty");
        }

        if (userDto.getPassword()== null || userDto.getPassword().isBlank()) {

            throw new AuthException("Password cannot be empty");
        }

        User user = userRepository.findByEmail(userDto.getEmail())
                .orElseThrow(() -> new AuthException("User not found"));

        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new AuthException("Invalid credentials");
        }

        return user;
    }


}