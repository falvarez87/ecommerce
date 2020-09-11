package com.falvarez.marketplace.service;

import org.springframework.validation.annotation.Validated;

import com.falvarez.marketplace.model.OrderProduct;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface IOrderProductService {
	OrderProduct create(@NotNull(message = "The products for order cannot be null.") @Valid OrderProduct orderProduct);
}
