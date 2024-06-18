package com.bruncheers.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bruncheers.product.entity.ProductCategory;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {

}