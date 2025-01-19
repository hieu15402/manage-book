package com.example.managebook.entities;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(nullable = false)
    String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    Author author;

    @Column(name = "published_date")
    LocalDate publishedDate;

    @Column(nullable = false, unique = true, length = 13)
    String isbn;

    @Column(nullable = false, precision = 10, scale = 2)
    BigDecimal price;
}
