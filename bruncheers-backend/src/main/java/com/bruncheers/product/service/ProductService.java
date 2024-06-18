package com.bruncheers.product.service;

import java.util.List;

import com.bruncheers.product.dto.ProductDto;
import com.bruncheers.product.dto.ProductInCatDto;

public interface ProductService {
	
	//상품입력
	ProductDto saveProduct(ProductDto productDto) throws Exception;
	
	//상품수정
	ProductDto UpdateProduct(ProductDto productDto)throws Exception;
	
	//상품삭제
	void DeleteProduct(int pNo)throws Exception;
	
	//상품 전체 찾기
	List<ProductDto> SeleteAll();
	
	//상품 번호로 찾기
	ProductDto SeleteByNo(int pNo);
	
	//상품 이름으로 찾기
	//ProductDto SeleteByName(String pName)throws Exception;
	
	//상품 카테고리번호로 찾기
	List <ProductDto> SeleteByCategory(int catNo);
	
	//상품 옵션변호로 찾기
	ProductDto SeleteByProductOption(int poNo);
	
	// 상품 전체 찾기 ( 상품 카테고리와 함께)
	public List<ProductInCatDto> SeleteAllWithProductCat();

}
