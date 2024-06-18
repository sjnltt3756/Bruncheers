/*
package com.bruncheers.product.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bruncheers.product.entity.Product;
import com.bruncheers.product.repository.ProductRepository;

@Repository
public class ProductDaoImpl implements ProductDao {

	@Autowired
	ProductRepository productRepository;
	
	@Override // 상품입력(추가)
	public Product productInsert(Product product) throws Exception {
		Product productInsert = productRepository.save(product);
		return productInsert;
	}

	@Override // 상품수정
	public Product productUpdate(Product product) throws Exception {
		Product productUpdate = productRepository.save(product);
		return productUpdate;
	}

	@Override // 상품삭제
	public int productDelete(int pNo) throws Exception {
		if (!productRepository.findById(pNo).isPresent()) {
			throw new Exception();
		}
		productRepository.deleteById(pNo);
		return 0;
	}
	
	@Override //상품 번호 1개로 찾기
	public int findNo(int pNo)throws Exception{
		productRepository.findById(pNo);
		return 0;
	}
	

	@Override // 상품 전체 찾기
	public List<Product> findAll() throws Exception {
		return productRepository.findAll();
	}

	@Override // 상품 번호로 찾기
	public List<Product> findByNo(int pNo) throws Exception {
		return productRepository.findByNo(pNo); // return
		//productRepository.findByNo(pNo);
	}

	@Override // 상품 이름으로 찾기
	public List<Product> findByName(String pName) throws Exception {
		return productRepository.findByName(pName);
	}

	@Override	//상품 카테고리번호로 찾기
	public List<Product> findByCategory(int cNo) throws Exception {
		return productRepository.findByCategory(cNo);
	}

	@Override	//상품 옵션번호로 찾기
	public List<Product> findByProductOption(int poNo) throws Exception {
		return productRepository.findByProductOption(poNo);
	}

}
*/
