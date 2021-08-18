package com.eindwerk.SnelGeboekt.user;

import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;
import java.util.Set;

public interface UserService {

    class PasswordException extends Exception {
        public PasswordException(String message) {
            super(message);
        }
    }

    class PasswordMisMatchException extends Exception {
        public PasswordMisMatchException(String message) {
            super(message);
        }
    }

    @PreAuthorize("isAuthenticated()")
    User getUserByEmail(String email);


    User findUser(String needle);

    List<User> getAll();

    void save(User user) throws PasswordException, PasswordMisMatchException;
}
