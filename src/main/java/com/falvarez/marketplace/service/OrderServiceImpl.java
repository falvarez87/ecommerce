package com.falvarez.marketplace.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.falvarez.marketplace.exception.CustomException;
import com.falvarez.marketplace.model.Order;
import com.falvarez.marketplace.model.OrderStatus;
import com.falvarez.marketplace.model.User;
import com.falvarez.marketplace.repository.OrderRepository;
import com.falvarez.marketplace.repository.UserRepository;
import com.falvarez.marketplace.security.JwtTokenProvider;

import java.time.LocalDate;

@Service
public class OrderServiceImpl implements IOrderService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Autowired
	IBitcoinService bitcoinService;

	@Override
	@Transactional(readOnly = true)
	public @NotNull Iterable<Order> getAllOrders() {
		return this.orderRepository.findAll();
	}

	@Override
	@Transactional
	public Order create(@NotNull(message = "The order cannot be null.") @Valid Order order, HttpServletRequest req) {
		order.setDateCreated(LocalDate.now());
		User user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
		order.setUser(user);
		return this.orderRepository.save(order);
	}

	@Override
	@Transactional
	public void update(@NotNull(message = "The order cannot be null.") @Valid Order order) {
		this.orderRepository.save(order);
	}

	@Override
	@Transactional(readOnly = true)
	public @NotNull Iterable<Order> getAllOrdersByUser(HttpServletRequest req) {
		User user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
		return this.orderRepository.getAllByUser(user);
	}

	@Override
	@Transactional(readOnly = true)
	public Order getOrderById(long id, HttpServletRequest req) {
		Order order = this.orderRepository.findById(id).orElse(null);
		if (order == null) {
			throw new CustomException("ORD-001", "The order doesn't exist", HttpStatus.NOT_FOUND,
					req.getSession().getId());
		}
		return order;
	}

	@Override
	@Transactional
	public Order payOrder(@NotNull(message = "The order cannot be null.") @Valid Order order, HttpServletRequest req) {
		Order _order = this.orderRepository.findById(order.getId()).orElse(null);
		if (_order == null) {
			throw new CustomException("ORD-001", "The order doesn't exist", HttpStatus.NOT_FOUND,
					req.getSession().getId());
		}
		if (_order.getStatus().equals(OrderStatus.PAID.name())) {
			throw new CustomException("ORD-002", "The order it's already paid", HttpStatus.NOT_FOUND,
					req.getSession().getId());
		}
		_order.setStatus(OrderStatus.PENDING.name());
		this.orderRepository.save(_order);
		return _order;
	}

}
