package com.CommuVerse.CommuVerse_api.model.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tags")
@Data
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombreEtiqueta;

    @Column
    private String descripcion;

    @ManyToMany(mappedBy = "tags")
    private Set<Article> articles = new HashSet<>();
}
