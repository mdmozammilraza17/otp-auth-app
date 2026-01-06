package com.auth.user_auth.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table (name = "users")
@ToString (exclude = "password")
public class User {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (nullable = false, unique = true)
    private String username;

    @Column (nullable = false, length = 30)
    private String firstName;

    @Column (nullable = false, length = 20)
    private String lastName;

    @Column (nullable = false, length = 100)
    private String password;

    @Column (nullable = false, unique = true)
    private String email;

    @Column (nullable = false)
    private String role;

    private Boolean enabled;

}
