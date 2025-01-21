package com.jsp.SaveImage.entity;



import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "table_user")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;
    private String password;
    private String roles; // Comma-separated roles

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Images> images;

    public Users() {
    }

    public Users(String email, String password, String roles) {
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", password=" + password + ", roles=" + roles + "]";
    }
}
