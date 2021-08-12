package com.eindwerk.SnelGeboekt.user;

import java.util.List;

public interface UserService {

    class PasswordMisMatchException extends Exception {
        public PasswordMisMatchException(String message) {
            super(message);
        }
    }

    User findUser(String needle);

    List<User> getAll();

    void save(User user) throws PasswordMisMatchException;

}
