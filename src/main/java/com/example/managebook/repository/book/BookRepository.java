package com.example.managebook.repository.book;

import com.example.managebook.entities.Book;
import com.example.managebook.repository.book.BookRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    boolean existsByIsbn(String isbn);
}

