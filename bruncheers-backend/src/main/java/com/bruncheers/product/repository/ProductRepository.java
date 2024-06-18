package com.bruncheers.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.bruncheers.product.entity.Product;
public interface ProductRepository extends JpaRepository<Product, Integer> {

	// 상품번호로 찾기
	@Query("SELECT p FROM Product p WHERE p.pNo = :pNo")
	List<Product> findByNo(@Param("pNo")int pNo);

	// 상품이름으로 찾기
	@Query("SELECT p FROM Product p WHERE p.pName = :pName")
	List<Product> findByName(@Param("pName")String pName);

	// 상품카테고리 번호로 찾기
	/*
	@Query("SELECT p FROM Product p JOIN p.productCategory cat WHERE cat.catNo = :catNo")
	List<Product> findByCategory(@Param("catNo")int catNo);
	*/
	
	@Query("SELECT p FROM Product p JOIN p.productCategory cat WHERE cat.catNo = :catNo")
	List<Product> findByCategory(@Param("catNo")int catNo);
	
	
	// 상품옵션번호로 찾기
	/*
	@Query("SELECT p FROM Product p JOIN p.option po WHERE po.no = :no")
	List<Product> findByProductOption(int poNo);
	 */
	
	// 상품옵션번호로 찾기
	@Query("SELECT p FROM Product p JOIN p.productOptions po WHERE po.poNo = :poNo")
	List<Product> findByProductOption(@Param("poNo") int poNo);


}
