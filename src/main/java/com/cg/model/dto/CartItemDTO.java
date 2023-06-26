package com.cg.model.dto;

import com.cg.model.CartItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
public class CartItemDTO {
    private Long id;
    private Long productId;
    private String title;

    private String image;
    private BigDecimal price;
    private int quantity;
    private BigDecimal amount;

    public CartItem cartItem(){
        return new CartItem()
                .setId(id)
                .setProductId(productId)
                .setTitle(title)
                .setImage(image)
                .setPrice(price)
                .setQuantity(quantity)
                .setAmount(amount);
    }
}
