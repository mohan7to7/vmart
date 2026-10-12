package com.vmart.cart.repository;

import com.vmart.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUserIdAndStatus(Long userId, String status);

    Optional<Cart> findByGuestTokenHashAndStatus(
            String guestTokenHash,
            String status
    );
}