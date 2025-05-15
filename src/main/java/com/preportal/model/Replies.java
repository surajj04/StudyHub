package com.preportal.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Replies {
    private int id;
    private String reply;
    private String name;
    private int userID;
    private LocalDateTime date;

    @Override
    public String toString() {
        return "Replies{" +
                "id=" + id +
                ", reply='" + reply + '\'' +
                ", name='" + name + '\'' +
                ", userID=" + userID +
                ", date=" + date +
                '}';
    }
}
