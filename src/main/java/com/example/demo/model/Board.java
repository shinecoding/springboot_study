package com.example.demo.model;


import lombok.Data;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Data
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Size(min=2, max = 30, message = "제목은 2자 이상 30자 이하입니다.2")
    private String title;
    private String content;










    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
