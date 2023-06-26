package com.cg.service.cartItem;

import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.dto.CartItemDTO;
import com.cg.service.IGeneralService;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface CartItemService extends IGeneralService<CartItem> {
    Optional<CartItem> findByProductId(Long productId);
    BigDecimal sumAmountByCartId(@Param("cartId") Long cartId);
    List<CartItemDTO> findAllCartItemDTO(@Param("cartId") Long cartId);
    List<CartItem> findAllByCart(Cart cart);
    void delete(CartItem cartItem);
}
