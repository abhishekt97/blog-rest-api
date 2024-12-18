package com.app.blog.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "user_username_constraint",columnNames = "username"),
        @UniqueConstraint(name = "user_password_constraint", columnNames = "password")})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    @Column(name = "username", nullable = false, columnDefinition = "TEXT")
    private String username;
    @Column(name = "password", nullable = false, columnDefinition = "TEXT")
    private String password;
    @Column(name = "email", updatable = false, nullable = false)
    private String email;

    @ManyToMany(fetch = FetchType.EAGER,    //default lazy, here we want to fetch roles immediately when we fetch user so eager.
                cascade = CascadeType.ALL)    //parent child should do all operations together
    @JoinTable(name = "user_roles",
               joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();
}
