package com.cg.controller.rest;


import com.cg.exception.NoCardException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.Order;
import com.cg.model.Orderitem;
import com.cg.model.dto.CartItemDTO;
import com.cg.service.cart.CartService;
import com.cg.service.cartItem.CartItemService;
import com.cg.service.order.OrderService;
import com.cg.service.orderItem.OrderItemService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/carts")

public class CartRestController {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderItemService orderItemService;

    @GetMapping
    public ResponseEntity<?> getAllCartItems() {
        String createdBy = AppUtils.getPrincipalUsername();

        Optional<Cart> cartOptional = cartService.findByCreatedBy(createdBy);
        if (!cartOptional.isPresent()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Long cartId = cartOptional.get().getId();

        List<CartItemDTO> cartItemDTOS = cartItemService.findAllCartItemDTO(cartId);

        if (cartItemDTOS.isEmpty()) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(cartItemDTOS, HttpStatus.OK);
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder() {
        String createdBy = AppUtils.getPrincipalUsername();
        Optional<Cart> cartOptional = cartService.findByCreatedBy(createdBy);

        if (!cartOptional.isPresent()) {
            throw new NoCardException("Không có sản phẩm trong giỏ");
        }

        Order order = new Order();
        order.setId(0L);
        order.setTotalAmount(cartOptional.get().getTotalAmount());
        order.setCreatedBy(createdBy);

        Order newOrder = orderService.save(order);

        Long orderId = newOrder.getId();

        List<CartItem> cartItems = cartItemService.findAllByCart(cartOptional.get());

        if (cartItems.isEmpty()){
            throw new NoCardException("Không có sản phẩm");
        }

        for (CartItem cartItem : cartItems) {
            Orderitem orderItem = new Orderitem();
            orderItem.setId(0L);
            orderItem.setTitle(cartItem.getTitle());
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setAmount(cartItem.getAmount());
            orderItem.setOrder(newOrder);
            orderItem.setCreatedBy(createdBy);
            orderItemService.save(orderItem);

            cartItemService.delete(cartItem);
        }

        cartService.delete(cartOptional.get());


        Map<String, Boolean> result = new HashMap<>();
        result.put("success", true);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
