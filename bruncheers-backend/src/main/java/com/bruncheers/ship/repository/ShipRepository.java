package com.bruncheers.ship.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bruncheers.ship.entity.Ship;

public interface ShipRepository extends JpaRepository<Ship, Integer>{

	List<Ship> findByUserUserNo(Long userNo);
	
	List<Ship> findBysType(String sType);
	
	@Query(value = "SELECT * FROM (SELECT * FROM ship WHERE user_no = :userNo ORDER BY s_no) WHERE ROWNUM = 1", nativeQuery = true)
	Ship findFirstByUserNoOrderBySNoAsc(@Param("userNo") Long userNo);
}
