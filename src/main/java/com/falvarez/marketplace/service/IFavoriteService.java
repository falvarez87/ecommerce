package com.falvarez.marketplace.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.validation.annotation.Validated;

import com.falvarez.marketplace.model.Favorite;
import com.falvarez.marketplace.model.Product;

@Validated
public interface IFavoriteService {
	@NotNull
	Iterable<Favorite> getAllFavoritesByUser(HttpServletRequest req);

	Favorite create(@NotNull(message = "The product cannot be null.") @Valid Product product, HttpServletRequest req);
}
