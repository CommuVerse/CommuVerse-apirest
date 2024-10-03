package com.CommuVerse.CommuVerse_api.model.entity;

import jakarta.persistence.*;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type", nullable = false, length = 50)
    private String type;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @Column(name = "publication_date", nullable = false)
    private LocalDateTime publicationDate;

    @Column(name = "status")
    private boolean status;

    @Column(name = "num_reads", nullable = false)
    private Integer numReads ;

    @Column(name = "num_comments", nullable = false)
    private Integer numComments ;

    @Column(name = "num_likes", nullable = false)
    private Integer numLikes ;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDateTime scheduledDate;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User creator;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "article_tags",
            joinColumns = @JoinColumn(name = "article_id"),
            inverseJoinColumns = @JoinColumn(name = "tag-id")
    )
    private Set<Tag> tags = new HashSet<>();
}
