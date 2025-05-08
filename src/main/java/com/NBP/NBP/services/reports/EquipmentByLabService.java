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
public class EquipmentByLabService {

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
                getClass().getResourceAsStream("/reports/equipment_by_lab_report.jrxml")
        );


        // Pripremi parametre za izveštaj (ako su potrebni, kao što je naziv izveštaja)
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("REPORT_TITLE", "Laboratory Equipment Report");

        // Popuni izveštaj sa podacima
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

        // Izvezi izveštaj u PDF
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);

        return outputStream.toByteArray();
    }
}
