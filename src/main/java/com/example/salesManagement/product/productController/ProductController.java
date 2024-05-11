package com.example.salesManagement.product.productController;

import com.example.salesManagement.product.dto.ProductRequest;
import com.example.salesManagement.product.dto.ProductResponseDto;
import com.example.salesManagement.product.dto.ProductUpdateRequest;
import com.example.salesManagement.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/product/")
public class ProductController {
    private final ProductService productService;

    @PostMapping("add-product")
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.createProduct(request));
    }

    @PutMapping("update-product/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(productService.updateProduct(productId, request));
    }

    @DeleteMapping("delete-product/{productId}")
    public ResponseEntity<String> deleteProduct(@RequestParam Long sellerId, @PathVariable Long productId) {
        return ResponseEntity.ok(productService.deleteProduct(productId, sellerId));
    }

    @GetMapping("get-product/{productId}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @GetMapping("get-all-product")
    public ResponseEntity<List<ProductResponseDto>> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProducts());
    }
}
