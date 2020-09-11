package com.falvarez.marketplace.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.falvarez.marketplace.model.Favorite;
import com.falvarez.marketplace.model.Product;
import com.falvarez.marketplace.service.IFavoriteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@RestController
@RequestMapping("/api/favorites")
@Api(tags = "favorites")
public class FavoriteController {

	@Autowired
	IFavoriteService favoriteService;

	@GetMapping
	@ApiOperation(value = "${FavoriteController.list}", response = Favorite.class, responseContainer = "List", authorizations = {
			@Authorization(value = "apiKey") })
	public @NotNull Iterable<Favorite> list(HttpServletRequest req) {
		return favoriteService.getAllFavoritesByUser(req);
	}

	@PostMapping
	@ApiOperation(value = "${FavoriteController.create}", response = Favorite.class, authorizations = {
			@Authorization(value = "apiKey") })
	public ResponseEntity<Favorite> create(@RequestBody Product product, HttpServletRequest req) {
		Favorite favorite = favoriteService.create(product, req);
		return new ResponseEntity<Favorite>(favorite, HttpStatus.CREATED);
	}
}
