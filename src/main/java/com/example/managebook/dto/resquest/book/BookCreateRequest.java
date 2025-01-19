package com.example.managebook.dto.resquest.book;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCreateRequest {
    String title;
    Long authorId;
    LocalDate publishedDate;
    String isbn;
    BigDecimal price;
}
