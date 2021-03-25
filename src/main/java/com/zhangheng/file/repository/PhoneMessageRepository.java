package com.zhangheng.file.repository;


import com.zhangheng.file.entity.PhoneMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PhoneMessageRepository extends JpaRepository<PhoneMessage,String> {
}
