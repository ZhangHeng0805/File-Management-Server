package com.zhangheng.file.repository;

import com.zhangheng.file.bean.Merchants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerchantsRepository extends JpaRepository<Merchants, String> {
}
