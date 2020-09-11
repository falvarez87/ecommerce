package com.falvarez.marketplace.service;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.falvarez.marketplace.model.OrderProduct;
import com.falvarez.marketplace.repository.OrderProductRepository;

@Service
public class OrderProductServiceImpl implements IOrderProductService {

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Override
	@Transactional
	public OrderProduct create(
			@NotNull(message = "The products for order cannot be null.") @Valid OrderProduct orderProduct) {
		return this.orderProductRepository.save(orderProduct);
	}

}
