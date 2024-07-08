package com.example.milanarestoran.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String Name;
    private String Email;

    @Column(length = 900)
    private String Message;

    private String phone;

    @Enumerated(EnumType.STRING)
    private Position position;

    public enum Position {
        ОФИЦИАНТ,
        БАРМЕН,
        АДМИНИСТРАТОР,
        ШЕФ_ПОВАР,
        ПОВАР,
        ХОСТЕС,
        ПОСУДОМОЙЩИК,
        КОНДИТЕР,
        УБОРЩИК,
        ОХРАННИК
    }


}
