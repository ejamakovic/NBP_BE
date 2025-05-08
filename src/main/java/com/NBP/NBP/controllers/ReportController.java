package com.NBP.NBP.controllers;

import com.NBP.NBP.services.reports.EquipmentByLabService;
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
    private EquipmentByLabService equipmentByLabService;

    @GetMapping("/equipment-by-lab")
    public ResponseEntity<byte[]> getEquipmentByLaboratoryReport() {
        try {
            byte[] reportData = equipmentByLabService.generateLaboratoryReport();

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
}
