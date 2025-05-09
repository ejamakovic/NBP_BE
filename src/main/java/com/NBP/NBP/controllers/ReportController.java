package com.NBP.NBP.controllers;

import com.NBP.NBP.services.reports.ReportService;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/equipment-by-lab")
    public ResponseEntity<byte[]> getEquipmentByLaboratoryReport() {
        try {
            byte[] reportData = reportService.generateLaboratoryReport();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=laboratory_report.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(reportData);

        } catch (JRException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to generate report".getBytes());
        }
    }

    @GetMapping("/equipment-by-category")
    public ResponseEntity<byte[]> getEquipmentByCategoryReport() {
        try {
            byte[] reportData = reportService.generateCategoryReport();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=category_report.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(reportData);

        } catch (JRException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to generate report".getBytes());
        }
    }

    @GetMapping("/service-by-equipment")
    public ResponseEntity<byte[]> getServiceByEquipmentReport() {
        try {
            byte[] reportData = reportService.generateServiceReport();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=category_report.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(reportData);

        } catch (JRException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to generate report".getBytes());
        }
    }

    @GetMapping("/order-by-supplier")
    public ResponseEntity<byte[]> getOrderBySupplierReport() {
        try {
            byte[] reportData = reportService.generateOrderReport();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=category_report.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(reportData);

        } catch (JRException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to generate report".getBytes());
        }
    }

    @GetMapping("/equipment-by-department")
    public ResponseEntity<byte[]> getEquipmentByDepartmentReport() {
        try {
            byte[] reportData = reportService.generateDepartmentReport();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "inline; filename=category_report.pdf");

            return ResponseEntity.ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(reportData);

        } catch (JRException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to generate report".getBytes());
        }
    }


}
