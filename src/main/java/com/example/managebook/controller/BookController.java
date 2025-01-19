package com.example.managebook.controller;

import com.example.managebook.dto.response.book.BookCreateResponse;
import com.example.managebook.dto.response.book.BookResponseV1;
import com.example.managebook.dto.response.book.BookResponseV2;
import com.example.managebook.dto.response.book.BookUpdateResponse;
import com.example.managebook.dto.response.general.GenericResponse;
import com.example.managebook.dto.response.general.PageResponse;
import com.example.managebook.dto.resquest.book.BookCreateRequest;
import com.example.managebook.dto.resquest.book.BookUpdateRequest;
import com.example.managebook.entities.Book;
import com.example.managebook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<GenericResponse<BookCreateResponse>> createBook(@RequestBody BookCreateRequest request) {
        BookCreateResponse response = bookService.createBook(request);
        return ResponseEntity.ok(GenericResponse.<BookCreateResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message("Book created successfully")
                .result(response)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenericResponse<BookResponseV1>> getBookById(@PathVariable Long id) {
        BookResponseV1 response = bookService.getBookById(id);
        return ResponseEntity.ok(GenericResponse.<BookResponseV1>builder()
                .code(HttpStatus.OK.value())
                .message("Book fetched successfully")
                .result(response)
                .build());
    }

    @GetMapping
    public ResponseEntity<GenericResponse<PageResponse<BookResponseV2>>> getBooks(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "authorId", required = false) Long authorId,
            @RequestParam(value = "publishedDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate publishedDate,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "minPrice", required = false) BigDecimal minPrice,
            @RequestParam(value = "maxPrice", required = false) BigDecimal maxPrice) {

        // Gọi dịch vụ để lấy danh sách sách đã lọc
        PageResponse<BookResponseV2> response = bookService.getBooks(authorId, publishedDate, title, minPrice,maxPrice
                , page, size);

        // Trả về kết quả
        return ResponseEntity.ok(GenericResponse.<PageResponse<BookResponseV2>>builder()
                .code(HttpStatus.OK.value())
                .message("Books fetched successfully")
                .result(response)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenericResponse<BookUpdateResponse>> updateBook(
            @PathVariable Long id,
            @RequestBody BookUpdateRequest request) {
        BookUpdateResponse response = bookService.updateBook(id, request);
        return ResponseEntity.ok(GenericResponse.<BookUpdateResponse>builder()
                .code(HttpStatus.OK.value())
                .message("Book updated successfully")
                .result(response)
                .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<GenericResponse<Void>> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok(GenericResponse.<Void>builder()
                .code(HttpStatus.OK.value())
                .message("Book deleted successfully")
                .build());
    }
}

