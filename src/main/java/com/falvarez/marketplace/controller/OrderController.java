package com.falvarez.marketplace.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;

import com.falvarez.marketplace.dto.OrderProductDto;
import com.falvarez.marketplace.exception.ResourceNotFoundException;
import com.falvarez.marketplace.model.Order;
import com.falvarez.marketplace.model.OrderProduct;
import com.falvarez.marketplace.model.OrderStatus;
import com.falvarez.marketplace.service.IOrderProductService;
import com.falvarez.marketplace.service.IOrderService;
import com.falvarez.marketplace.service.IProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api/orders")
@Api(tags = "orders")
public class OrderController {

	@Autowired
	IProductService productService;

	@Autowired
	IOrderService orderService;

	@Autowired
	IOrderProductService orderProductService;

	@GetMapping
	@ApiOperation(value = "${OrderController.list}", response = Order.class, responseContainer = "List", authorizations = {
			@Authorization(value = "apiKey") })
	public @NotNull Iterable<Order> listByUser(HttpServletRequest req) {
		return this.orderService.getAllOrdersByUser(req);
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "${OrderController.getById}", response = Order.class, responseContainer = "List", authorizations = {
			@Authorization(value = "apiKey") })
	public Order getById(@PathVariable Integer id, HttpServletRequest req) {
		return this.orderService.getOrderById(id, req);
	}

	@GetMapping("/all")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(value = "${OrderController.listAll}", response = Order.class, responseContainer = "List", authorizations = {
			@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @NotNull Iterable<Order> listAll() {
		return this.orderService.getAllOrders();
	}

	@PostMapping
	@ApiOperation(value = "${OrderController.create}", response = Order.class, authorizations = {
			@Authorization(value = "apiKey") })
	public ResponseEntity<Order> create(@RequestBody OrderForm form, HttpServletRequest req) {
		List<OrderProductDto> formDtos = form.getProductOrders();
		validateProductsExistence(formDtos);
		Order order = new Order();
		order.setStatus(OrderStatus.BUYING.name());
		order = this.orderService.create(order, req);

		List<OrderProduct> orderProducts = new ArrayList<OrderProduct>();
		for (OrderProductDto dto : formDtos) {
			orderProducts.add(orderProductService.create(
					new OrderProduct(order, productService.getProduct(dto.getProduct().getId()), dto.getQuantity())));
		}

		order.setOrderProducts(orderProducts);

		this.orderService.update(order);

		return new ResponseEntity<>(order, HttpStatus.CREATED);
	}

	@PostMapping("/pay")
	@ApiOperation(value = "${OrderController.payOrder}", response = Order.class, authorizations = {
			@Authorization(value = "apiKey") })
	public ResponseEntity<Order> pay(@RequestBody Order order, HttpServletRequest req) {
		Order _order = orderService.payOrder(order, req);
		return new ResponseEntity<>(_order, HttpStatus.CREATED);
	}

	private void validateProductsExistence(List<OrderProductDto> orderProducts) {
		List<OrderProductDto> list = orderProducts.stream()
				.filter(op -> Objects.isNull(productService.getProduct(op.getProduct().getId())))
				.collect(Collectors.toList());

		if (!CollectionUtils.isEmpty(list)) {
			new ResourceNotFoundException("Product not found");
		}
	}

	public static class OrderForm {

		private List<OrderProductDto> productOrders;

		public List<OrderProductDto> getProductOrders() {
			return productOrders;
		}

		public void setProductOrders(List<OrderProductDto> productOrders) {
			this.productOrders = productOrders;
		}
	}

}
