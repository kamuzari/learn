package com.tutorial.elasticsearch.domain;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductDocumentRepository extends ElasticsearchRepository<ProductDocument, Long> {
	List<Product> findByNameContainingOrDescriptionContaining(String keyword, String keyword1);
}
