package com.group.practicetutorial.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Journal {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    private String heading;
    private String content;
    private LocalDate dateRN;
}
