package com.example.demo.model;

import lombok.Data;
import javax.persistence.Entity;

@Entity
@Data
public class Board {
    private Long id;
    private String title;
    private String content;

}
