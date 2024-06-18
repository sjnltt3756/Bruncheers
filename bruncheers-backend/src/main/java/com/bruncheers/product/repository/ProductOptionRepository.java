package com.bruncheers.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruncheers.product.entity.ProductOption;
public interface ProductOptionRepository extends JpaRepository<ProductOption, Integer>{
	
	List<ProductOption> findByProduct_pNo(Integer pNo);

}
