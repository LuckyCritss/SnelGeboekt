package com.eindwerk.SnelGeboekt.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements  UserService{

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    @Override
    public User findUser(String needle) {
        if (userRepository.getUserByEmail(needle) != null) {
            return userRepository.getUserByEmail(needle);
        } else {
            return null;
        }
    }

    @Override
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Override
    public void save(User user) throws UserService.PasswordException, UserService.PasswordMisMatchException {
        if (user.getWachtWoord() != null && user.getWachtWoord().length() < 5) {
            throw new UserService.PasswordException("password malformed");
        }
        if (user.getWachtWoord() == null && user.getId() == 0) {
            throw new UserService.PasswordException("password malformed");
        }
        if (user.getWachtWoord().length() > 4 && !user.getWachtWoord().equals(user.getCheckWachtWoord()) ) {
            throw new UserService.PasswordMisMatchException("password mismatch");
        }

        if (user.getWachtWoord() == null && user.getId() > 0) {
            user.setWachtWoord(userRepository.findById(user.getId()).get().getWachtWoord());
        } else {
            user.setWachtWoord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getWachtWoord()));
        }
        userRepository.save(user);
    }
}