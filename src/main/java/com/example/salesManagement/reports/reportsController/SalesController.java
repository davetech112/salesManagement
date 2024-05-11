package com.example.salesManagement.reports.reportsController;

import com.example.salesManagement.reports.dto.SalesDTO;
import com.example.salesManagement.reports.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/sale/")
public class SalesController {
    private final SaleService saleService;

    @PostMapping("make-sale")
    public ResponseEntity<String> sellProduct(@RequestBody List<SalesDTO> salesDTOs) {
        return ResponseEntity.ok(saleService.processSales(salesDTOs));
    }


}
