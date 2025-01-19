package com.example.managebook.service;

import com.example.managebook.dto.response.author.AuthorResponse;
import com.example.managebook.dto.response.book.BookCreateResponse;
import com.example.managebook.dto.response.book.BookResponseV1;
import com.example.managebook.dto.response.book.BookResponseV2;
import com.example.managebook.dto.response.book.BookUpdateResponse;
import com.example.managebook.dto.response.general.PageResponse;
import com.example.managebook.dto.resquest.book.BookCreateRequest;
import com.example.managebook.dto.resquest.book.BookUpdateRequest;
import com.example.managebook.entities.Author;
import com.example.managebook.entities.Book;
import com.example.managebook.exception.AppException;
import com.example.managebook.exception.ErrorCode;
import com.example.managebook.exception.InvalidArgumentException;
import com.example.managebook.repository.AuthorRepository;
import com.example.managebook.repository.book.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    BookRepository bookRepository;
    AuthorRepository authorRepository;

    public BookCreateResponse createBook(BookCreateRequest request) {
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new InvalidArgumentException(ErrorCode.INVALID_TITLE);
        }

        if (request.getPublishedDate() == null) {
            throw new InvalidArgumentException(ErrorCode.INVALID_DATE);
        }

        if (request.getIsbn() == null || request.getIsbn().isEmpty()) {
            throw new InvalidArgumentException(ErrorCode.INVALID_ISBN);
        }

        if (request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException(ErrorCode.INVALID_PRICE);
        }
        boolean isbnExists = bookRepository.existsByIsbn(request.getIsbn());
        if (isbnExists) {
            throw new InvalidArgumentException(ErrorCode.DUPLICATE_ISBN);
        }

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new InvalidArgumentException(ErrorCode.INVALID_AUTHOR_ID));

        Book book = Book.builder()
                .title(request.getTitle())
                .author(author)
                .publishedDate(request.getPublishedDate())
                .isbn(request.getIsbn())
                .price(request.getPrice())
                .build();
        Book savedBook = bookRepository.save(book);
        return BookCreateResponse.builder()
                .id(savedBook.getId())
                .title(savedBook.getTitle())
                .authorId(savedBook.getAuthor().getId())
                .publishedDate(savedBook.getPublishedDate())
                .isbn(savedBook.getIsbn())
                .price(savedBook.getPrice())
                .build();
    }

    public BookResponseV1 getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new InvalidArgumentException(ErrorCode.INVALID_BOOK_ID));
        Author author = book.getAuthor();

        AuthorResponse authorResponse = AuthorResponse.builder()
                .id(author.getId())
                .name(author.getName())
                .nationality(author.getNationality())
                .build();

        return BookResponseV1.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(authorResponse)
                .publishedDate(book.getPublishedDate())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .build();
    }

    public PageResponse<BookResponseV2> getBooks(Long authorId, LocalDate publishedDate, String title,
                                                 BigDecimal minPrice, BigDecimal maxPrice,
                                                 int page, int size) {
        // Lấy danh sách sách từ findBooksByCriteria mà không có phân trang
        List<Book> books = bookRepository.findBooksByCriteria(authorId, publishedDate, title, minPrice, maxPrice);

        // Xử lý phân trang thủ công
        int totalElements = books.size();
        int totalPages = (totalElements + size - 1) / size; // Tính tổng số trang
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, totalElements);

        List<Book> pagedBooks = books.subList(fromIndex, toIndex); // Phân trang danh sách sách

        // Chuyển đổi kết quả từ Book sang BookResponseV2
        List<BookResponseV2> bookResponses = pagedBooks.stream().map(book -> BookResponseV2.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorId(book.getAuthor().getId())
                .publishedDate(book.getPublishedDate())
                .isbn(book.getIsbn())
                .price(book.getPrice())
                .build()).toList();

        // Tạo PageResponse để trả về kết quả
        return PageResponse.<BookResponseV2>builder()
                .currentPage(page)
                .totalPages(totalPages)
                .pageSize(size)
                .totalElements(totalElements)
                .data(bookResponses)
                .build();
    }
    public BookUpdateResponse updateBook(Long bookId, BookUpdateRequest request) {
        if (request.getTitle() == null || request.getTitle().isEmpty()) {
            throw new InvalidArgumentException(ErrorCode.INVALID_TITLE);
        }

        if (request.getPublishedDate() == null) {
            throw new InvalidArgumentException(ErrorCode.INVALID_DATE);
        }

        if (request.getPrice() == null || request.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidArgumentException(ErrorCode.INVALID_PRICE);
        }


        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new InvalidArgumentException(ErrorCode.INVALID_BOOK_ID));

        Author author = authorRepository.findById(request.getAuthorId())
                .orElseThrow(() -> new InvalidArgumentException(ErrorCode.INVALID_AUTHOR_ID));

        existingBook.setTitle(request.getTitle());
        existingBook.setAuthor(author);
        existingBook.setPublishedDate(request.getPublishedDate());
        existingBook.setPrice(request.getPrice());

        Book updatedBook = bookRepository.save(existingBook);

        // Trả về DTO BookUpdateResponse
        return BookUpdateResponse.builder()
                .id(updatedBook.getId())
                .title(updatedBook.getTitle())
                .authorId(updatedBook.getAuthor().getId())
                .publishedDate(updatedBook.getPublishedDate())
                .isbn(updatedBook.getIsbn())
                .price(updatedBook.getPrice())
                .build();
    }
    public void deleteBook(Long bookId) {
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new InvalidArgumentException(ErrorCode.INVALID_BOOK_ID));

        bookRepository.delete(existingBook);

        boolean exists = bookRepository.existsById(bookId);
        if (exists) {
            throw new AppException(ErrorCode.INVALID_BOOK_ID);
        }
    }
}
