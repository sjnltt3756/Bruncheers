package com.bruncheers.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruncheers.exception.OrderNotFoundException;
import com.bruncheers.product.dto.ProductOptionDto;
import com.bruncheers.product.dto.ProductOptionDtoInProduct;
import com.bruncheers.product.entity.ProductOption;
import com.bruncheers.product.repository.ProductOptionRepository;

@Service
public class ProductOptionServiceImpl implements ProductOptionService {

	@Autowired
	private ProductOptionRepository productOptionRepository;

	// 상품 옵션 등록
	@Override
	public ProductOptionDto InsertProductOption(ProductOptionDto productOptionDto) throws Exception {
		ProductOption productOptionEntity = productOptionRepository.save(ProductOption.toEntity(productOptionDto));
		return ProductOptionDto.toDto(productOptionEntity);
	}

	// 상품 옵션 수정
	@Override
	public ProductOptionDto UpdateProductOption(ProductOptionDto productOptionDto) throws Exception {
		ProductOption productOptionEntity = productOptionRepository.save(ProductOption.toEntity(productOptionDto));
		return ProductOptionDto.toDto(productOptionEntity);
	}

	// 상품 옵션 삭제
	@Override
	public void DeleteProductOption(int poNo) throws Exception {
		productOptionRepository.deleteById(poNo);
	}

	// 상품번호로 상품리스트 찾기
	@Override
	public List<ProductOptionDtoInProduct> FindByProductOption(List<Integer> poNoList) {

		List<ProductOption> productOptionList = new ArrayList<>();

		for (Integer poNo : poNoList) {
			productOptionList.add(productOptionRepository.findById(poNo).get());

		}

		List<ProductOptionDtoInProduct> productOptionDtoList = new ArrayList<>();

		for (ProductOption option : productOptionList) {
			productOptionDtoList.add(ProductOptionDtoInProduct.toDto(option));
		}

		return productOptionDtoList;
	}
	
	//상품옵션 리스트로 상품 번호 찾기
	@Override
	public List<ProductOptionDto> findByProductNo(int pNo) {
		List<ProductOption> productOptionList =
				productOptionRepository.findByProduct_pNo(pNo);
		List<ProductOptionDto> productOptionDtoList = new ArrayList<>();
		for(ProductOption productOption : productOptionList) {
			productOptionDtoList.add(ProductOptionDto.toDto(productOption));
		}
		return productOptionDtoList;
	}
	
	
	
	

}