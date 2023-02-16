package com.jeesuite.admin.dao.repository;

import com.jeesuite.admin.dao.entity.FoRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoRateRepository extends JpaRepository<FoRate, Long> {

    @Query(value = "select * from fo_rate t order by create_time desc limit 1 ", nativeQuery = true)
    FoRate findLastOne();
}
