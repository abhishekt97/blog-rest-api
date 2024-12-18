package com.app.blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    @Column(name = "email", updatable = false)
    private String email;
    @Column(name = "body", columnDefinition = "TEXT")
    private String body;

    @ManyToOne(fetch = FetchType.LAZY)  //only fetch the related entities when you use the relationship
    @JoinColumn(
            name = "post_id",   //name as foreign key
            nullable = false,
            referencedColumnName = "id",    //name of column referenced by this foreign key column
            foreignKey = @ForeignKey(name = "post_comment_fk")   //control the generation of a foreign key constraint
    )
    private Post post;
}
