package com.cg.service.cartItem;

import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.dto.CartItemDTO;
import com.cg.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartItemServiceImpl implements CartItemService{
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public Iterable<CartItem> findAll() {
        return null;
    }

    @Override
    public Optional<CartItem> findById(Long id) {
        return cartItemRepository.findById(id);
    }

    @Override
    public CartItem save(CartItem cartItem) {
        return cartItemRepository.save(cartItem);
    }

    @Override
    public void remove(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public Optional<CartItem> findByProductId(Long productId) {
        return cartItemRepository.findByProductId(productId);
    }

    @Override
    public BigDecimal sumAmountByCartId(Long cartId) {
        return cartItemRepository.sumAmountByCartId(cartId);
    }

    @Override
    public List<CartItemDTO> findAllCartItemDTO(Long cartId) {
        return cartItemRepository.findAllCartItemDTO(cartId);
    }

    @Override
    public List<CartItem> findAllByCart(Cart cart) {
        return cartItemRepository.findAllByCart(cart);
    }

    @Override
    public void delete(CartItem cartItem) {
        cartItemRepository.delete(cartItem);
    }
}
