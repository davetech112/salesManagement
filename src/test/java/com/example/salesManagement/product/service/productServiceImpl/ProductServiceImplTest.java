package com.example.salesManagement.product.service.productServiceImpl;

import com.example.salesManagement.exception.NotFoundException;
import com.example.salesManagement.product.dto.ProductRequest;
import com.example.salesManagement.product.dto.ProductResponseDto;
import com.example.salesManagement.product.dto.ProductUpdateRequest;
import com.example.salesManagement.product.model.Product;
import com.example.salesManagement.product.productRepository.ProductRepository;
import com.example.salesManagement.reports.service.ProductUpdateService;
import com.example.salesManagement.user.Model.User;
import com.example.salesManagement.user.enums.Role;
import com.example.salesManagement.user.userRepository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductUpdateService productUpdateService;

    @InjectMocks
    private ProductServiceImpl productService;
    @Test
    void createProduct() {
        ProductRequest request = new ProductRequest();
        request.setSellerId(1L);
        request.setProductName("Test Product");
        request.setCategory("Test Category");
        request.setPrice(BigDecimal.TEN);
        request.setQuantity(100L);

        User user = new User();
        user.setId(1L);
        user.setRole(Role.SELLER);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(new Product());

        String result = productService.createProduct(request);

        // Verify
        assertEquals("Product created successfully", result);
    }

    @Test
    public void testUpdateProduct() {
        ProductUpdateRequest updateRequest = new ProductUpdateRequest();
        updateRequest.setUserId(1L);
        updateRequest.setProductName("Updated Product");
        updateRequest.setCategory("Updated Category");
        updateRequest.setPrice(BigDecimal.valueOf(20));
        updateRequest.setQuantity(50L);

        User user = new User();
        user.setId(1L);
        user.setRole(Role.SELLER);

        Product product = new Product();
        product.setId(1L);
        product.setUser(user);
        product.setInitialQuantity(100L);

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);

        String result = productService.updateProduct(1L, updateRequest);

        Mockito.verify(productRepository).save(product);
        Mockito.verify(productUpdateService).updateProductAction(Mockito.any());

        assertEquals("Product updated successfully", result);
        assertEquals("Updated Product", product.getProductName());
        assertEquals("Updated Category", product.getCategory());
        assertEquals(BigDecimal.valueOf(20), product.getPrice());
    }

    @Test
    public void testGetProduct() {
        User user = new User();
        user.setId(1L);
        user.setRole(Role.SELLER);

        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setUser(user);
        product.setCategory("Category");
        product.setProductName("Product Name");
        product.setTotalRevenue(BigDecimal.valueOf(100));
        product.setQuantitySold(50L);
        product.setInitialQuantity(100L);
        product.setPrice(BigDecimal.valueOf(10));

        Mockito.when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        ProductResponseDto responseDto = productService.getProduct(productId);

        assertEquals(productId, responseDto.getId());
        assertEquals(product.getUser().getId(), responseDto.getUserId());
        assertEquals("Category", responseDto.getCategory());
        assertEquals("Product Name", responseDto.getProductName());
        assertEquals(BigDecimal.valueOf(100), responseDto.getTotalRevenue());
        assertEquals(BigDecimal.valueOf(10), responseDto.getPrice());
    }

    @Test
    public void testGetProduct_ProductNotFound() {
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            productService.getProduct(1L);
        });
    }
}