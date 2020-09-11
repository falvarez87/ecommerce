package com.falvarez.marketplace.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.falvarez.marketplace.model.Favorite;
import com.falvarez.marketplace.model.User;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

	@Query("SELECT F FROM Favorite F WHERE F.user=?1")
	List<Favorite> getAllFavoritesByUser(User user);
}
