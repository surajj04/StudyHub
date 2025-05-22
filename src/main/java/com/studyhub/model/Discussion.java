package com.studyhub.model;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Discussion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String topic;
    @Column(columnDefinition = "TEXT")
    private String detail;
    @Type(JsonBinaryType.class)
    @Column(columnDefinition = "jsonb")
    private List<Replies> replies;
    private String postBy;
    private LocalDateTime date;
    private int userID;


    @Override
    public String toString() {
        return "Discussion{" +
                "id=" + id +
                ", topic='" + topic + '\'' +
                ", detail='" + detail + '\'' +
                ", replies=" + replies +
                ", postBy='" + postBy + '\'' +
                ", date=" + date +
                ", userID=" + userID +
                '}';
    }
}
