package com.falvarez.marketplace.controller;

import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falvarez.marketplace.model.Product;
import com.falvarez.marketplace.service.IProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api/products")
@Api(tags = "products")
public class ProductController {

	@Autowired
	IProductService productService;

	@GetMapping
	@ApiOperation(value = "${ProductController.list}", response = Product.class, responseContainer = "List", authorizations = {
			@Authorization(value = "apiKey") })
	public @NotNull Iterable<Product> list() {
		return productService.getAllProducts();
	}
}
