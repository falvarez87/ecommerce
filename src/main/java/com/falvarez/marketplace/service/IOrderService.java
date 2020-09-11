package com.falvarez.marketplace.service;

import org.springframework.validation.annotation.Validated;

import com.falvarez.marketplace.model.Order;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
public interface IOrderService {

	@NotNull
	Iterable<Order> getAllOrders();

	@NotNull
	Iterable<Order> getAllOrdersByUser(HttpServletRequest req);

	Order create(@NotNull(message = "The order cannot be null.") @Valid Order order, HttpServletRequest req);

	void update(@NotNull(message = "The order cannot be null.") @Valid Order order);

	Order getOrderById(long id, HttpServletRequest req);

	Order payOrder(@NotNull(message = "The order cannot be null.") @Valid Order order, HttpServletRequest req);
}
