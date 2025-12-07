package com.AnnularTechnologies.PaginationSorting.Service;

import com.AnnularTechnologies.PaginationSorting.Models.Product;
import com.AnnularTechnologies.PaginationSorting.Repository.ProductRepo;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepository;

    public List<Product> searchProducts(
            String keyword,
            Double minPrice,
            Double maxPrice,
            Integer minStock,
            Integer maxStock,
            Pageable pageable
    ) {
        return productRepository.findAll((root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            // Product Name or Description contains keyword
            if (keyword != null && !keyword.isEmpty()) {
                Predicate namePredicate = cb.like(cb.lower(root.get("productName")), "%" + keyword.toLowerCase() + "%");
                Predicate descPredicate = cb.like(cb.lower(root.get("productDescription")), "%" + keyword.toLowerCase() + "%");
                predicates.add(cb.or(namePredicate, descPredicate));
            }

            if (minPrice != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("productPrice"), minPrice));
            }

            if (maxPrice != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("productPrice"), maxPrice));
            }

            if (minStock != null) {
                predicates.add(cb.greaterThanOrEqualTo(root.get("productStock"), minStock));
            }

            if (maxStock != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("productStock"), maxStock));
            }

            return cb.and(predicates.toArray(new Predicate[0]));

        }, pageable).getContent();
    }
}
