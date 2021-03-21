package com.zhangheng.file.repository;


import com.zhangheng.file.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
