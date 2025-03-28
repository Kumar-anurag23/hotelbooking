package org.hotelbooking.service;


import jakarta.security.auth.message.AuthException;
import org.hotelbooking.dto.UserDto;
import org.hotelbooking.models.User;

public interface UserService {
    public User registerUser(UserDto signupRequest);
    public User loginUser(UserDto userDto) throws AuthException;
}
