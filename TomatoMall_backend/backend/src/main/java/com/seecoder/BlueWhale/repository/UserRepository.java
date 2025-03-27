package com.seecoder.BlueWhale.repository;

import com.seecoder.BlueWhale.po.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByTelephone(String telephone);
    User findByTelephoneAndPassword(String telephone, String password);
}
