package com.Ipaisa.Repository;

import com.Ipaisa.Entitys.RequestLog;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}
