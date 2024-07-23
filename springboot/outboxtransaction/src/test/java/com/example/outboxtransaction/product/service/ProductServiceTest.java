package com.example.outboxtransaction.product.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import net.datafaker.Faker;

import com.example.outboxtransaction.product.domain.ProductEntity;
import com.example.outboxtransaction.product.domain.ProductRepository;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@InjectMocks
	ProductService productService;

	@Mock
	ProductMessageService productMessageService;

	@Mock
	ProductRepository productRepository;

	@Spy
	ProductMapper productMapper;

	Faker faker = new Faker();

	@Test
	void create() {
		/// given
		ProductEntity savedProduct = new ProductEntity(faker.commerce().productName());
		BDDMockito.given(productRepository.save(any())).willReturn(savedProduct);

		// when
		// then
		assertThatThrownBy(() -> productService.create())
			.isInstanceOf(RuntimeException.class);
		verify(productMessageService, times(1)).sendToSearchingServer(any(), any());
	}
}