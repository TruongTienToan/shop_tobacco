package com.cg.model.dto;

import com.cg.model.Role;
import com.cg.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.lang.annotation.Annotation;


@Getter
@Setter
@NoArgsConstructor
//@AllArgsConstructor
@Accessors(chain = true)
public class UserDTO implements Validator {

    private Long id;

    @NotBlank(message = "The email is required")
    @Email(message = "The email address is invalid")
    @Size(max = 50, message = "The length of email must be between 5 and 50 characters")
    private String username;

    @NotBlank(message = "The password is required")
    @Size(max = 30, message = "Maximum password length 30 characters")
    private String password;

    private String fullName;

    private String phone;

    private String address;

    @Valid
    private RoleDTO role;

    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserDTO(Long id, String username, String password, String phone, String address, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.role = role.toRoleDTO();
    }

    public User toUser() {
        return new User()
                .setId(id)
                .setUsername(username)
                .setPassword(password)
                .setPhone(phone)
                .setAddress(address)
                .setRole(role.toRole());
    }


    @Override
    public boolean supports(Class<?> aClass) {
        return UserDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        String usenameCheck = userDTO.getUsername();
        String fullnameCheck = userDTO.getFullName();
        String passwordCheck = userDTO.getPassword();
        String phoneCheck = userDTO.getPhone();
        String addressCheck = userDTO.getAddress();

        if (phoneCheck.trim().isEmpty()){
            errors.rejectValue("username","username.isEmpty", "vui long nhap email");
        }
    }
}
