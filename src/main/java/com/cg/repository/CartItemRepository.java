package com.cg.repository;

import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.dto.CartItemDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductId(Long productId);

    @Query("SELECT SUM(ci.amount) FROM CartItem  AS ci WHERE ci.cart.id = :cartId")
    BigDecimal sumAmountByCartId(@Param("cartId") Long cartId);

    @Query("SELECT NEW com.cg.model.dto.CartItemDTO(" +
            "ci.id, " +
            "ci.productId, " +
            "ci.title, " +
            "ci.image, " +
            "ci.price, " +
            "ci.quantity, " +
            "ci.amount" +
            ") FROM CartItem  AS ci WHERE ci.cart.id = :cartId")
    List<CartItemDTO> findAllCartItemDTO(@Param("cartId") Long cartId);

    List<CartItem> findAllByCart(Cart cart);
}
