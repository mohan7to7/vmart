package com.vmart.cart.repository;

import com.vmart.cart.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findAllByCartId(Long cartId);

    Optional<CartItem> findByCartIdAndVariantId(
            Long cartId,
            Long variantId
    );

    void deleteByCartIdAndVariantId(Long cartId, Long variantId);

    void deleteAllByCartId(Long cartId);
}
