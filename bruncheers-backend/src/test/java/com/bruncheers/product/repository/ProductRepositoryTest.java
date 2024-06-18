/*
package com.bruncheers.product.repository;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.bruncheers.product.entity.Product;

@SpringBootTest

class ProductRepositoryTest {
	@Autowired
	ProductRepository productRepository;
	
	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testFindByNo()throws Exception {
		List<Product> productList = productRepository.findByNo(1);
		System.out.println(productList);
	}
	
	@Test
	void testFindByName()throws Exception {
		List<Product> productList = productRepository.findByName("샐러드");
		System.out.println(productList);
	}
	
	@Test
	void testFindByCategory()throws Exception {
		List<Product> productList = productRepository.findByCategory(1);
		System.out.println(productList);
	}
		@Test
		void testFindByNo()throws Exception {
			List<Product> productList = productRepository.findByNo(1);
			System.out.println(productList);
		}
		
		@Test
		void testFindByName()throws Exception {
			List<Product> productList = productRepository.findByName("");
			System.out.println(productList);
		}
		
		@Test
		void testFindByCategory()throws Exception {
			List<Product> productList = productRepository.findByCategory(1);
			System.out.println(productList);
		}
	
	@Test
	void testFindByProductOption()throws Exception {
		List<Product> productList = productRepository.findByProductOption(1);
		System.out.println(productList);
	}

}
*/