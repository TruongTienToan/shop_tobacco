package com.cg.model.dto;


import com.cg.model.Category;
import com.cg.model.Product;
import com.cg.utils.ValidDateUtils;
import com.cg.validates.ValidationStepOne;
import com.cg.validates.ValidationStepTwo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Accessors(chain = true)
@GroupSequence({ValidationStepOne.class, ValidationStepTwo.class, ProductDTO.class})
public class ProductDTO implements Validator{
//    @Autowired
//    private ICategoryService categoryService;

    private Long id;


    @Size(min = 10, max = 50, message = "Tên chỉ được phép gồm 10-50 kí tự!")
    @NotBlank(message = "Tên không được để trống!",groups = ValidationStepOne.class)
    private String name;


    @Size(max = 16000, message = "Đường dẫn ảnh quá dài vượt quá 16000 kí tự!")
    @NotBlank(message = "Đường dẫn ảnh không được để trống!",groups = ValidationStepOne.class)
    private String image;

    private BigDecimal price;

    private CategoryDTO category;

    public ProductDTO(Long id, String name, String image, BigDecimal price, Category category) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.price = price;
        this.category = category.toCategoryDTO();
    }

    public Product toProduct() {
        return new Product()
                .setName(name)
                .setImage(image)
                .setPrice(price)
                .setCategory(category.toCategory());
    }

    @Override
    public boolean supports(Class<?> aClass) {

        return ProductDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ProductDTO productDTO = (ProductDTO) o;
        String price = productDTO.getPrice().toString();


        if (!ValidDateUtils.isNumberValid(price)) {

            if (price == null || price.equals("")) {
                errors.rejectValue("price", "400", "Giá không được để trống!");
            } else {
                errors.rejectValue("price", "400", "Vui lòng nhập giá hợp lệ!");
            }

        } else {
            if (price.length() > 10) {
                errors.rejectValue("price", "400", "Giá tối đa của một sản phẩm là 1.000.000.000đ!");
            } else {

                long validPrice = Long.parseLong(price);
                if (validPrice < 0) {
                    errors.rejectValue("price", "400", "Giá sản phẩm không được âm!");
                }

                if (validPrice > 1000000000) {
                    errors.rejectValue("price", "400", "Giá tối đa của một sản phẩm là 1.000.000.000đ!");
                }
            }
        }
    }
}
