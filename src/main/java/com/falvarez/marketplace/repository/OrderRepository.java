package com.falvarez.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.falvarez.marketplace.model.Order;
import com.falvarez.marketplace.model.User;

public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT O FROM Order O WHERE O.user=?1")
	Iterable<Order> getAllByUser(User user);
}
