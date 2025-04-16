package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByUsernameAndPassword(String telephone, String password);
}
