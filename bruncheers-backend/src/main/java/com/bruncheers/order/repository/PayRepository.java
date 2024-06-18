package com.bruncheers.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruncheers.order.entity.Pay;

public interface PayRepository extends JpaRepository<Pay, Integer> {

	Pay findBypaType(String paType);
}
