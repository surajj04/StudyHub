package com.studyhub.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails {
    private int id;
    private String name;
    private String email;
    private String institute;
    private String course;

    @Override
    public String toString() {
        return "CustomUserDetails{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", institute='" + institute + '\'' +
                ", course='" + course + '\'' +
                '}';
    }
}
