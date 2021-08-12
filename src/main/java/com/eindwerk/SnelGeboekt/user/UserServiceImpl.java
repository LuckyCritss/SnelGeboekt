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
    public User findUser(String needle) {
        if (userRepository.getUserByEmail(needle) != null) {
            return userRepository.getUserByEmail(needle);

        } else if (userRepository.getUserByUserName(needle) != null) {
            return userRepository.getUserByUserName(needle);

        } else {
            return null;
        }
    }

    @Override
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Override
    public void save(User user) throws UserService.PasswordMisMatchException {


        if (!user.getWachtWoord().equals(user.getWachtWoordCheck()) ) {
            throw new UserService.PasswordMisMatchException("password mismatch");
        }
        user.setWachtWoord(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(user.getWachtWoord()));
        userRepository.save(user);
    }
}