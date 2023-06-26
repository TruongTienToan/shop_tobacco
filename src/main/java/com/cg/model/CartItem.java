package com.cg.model;

import com.cg.model.dto.CartItemDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cartItems")
@Accessors(chain = true)
public class CartItem extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_id")
    private Long productId;

    private String title;

    private String image;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id",nullable = false)
    private Cart cart;

    public CartItemDTO cartItemDTO(){
        return new CartItemDTO()
                .setId(id)
                .setProductId(productId)
                .setTitle(title)
                .setImage(image)
                .setPrice(price)
                .setQuantity(quantity)
                .setAmount(amount);
    }
}
