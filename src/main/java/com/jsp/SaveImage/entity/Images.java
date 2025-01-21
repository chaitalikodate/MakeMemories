package com.jsp.SaveImage.entity;

import jakarta.persistence.*;
import java.sql.Blob;
import java.util.Date;

@Entity
@Table(name = "image_table")
public class Images {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Lob
    private Blob image;

    private Date date = new Date();

    private String note;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }

    public Date getDate() {
        return date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}
