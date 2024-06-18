package com.bruncheers.product.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import com.bruncheers.FinalProjectTeam2BruncheersApplicationTests;
import com.bruncheers.product.entity.Product;
import com.bruncheers.product.entity.ProductCategory;
import com.bruncheers.product.entity.ProductOption;

import jakarta.transaction.Transactional;

@SpringBootTest
class ProductOptionRepositoryTest extends FinalProjectTeam2BruncheersApplicationTests {

	@Autowired
	ProductOptionRepository productOptionRepository;
	@Autowired
	ProductRepository productRepository;


	@Test
	@Disabled
	void productOptionWithProductSave() {
		// persist

		Product product = Product.builder().pNo(42).pName("테트상품").pPrice(11044).pReg(new Date()).pDeimg("테스트세.jpg")
				.pImage("테트.jpg").pDetail("상세..").productCategory(ProductCategory.builder().catNo(1).catName("하").build()).build();
		productRepository.save(product);

		ProductOption productOption = ProductOption.builder().poNo(15).poName("테스트 열심히...").poPrice(1004).product(Product.builder().pNo(3).build()).build();
		productOptionRepository.save(productOption);
		
		productOptionRepository.flush(); 
		productRepository.flush();
		
	}

	@Test
	@Disabled
	void update() {
		ProductOption productOption = productOptionRepository.findById(15).get();
		productOption.setPoName("쥔장 변심 5-4");
		productOptionRepository.save(productOption);
	}

	@Test
	@Disabled
	void productOptionWithProductDelete() {
		ProductOption productOption = productOptionRepository.findById(4).get();
		Product products = productOption.getProduct();
		productOptionRepository.delete(productOption);
		productOptionRepository.flush();
	}
	
	@Test
	@Disabled
	@Rollback(false)
	@Transactional
	void DeletePrductOption() {
		productOptionRepository.deleteById(3);
		productOptionRepository.flush();
		assertTrue(productOptionRepository.findById(3).isEmpty());
	}
}
