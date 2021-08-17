package com.eindwerk.SnelGeboekt.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u from User u Where u.email = :email")
    public User getUserByEmail(@Param("email") String email);

    @Query("SELECT u from User u Where u.gebruikersNaam = :gebruikersNaam")
    public User getUserByUserName(@Param("gebruikersNaam") String gebruikersNaam);

    @Override
    @Query("SELECT u from User u")
    public List<User> findAll();

    public User getUserById(Integer integer);

}

