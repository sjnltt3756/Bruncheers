package com.bruncheers.product.dao;

import java.util.List;

import com.bruncheers.product.entity.Product;

public interface ProductDao {
	
	//상품입력
	Product productInsert(Product product) throws Exception;
	
	//상품수정
	Product productUpdate(Product product) throws Exception;
	
	//상품삭제
	int productDelete(int pNo) throws Exception;
	
	//상품 번호 1개로 찾기
	int findNo(int pNo)throws Exception;
	
	//상품 전체 찾기
	List<Product> findAll() throws Exception;
	
	//상품 번호로 찾기
	List<Product> findByNo(int pNo) throws Exception;
	//Product findByNo(int pNo) throws Exception;
	
	//상품 이름으로 찾기
	List<Product> findByName(String pName) throws Exception;
	//Product findByName(String pName) throws Exception;
	
	//상품 카테고리번호로 찾기
	List<Product> findByCategory(int cNo) throws Exception;
	
	//상품 옵션번호로 찾기
	List<Product> findByProductOption(int poNo) throws Exception;
	
}


