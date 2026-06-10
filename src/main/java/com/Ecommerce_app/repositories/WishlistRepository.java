package com.Ecommerce_app.repositories;

import com.Ecommerce_app.entities.Product;
import com.Ecommerce_app.entities.User;
import com.Ecommerce_app.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WishlistRepository
        extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUser(User user);

    Optional<Wishlist> findByUserAndProduct(
            User user,
            Product product);
}
