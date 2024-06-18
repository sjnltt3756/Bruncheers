package com.bruncheers.product.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bruncheers.product.dto.ProductDto;
import com.bruncheers.product.dto.ProductInCatDto;
import com.bruncheers.product.entity.Product;
import com.bruncheers.product.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	// 상품등록
	@Override
	public ProductDto saveProduct(ProductDto productDto) throws Exception {
		if (productDto.getPReg() == null) {
			productDto.setPReg(new Date());
		}
		Product productEntity = productRepository.save(Product.toEntity(productDto));

		return ProductDto.toDto(productEntity);

	}

	@Override
	public ProductDto UpdateProduct(ProductDto productDto) throws Exception {
		Product productEntity = productRepository.save(Product.toEntity(productDto));
		return ProductDto.toDto(productEntity);
	}

	@Override
	public void DeleteProduct(int pNo) throws Exception {
		productRepository.deleteById(pNo);
	}

	@Override
	public List<ProductDto> SeleteAll() {
		List<Product> productList = productRepository.findAll();
		List<ProductDto> productDtoList = new ArrayList<>();
		for (Product product : productList) {
			productDtoList.add(ProductDto.toDto(product));
		}
		return productDtoList;
	}

	// 상품 전체 찾기 ( 상품 카테고리와 함께)
	@Override
	public List<ProductInCatDto> SeleteAllWithProductCat() {
		List<Product> productList = productRepository.findAll();
		List<ProductInCatDto> productInCatDto = new ArrayList<>();
		for (Product product : productList) {
			productInCatDto.add(ProductInCatDto.toDto(product));
		}
		return productInCatDto;
	}

	@Override
	public ProductDto SeleteByNo(int pNo) {
		Product productEntity = productRepository.findById(pNo).get();
		ProductDto productDto = ProductDto.toDto(productEntity);
		return productDto;
	}

	@Override
	public List<ProductDto> SeleteByCategory(int catNo) {
		List<Product> productCategoryList = productRepository.findByCategory(catNo);
		List<ProductDto> productDtoCategoryList = new ArrayList<>();
		for (Product product : productCategoryList) {
			productDtoCategoryList.add(ProductDto.toDto(product));
		}
		return productDtoCategoryList;
	}

	/*
	public ProductDto SeleteByCategory(int catNo)  {
		Product productEntity=
				productRepository.findById(catNo).get();
		ProductDto productDto=
				ProductDto.toDto(productEntity);
		return productDto;
	}
	*/

	@Override
	public ProductDto SeleteByProductOption(int poNo) {
		Product productEntity = productRepository.findById(poNo).get();
		ProductDto productDto = ProductDto.toDto(productEntity);
		return productDto;
	}

}
