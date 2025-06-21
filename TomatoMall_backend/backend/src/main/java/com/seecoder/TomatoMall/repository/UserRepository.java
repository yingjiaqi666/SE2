package com.seecoder.TomatoMall.repository;

import com.seecoder.TomatoMall.po.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByUsernameAndPassword(String telephone, String password);
}
