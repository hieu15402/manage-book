package com.example.managebook.repository.book;

import com.example.managebook.entities.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<Book> findBooksByCriteria(Long authorId, LocalDate publishedDate, String title, BigDecimal minPrice, BigDecimal maxPrice) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
        Root<Book> bookRoot = criteriaQuery.from(Book.class);

        // Tạo các điều kiện tìm kiếm
        Predicate predicate = criteriaBuilder.conjunction();

        // Kiểm tra authorId nếu có
        if (authorId != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.equal(bookRoot.get("author").get("id"), authorId));
        }

        // Kiểm tra publishedDate nếu có
        if (publishedDate != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(bookRoot.get("publishedDate"), publishedDate));
        }

        // Kiểm tra title nếu có
        if (title != null && !title.isEmpty()) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.like(bookRoot.get("title"), "%" + title + "%"));
        }

        // Kiểm tra minPrice và maxPrice nếu có
        if (minPrice != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.greaterThanOrEqualTo(bookRoot.get("price"), minPrice));
        }

        if (maxPrice != null) {
            predicate = criteriaBuilder.and(predicate, criteriaBuilder.lessThanOrEqualTo(bookRoot.get("price"), maxPrice));
        }

        // Áp dụng điều kiện vào CriteriaQuery
        criteriaQuery.where(predicate);

        // Tạo truy vấn
        TypedQuery<Book> query = entityManager.createQuery(criteriaQuery);

        // Trả về danh sách Book thay vì Page
        return query.getResultList();
    }
}
