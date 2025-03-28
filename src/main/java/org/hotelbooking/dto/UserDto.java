package org.hotelbooking.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
   @Size(min = 2, max = 20)
    @NotBlank(message = "username can not be blank")
    private String username;

    @NotBlank(message = "email can not be blank")
    @Email(message = "input valid email")
    private String email;


    @Size(min = 7,max = 10)
    @Column(unique = true)
    @NotBlank(message = "Password can not be blank")
    private String password;

}
