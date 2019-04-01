package com.myapp.user.repository;

import com.myapp.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUserName(String name);
    User findByEmail(String email);
}
