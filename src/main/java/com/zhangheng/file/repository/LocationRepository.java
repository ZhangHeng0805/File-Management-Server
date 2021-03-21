package com.zhangheng.file.repository;


import com.zhangheng.file.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {

}
