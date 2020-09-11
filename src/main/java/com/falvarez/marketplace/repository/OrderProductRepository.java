package com.falvarez.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.falvarez.marketplace.model.OrderProduct;
import com.falvarez.marketplace.model.OrderProductPK;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {

}
