package com.bruncheers.product.service;

import java.util.List;

import com.bruncheers.product.dto.ProductOptionDto;
import com.bruncheers.product.dto.ProductOptionDtoInProduct;

public interface ProductOptionService {

	// 상품 옵션 등록
	ProductOptionDto InsertProductOption(ProductOptionDto productOptionDto) throws Exception;

	// 상품 옵션 삭제
	void DeleteProductOption(int poNo) throws Exception;
	
	// 상품 옵션 수정
	ProductOptionDto UpdateProductOption(ProductOptionDto productOptionDto) throws Exception;

	//상품옵션번호로 상품리스트 찾기 
	List<ProductOptionDtoInProduct> FindByProductOption(List<Integer> poNoList);
	
	//상품옵션 리스트로 상품 번호 찾기
	List<ProductOptionDto> findByProductNo(int pNo);
}
