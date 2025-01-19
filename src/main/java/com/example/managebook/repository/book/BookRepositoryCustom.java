package com.example.managebook.repository.book;

import com.example.managebook.entities.Book;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepositoryCustom {
    List<Book> findBooksByCriteria(Long authorId, LocalDate publishedDate, String title, BigDecimal minPrice, BigDecimal maxPrice);
}

