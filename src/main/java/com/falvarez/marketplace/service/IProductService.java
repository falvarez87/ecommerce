package com.falvarez.marketplace.service;

import org.springframework.validation.annotation.Validated;

import com.falvarez.marketplace.model.Product;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Validated
public interface IProductService {
	@NotNull
	Iterable<Product> getAllProducts();

	Product getProduct(@Min(value = 1L, message = "Invalid product ID.") long id);

	Product save(Product product);
}
