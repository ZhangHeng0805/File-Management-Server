package com.zhangheng.file.repository;

import com.zhangheng.file.bean.Merchants;
import com.zhangheng.file.bean.Store;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {
    @Query("select c from Store c where c.store_id=?1 and c.store_name=?2 and c.boss_name=?3")
    List<Store> findByStore_idAndStore_nameAndBoss_name(Integer id, String store_name, String name);
}
