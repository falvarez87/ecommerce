package com.falvarez.marketplace.dto;

import com.falvarez.marketplace.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

public class OrderProductDto {

	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Product product;
	private Integer quantity;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
}