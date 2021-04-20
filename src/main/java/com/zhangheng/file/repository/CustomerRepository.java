package com.zhangheng.file.repository;


import com.zhangheng.file.bean.Customer;
import com.zhangheng.file.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

//@Service
//@EnableJpaRepositories
@Repository
@Transactional
public interface CustomerRepository extends JpaRepository<Customer, String> {

    @Modifying
    @Query("update Customer sc set sc.address =  ?1 where sc.phone = ?2")
    public void updateAddressByPhone(String address, String phone);
}
