package com.falvarez.marketplace.service;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.falvarez.marketplace.model.Favorite;
import com.falvarez.marketplace.model.Product;
import com.falvarez.marketplace.model.User;
import com.falvarez.marketplace.repository.FavoriteRepository;
import com.falvarez.marketplace.repository.UserRepository;
import com.falvarez.marketplace.security.JwtTokenProvider;

@Service
public class FavoriteServiceImpl implements IFavoriteService {

	@Autowired
	FavoriteRepository favoriteRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@Override
	public @NotNull Iterable<Favorite> getAllFavoritesByUser(HttpServletRequest req) {
		User user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
		return favoriteRepository.getAllFavoritesByUser(user);
	}

	@Override
	public Favorite create(@NotNull(message = "The product cannot be null.") @Valid Product product,
			HttpServletRequest req) {
		User user = userRepository.findByUsername(jwtTokenProvider.getUsername(jwtTokenProvider.resolveToken(req)));
		Favorite favorite = new Favorite();
		favorite.setUser(user);
		favorite.setProduct(product);
		return favoriteRepository.save(favorite);
	}

}
