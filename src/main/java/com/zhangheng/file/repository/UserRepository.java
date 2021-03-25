package com.zhangheng.file.repository;


import com.zhangheng.file.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

//@Service
//@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, String> {

}
