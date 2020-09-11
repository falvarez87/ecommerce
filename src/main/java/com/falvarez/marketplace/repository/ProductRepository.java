package com.falvarez.marketplace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.falvarez.marketplace.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
