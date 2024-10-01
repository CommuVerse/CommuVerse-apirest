package com.CommuVerse.CommuVerse_api.model.entity;

import com.CommuVerse.CommuVerse_api.model.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "nickname", nullable = false)
    private String nickName;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "bio")
    private String bio;

    @Column(name = "profile_picture")
    private String profilePicture;

    @Column(name = "registration_date", nullable = false)
    private LocalDateTime registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role.name()); // Retorna el rol como autoridad
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email; // O puedes usar nickName o cualquier otro campo que desees
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Cambia esto según tu lógica de negocio
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Cambia esto según tu lógica de negocio
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Cambia esto según tu lógica de negocio
    }

    @Override
    public boolean isEnabled() {
        return true; // Cambia esto según tu lógica de negocio
    }
}
