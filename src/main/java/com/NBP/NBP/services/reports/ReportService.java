package com.NBP.NBP.services.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public byte[] generateLaboratoryReport() throws JRException {
        // SQL upit za čitanje podataka iz specijalno kreirane tabele
        String sql = "SELECT laboratory_id, laboratory_name, equipment_count, report_generated_at " +
                "FROM NBP08.EQUIPMENT_BY_LAB_REPORT";


        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        // Mapiraj podatke u JRBeanCollectionDataSource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rows);

        // Kompajliraj JRXML template
        JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/equipment_per_lab_report.jrxml")
        );


        // Pripremi parametre za izveštaj (ako su potrebni, kao što je naziv izveštaja)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Equipment count by laboratory");

        // Popuni izveštaj sa podacima
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Izvezi izveštaj u PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }

    public byte[] generateCategoryReport () throws JRException {
        // SQL upit za čitanje podataka iz specijalno kreirane tabele
        String sql = "SELECT category_id, category_name, equipment_count, report_generated_at " +
                "FROM NBP08.EQUIPMENT_BY_CATEGORY_REPORT";


        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        // Mapiraj podatke u JRBeanCollectionDataSource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rows);

        // Kompajliraj JRXML template
        JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/equipment_per_category_report.jrxml")
        );


        // Pripremi parametre za izveštaj (ako su potrebni, kao što je naziv izveštaja)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Equipment count by category");

        // Popuni izveštaj sa podacima
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Izvezi izveštaj u PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }

    public byte[] generateServiceReport () throws JRException {
        // SQL upit za čitanje podataka iz specijalno kreirane tabele
        String sql = "SELECT equipment_id, equipment_name, service_count, report_generated_at " +
                "FROM NBP08.SERVICE_COUNT_PER_EQUIPMENT";


        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        // Mapiraj podatke u JRBeanCollectionDataSource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rows);

        // Kompajliraj JRXML template
        JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/service_per_equipment_report.jrxml")
        );


        // Pripremi parametre za izveštaj (ako su potrebni, kao što je naziv izveštaja)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Service count per equipment");

        // Popuni izveštaj sa podacima
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Izvezi izveštaj u PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }

    public byte[] generateOrderReport () throws JRException {
        // SQL upit za čitanje podataka iz specijalno kreirane tabele
        String sql = "SELECT supplier_id, supplier_name, order_count, report_generated_at " +
                "FROM NBP08.ORDERS_PER_SUPPLIER";


        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        // Mapiraj podatke u JRBeanCollectionDataSource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rows);

        // Kompajliraj JRXML template
        JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/order_per_supplier_report.jrxml")
        );


        // Pripremi parametre za izveštaj (ako su potrebni, kao što je naziv izveštaja)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Order count per supplier");

        // Popuni izveštaj sa podacima
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Izvezi izveštaj u PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }

    public byte[] generateDepartmentReport () throws JRException {
        // SQL upit za čitanje podataka iz specijalno kreirane tabele
        String sql = "SELECT department_id, department_name, equipment_count, report_generated_at " +
                "FROM NBP08.EQUIPMENT_BY_DEPARTMENT";


        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

        // Mapiraj podatke u JRBeanCollectionDataSource
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rows);

        // Kompajliraj JRXML template
        JasperReport jasperReport = JasperCompileManager.compileReport(
                getClass().getResourceAsStream("/reports/equipment_per_department_report.jrxml")
        );


        // Pripremi parametre za izveštaj (ako su potrebni, kao što je naziv izveštaja)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Equipment count by department");

        // Popuni izveštaj sa podacima
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Izvezi izveštaj u PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }


}
