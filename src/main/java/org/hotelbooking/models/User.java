package org.hotelbooking.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @NotBlank(message = "username can not be blank")
    @Size(max = 7)
    @Column(nullable = false,unique = true)
    private String username;

    @Size(min = 8, max = 20)
    @NotBlank(message = "password can not be blank")
    @Column(nullable = false,unique = true)
    private String password;

    @Email(message = "Invalid email format")
    @Column(nullable = false,unique = true)
    private String email;


}
