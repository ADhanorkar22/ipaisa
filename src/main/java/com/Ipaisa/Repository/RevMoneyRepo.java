package com.Ipaisa.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.Ipaisa.Entitys.ReverseMoney;
@Repository
public interface RevMoneyRepo extends JpaRepository<ReverseMoney, Integer> {
}