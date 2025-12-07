package com.AnnularTechnologies.PaginationSorting.Controller;

import com.AnnularTechnologies.PaginationSorting.Models.Product;
import com.AnnularTechnologies.PaginationSorting.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts(
            @RequestParam(defaultValue = "1") int pageno,
            @RequestParam(defaultValue = "10") int pagesize,
            @RequestParam(defaultValue = "ASC") String sort,
            @RequestParam(defaultValue = "productName") String sortby,
            @RequestParam(required = false) String keyword,        // search keyword
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Integer minStock,
            @RequestParam(required = false) Integer maxStock
    ) {

        Sort sortObj = sort.equalsIgnoreCase("ASC")
                ? Sort.by(sortby).ascending()
                : Sort.by(sortby).descending();

        Pageable pageable = PageRequest.of(pageno - 1, pagesize, sortObj);

        List<Product> products = productService.searchProducts(
                keyword, minPrice, maxPrice, minStock, maxStock, pageable
        );

        return ResponseEntity.ok(products);
    }
}
