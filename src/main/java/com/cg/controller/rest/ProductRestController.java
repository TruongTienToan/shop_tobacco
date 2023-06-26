package com.cg.controller.rest;

import com.cg.exception.DataInputException;
import com.cg.model.Cart;
import com.cg.model.CartItem;
import com.cg.model.Product;
import com.cg.model.dto.ProductCartRequestDTO;
import com.cg.model.dto.ProductDTO;
import com.cg.service.cart.CartService;
import com.cg.service.cartItem.CartItemService;
import com.cg.service.category.ICategoryService;
import com.cg.service.product.IProductService;
import com.cg.utils.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {
    @Autowired
    private IProductService productService;

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private CartService cartService;

    @Autowired
    AppUtils appUtils;

    @Autowired
    private CartItemService cartItemService;

    @GetMapping
    public ResponseEntity<?> showListProduct() {
        List<ProductDTO> products = productService.findAllProductDTO();

        if (products.isEmpty()) {
            return new ResponseEntity<>("Danh sách trống!", HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> productDTO = productService.findProductDTOById(id);

        if (!productDTO.isPresent()) {
            return new ResponseEntity<>("Không tìm thấy sản phẩm có id là:" + id + "!", HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(productDTO.get(), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<?> doCreate(@Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult) {

        new ProductDTO().validate(productDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return AppUtils.errors(bindingResult);
        }

        try {
            Product product = productDTO.toProduct();
            product.setId(0L);
            product.setDeleted(false);
            product = productService.save(product);

            return new ResponseEntity<>(product.toProductDTO(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Server không xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doEdit(@PathVariable Long id, @Validated @RequestBody ProductDTO productDTO,
                                    BindingResult bindingResult) {
        Optional<Product> p = productService.findById(id);

        if (!p.isPresent()) {
            return new ResponseEntity<>("Không tồn tại sản phẩm", HttpStatus.NOT_FOUND);
        }

        new ProductDTO().validate(productDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return AppUtils.errors(bindingResult);
        }

        try {
            Product product = productDTO.toProduct();

            product.setId(p.get().getId());
            product.setDeleted(p.get().isDeleted());

            productDTO = product.toProductDTO();

            productService.save(product);

            return new ResponseEntity<>(productDTO, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Server không xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/block/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<?> doBlock(@PathVariable Long id, @Validated @RequestBody ProductDTO productDTO, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return AppUtils.errors(bindingResult);
        }

        try {
            productService.deleteProductById(id);

            return new ResponseEntity<>(id, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Server không xử lý được", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/search/{query}")
    public ResponseEntity<?> searchListProduct(@PathVariable String query) {
        List<ProductDTO> productDTOList = productService.findProductByValue(query);
        return new ResponseEntity<>(productDTOList, HttpStatus.OK);
    }

    @PostMapping("/add-cart")
    public ResponseEntity<?> addCart(@RequestBody ProductCartRequestDTO productCartRequestDTO) {
        String createBy = AppUtils.getPrincipalUsername();

        Optional<Cart> cartOptional = cartService.findByCreatedBy(createBy);

        Boolean existByCreatedBy = cartService.existsByCreateBy(createBy);

        Long productId = productCartRequestDTO.getId();

        Optional<Product> product = productService.findById(productCartRequestDTO.getId());

        if (!product.isPresent()) {
            throw new DataInputException("Chưa có thông tin");
        }

        if (!cartOptional.isPresent()) {
            Cart cart = new Cart();
            cart.setCreatedBy(createBy);
            cart.setTotalAmount(new BigDecimal(0L));

            Cart newCart = cartService.save(cart);

            CartItem cartItem = new CartItem();
            cartItem.setId(0L);
            cartItem.setProductId(productId);
            cartItem.setTitle(product.get().getName());
            cartItem.setPrice(product.get().getPrice());
            cartItem.setQuantity(1);
            cartItem.setCart(newCart);

            cartItemService.save(cartItem);
            return new ResponseEntity<>(cartItem.cartItemDTO(), HttpStatus.CREATED);
        }

        Optional<CartItem> cartItemOptional = cartItemService.findByProductId(productId);
        if (!cartItemOptional.isPresent()) {
            CartItem cartItem = new CartItem();
            cartItem.setId(0L);
            cartItem.setProductId(productId);
            cartItem.setTitle(product.get().getName());
            cartItem.setPrice(product.get().getPrice());
            cartItem.setQuantity(1);
            cartItem.setAmount(product.get().getPrice());
            cartItem.setCart(cartOptional.get());
            cartItemService.save(cartItem);

            return new ResponseEntity<>(cartItem.cartItemDTO(), HttpStatus.CREATED);
        }
        BigDecimal price = product.get().getPrice();
        int oldQuantity = cartItemOptional.get().getQuantity();
        int newQuantity = oldQuantity + 1;
        BigDecimal newAmount = price.multiply(new BigDecimal((newQuantity)));

        CartItem cartItem = new CartItem();
        cartItem.setId(cartItemOptional.get().getId());
        cartItem.setProductId(productId);
        cartItem.setTitle(product.get().getName());
        cartItem.setPrice(product.get().getPrice());
        cartItem.setQuantity(newQuantity);
        cartItem.setAmount(newAmount);
        cartItem.setCart(cartOptional.get());
        cartItemService.save(cartItem);

        BigDecimal totalAmount = cartItemService.sumAmountByCartId(cartOptional.get().getId());

        Cart cart = new Cart();
        cart.setId(cartOptional.get().getId());
        cart.setCreatedBy(createBy);
        cart.setTotalAmount(totalAmount);

        cartService.save(cart);
        return new ResponseEntity<>(cartItem.cartItemDTO(), HttpStatus.CREATED);
    }
}
