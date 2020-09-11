package com.falvarez.marketplace.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.falvarez.marketplace.exception.ResourceNotFoundException;
import com.falvarez.marketplace.model.Product;
import com.falvarez.marketplace.repository.ProductRepository;

@Service
public class ProductServiceImpl implements IProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	@Transactional(readOnly = true)
	public Iterable<Product> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Product getProduct(long id) {
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
	}

	@Override
	@Transactional
	public Product save(Product product) {
		return productRepository.save(product);
	}

}
