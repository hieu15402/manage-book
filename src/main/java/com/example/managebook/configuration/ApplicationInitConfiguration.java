package com.example.managebook.configuration;

import com.example.managebook.entities.Author;
import com.example.managebook.entities.Book;
import com.example.managebook.repository.AuthorRepository;
import com.example.managebook.repository.book.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfiguration {

    @Bean
    @ConditionalOnProperty(
            prefix = "spring",
            value = "datasource.driver-class-name",
            havingValue = "org.postgresql.Driver"
    )
    ApplicationRunner applicationRunner(AuthorRepository authorRepository, BookRepository bookRepository) {
        log.info("Initializing application");

        return args -> {
            // Thêm tác giả nếu chưa có
            List<Author> authors = authorRepository.findAll();
            if (authors.isEmpty()) {
                Author author1 = Author.builder()
                        .name("Nguyen Minh Hieu")
                        .nationality("Viet Nam")
                        .build();
                Author author2 = Author.builder()
                        .name("Nguyen Minh A")
                        .nationality("Viet Nam")
                        .build();
                authorRepository.save(author1);
                authorRepository.save(author2);

                Book book1 = Book.builder()
                        .title("Advanced Java Programming")
                        .author(author1)
                        .publishedDate(LocalDate.parse("2025-01-11"))
                        .isbn("9780743271246")
                        .price(new BigDecimal("20.11"))
                        .build();

                Book book2 = Book.builder()
                        .title("Mastering Spring Framework")
                        .author(author1)
                        .publishedDate(LocalDate.parse("2024-10-15"))
                        .isbn("9780134685991")
                        .price(new BigDecimal("35.99"))
                        .build();

                Book book3 = Book.builder()
                        .title("Java Design Patterns")
                        .author(author2)
                        .publishedDate(LocalDate.parse("2023-07-22"))
                        .isbn("9780135166307")
                        .price(new BigDecimal("45.50"))
                        .build();

                Book book4 = Book.builder()
                        .title("Spring Boot Essentials")
                        .author(author2)
                        .publishedDate(LocalDate.parse("2024-05-18"))
                        .isbn("9780136576129")
                        .price(new BigDecimal("27.30"))
                        .build();

                Book book5 = Book.builder()
                        .title("Effective Java")
                        .author(author1)
                        .publishedDate(LocalDate.parse("2022-12-03"))
                        .isbn("9780134685991")
                        .price(new BigDecimal("40.00"))
                        .build();

                // Lưu sách vào repository
                bookRepository.save(book1);
                bookRepository.save(book2);
                bookRepository.save(book3);
                bookRepository.save(book4);
                bookRepository.save(book5);
            }

            log.info("Application initialization completed");
        };
    }
}
