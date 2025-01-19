package com.example.managebook.dto.response.book;

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
public class BookUpdateResponse {
    Long id;
    String title;
    Long authorId;
    LocalDate publishedDate;
    String isbn;
    BigDecimal price;
}
