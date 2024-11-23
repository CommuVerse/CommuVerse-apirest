package com.CommuVerse.CommuVerse_api.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "article_images")
public class ArticleImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "image", nullable = false, columnDefinition = "TEXT")
    private String image;

    @ManyToOne
    @JoinColumn(name = "article_id", nullable = false)
    private Article article;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}

