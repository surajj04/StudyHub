package com.studyhub.model;

import jakarta.persistence.Column;
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
