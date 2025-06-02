package com.NBP.NBP.services.reports;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JRMapCollectionDataSource;

import java.io.ByteArrayOutputStream;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Collection;
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
                                getClass().getResourceAsStream("/reports/equipment_per_lab_report.jrxml"));

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

        public byte[] generateCategoryReport() throws JRException {
                // SQL upit za čitanje podataka iz specijalno kreirane tabele
                String sql = "SELECT category_id, category_name, equipment_count, report_generated_at " +
                                "FROM NBP08.EQUIPMENT_BY_CATEGORY_REPORT";

                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

                // Mapiraj podatke u JRBeanCollectionDataSource
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rows);

                // Kompajliraj JRXML template
                JasperReport jasperReport = JasperCompileManager.compileReport(
                                getClass().getResourceAsStream("/reports/equipment_per_category_report.jrxml"));

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

        public byte[] generateServiceReport() throws JRException {
                String sql = "SELECT equipment_id, equipment_name, service_count, report_generated_at " +
                                "FROM NBP08.SERVICE_COUNT_PER_EQUIPMENT";

                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rows);

                JasperReport jasperReport = JasperCompileManager.compileReport(
                                getClass().getResourceAsStream("/reports/service_per_equipment_report.jrxml"));

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("REPORT_TITLE", "Service count per equipment");

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

                byte[] generatedPDF = outputStream.toByteArray();

                jdbcTemplate.update(connection -> {
                        PreparedStatement ps = connection.prepareStatement(
                        "INSERT INTO NBP08.GENERATED_REPORTS (report_name, report_file) VALUES (?, ?)");
                        ps.setString(1, "service_per_equipment.pdf");
                        ps.setBytes(2, generatedPDF);
                        return ps;
                });

                return generatedPDF;
        }

        public byte[] generateServiceByEquipmentIdReportOLD(int equipmentId) throws JRException {
                String sql = "SELECT s.description, s.service_date, " +
                                "e.name AS equipment_name, l.name AS laboratory_name, " +
                                "c.name AS category_name, d.name AS department_name " +
                                "FROM service s " +
                                "JOIN equipment e ON s.equipment_id = e.id " +
                                "JOIN laboratory l ON e.laboratory_id = l.id " +
                                "JOIN category c ON e.category_id = c.id " +
                                "JOIN department d ON l.department_id = d.id " +
                                "WHERE s.equipment_id = ? " +
                                "ORDER BY s.service_date DESC";

                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, equipmentId);

                if (rows.isEmpty()) {
                        throw new IllegalArgumentException("No service data found for equipment ID: " + equipmentId);
                }

                System.out.println("All rows:");
                for (Map<String, Object> row : rows) {
                        System.out.println(row);
                }

                Map<String, Object> firstRow = rows.get(0);

                String equipmentName = (String) firstRow.get("EQUIPMENT_NAME");
                String labName = (String) firstRow.get("LABORATORY_NAME");
                String categoryName = (String) firstRow.get("CATEGORY_NAME");
                String departmentName = (String) firstRow.get("DEPARTMENT_NAME");

                Collection<Map<String, ?>> dataCollection = new ArrayList<>();
                for (Map<String, Object> row : rows) {
                        row.remove("EQUIPMENT_NAME");
                        row.remove("LABORATORY_NAME");
                        row.remove("CATEGORY_NAME");
                        row.remove("DEPARTMENT_NAME");
                        dataCollection.add(row);
                }

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("REPORT_TITLE", "Service report for " + equipmentName);
                parameters.put("LABORATORY_NAME", labName);
                parameters.put("CATEGORY_NAME", categoryName);
                parameters.put("DEPARTMENT_NAME", departmentName);

                JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(dataCollection);

                JasperReport jasperReport = JasperCompileManager.compileReport(
                                getClass().getResourceAsStream("/reports/service_by_equipmentId_report.jrxml"));

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

                return outputStream.toByteArray();
        }

        public byte[] generateServiceByEquipmentIdReport(int equipmentId) throws JRException {
                String sql = "SELECT description, service_date, equipment_name, laboratory_name, category_name, department_name "
                                +
                                "FROM service_by_equipment_summary " +
                                "WHERE equipment_id = ? " +
                                "ORDER BY service_date DESC";

                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, equipmentId);

                if (rows.isEmpty()) {
                        throw new IllegalArgumentException("No service data found for equipment ID: " + equipmentId);
                }

                Map<String, Object> firstRow = rows.get(0);

                String equipmentName = (String) firstRow.get("EQUIPMENT_NAME");
                String labName = (String) firstRow.get("LABORATORY_NAME");
                String categoryName = (String) firstRow.get("CATEGORY_NAME");
                String departmentName = (String) firstRow.get("DEPARTMENT_NAME");

                Collection<Map<String, ?>> dataCollection = new ArrayList<>();
                for (Map<String, Object> row : rows) {
                        row.remove("EQUIPMENT_NAME");
                        row.remove("LABORATORY_NAME");
                        row.remove("CATEGORY_NAME");
                        row.remove("DEPARTMENT_NAME");
                        dataCollection.add(row);
                }

                Map<String, Object> parameters = new HashMap<>();
                parameters.put("REPORT_TITLE", "Service report for " + equipmentName);
                parameters.put("LABORATORY_NAME", labName);
                parameters.put("CATEGORY_NAME", categoryName);
                parameters.put("DEPARTMENT_NAME", departmentName);

                JRMapCollectionDataSource dataSource = new JRMapCollectionDataSource(dataCollection);

                JasperReport jasperReport = JasperCompileManager.compileReport(
                                getClass().getResourceAsStream("/reports/service_by_equipmentId_report.jrxml"));

                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

                return outputStream.toByteArray();
        }

        public byte[] generateOrderReport() throws JRException {
                // SQL upit za čitanje podataka iz specijalno kreirane tabele
                String sql = "SELECT supplier_id, supplier_name, order_count, report_generated_at " +
                                "FROM NBP08.ORDERS_PER_SUPPLIER";

                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

                // Mapiraj podatke u JRBeanCollectionDataSource
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rows);

                // Kompajliraj JRXML template
                JasperReport jasperReport = JasperCompileManager.compileReport(
                                getClass().getResourceAsStream("/reports/order_per_supplier_report.jrxml"));

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

        public byte[] generateDepartmentReport() throws JRException {
                // SQL upit za čitanje podataka iz specijalno kreirane tabele
                String sql = "SELECT department_id, department_name, equipment_count, report_generated_at " +
                                "FROM NBP08.EQUIPMENT_BY_DEPARTMENT";

                List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);

                // Mapiraj podatke u JRBeanCollectionDataSource
                JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(rows);

                // Kompajliraj JRXML template
                JasperReport jasperReport = JasperCompileManager.compileReport(
                                getClass().getResourceAsStream("/reports/equipment_per_department_report.jrxml"));

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
