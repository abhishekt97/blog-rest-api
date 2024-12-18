package com.app.blog.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table( name = "posts", uniqueConstraints = @UniqueConstraint(name = "post_title_constraint", columnNames = {"title"}))
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "title", nullable = false, columnDefinition = "TEXT")
    private String title;
    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;
    @OneToMany(
            mappedBy = "post",   //field that owns the relationship, this post is present in comment entity
            cascade = CascadeType.ALL,   //parent child should do all operations together
            orphanRemoval = true    //whenever remove parent child should also get removed with it.
    )
    Set<Comment> comments = new HashSet<>();

}
