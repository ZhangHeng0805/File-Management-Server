package com.zhangheng.file.repository;


import com.zhangheng.file.entity.ChatConfig;
import com.zhangheng.file.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ChatConfigRepository extends JpaRepository<ChatConfig, String> {
}
