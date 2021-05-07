package com.zhangheng.file.repository;


import com.zhangheng.file.entity.Delete_Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DeleteImageRepository extends JpaRepository<Delete_Image, Integer> {
}
