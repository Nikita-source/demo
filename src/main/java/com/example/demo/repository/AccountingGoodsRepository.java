package com.example.demo.repository;

import com.example.demo.entity.AccountingGoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountingGoodsRepository extends JpaRepository<AccountingGoodsEntity, Long> {
    AccountingGoodsRepository findByProduct_Id(Long product_id);

    AccountingGoodsRepository findByProduct_Title(String product_title);
}
