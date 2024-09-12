package com.Ipaisa.extrpayout;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtrnPayoutRepo extends JpaRepository<InstExtrPayout, Integer> {

}
