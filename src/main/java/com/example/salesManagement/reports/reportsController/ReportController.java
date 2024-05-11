package com.example.salesManagement.reports.reportsController;

import com.example.salesManagement.product.dto.ProductReportDto;
import com.example.salesManagement.reports.dto.ClientReportDto;
import com.example.salesManagement.reports.dto.SalesReportDto;
import com.example.salesManagement.reports.model.ProductUpdate;
import com.example.salesManagement.reports.model.UserAction;
import com.example.salesManagement.reports.service.ProductUpdateService;
import com.example.salesManagement.reports.service.SaleService;
import com.example.salesManagement.reports.service.UserActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/report/")
public class ReportController {
    private final SaleService saleService;
    private final ProductUpdateService productUpdateService;
    private final UserActionService userActionService;

    @GetMapping("get-product-report/{productId}")
    public ResponseEntity<ProductReportDto> getProductReport(@PathVariable Long productId) {
        return ResponseEntity.ok(saleService.productReport(productId));
    }

    @GetMapping("get-sales-report")
    public ResponseEntity<SalesReportDto> getSalesReport(
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        return ResponseEntity.ok(saleService.salesReport(startDate, endDate));
    }

    @GetMapping("get-client-report")
    public ResponseEntity<ClientReportDto> getClientReport() {
        return ResponseEntity.ok(saleService.clientReport());
    }

    @GetMapping("get-product-update-audit")
    public ResponseEntity<List<ProductUpdate>> getProductUpdateReport() {
        return ResponseEntity.ok(productUpdateService.productUpdateReport());
    }

    @GetMapping("get-user-action-audit")
    public ResponseEntity<List<UserAction>> getUserActionReport() {
        return ResponseEntity.ok(userActionService.userActionList());
    }
}
