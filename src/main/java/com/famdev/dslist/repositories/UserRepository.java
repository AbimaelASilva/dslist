package com.famdev.dslist.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.famdev.dslist.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    UserDetails findByLogin(String login);  

}
