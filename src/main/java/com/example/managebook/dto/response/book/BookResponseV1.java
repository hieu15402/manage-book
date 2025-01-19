package com.example.managebook.dto.response.book;

import com.example.managebook.dto.response.author.AuthorResponse;
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
public class BookResponseV1 {
    Long id;
    String title;
    AuthorResponse author;
    LocalDate publishedDate;
    String isbn;
    BigDecimal price;
}
