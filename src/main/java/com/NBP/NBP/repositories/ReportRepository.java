package com.NBP.NBP.repositories;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.NBP.NBP.models.dtos.GeneratedReportDTO;

@Repository
public class ReportRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReportRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public GeneratedReportDTO findTodaysReport() {
        String sql = """
                SELECT * FROM generated_reports
                WHERE report_name = 'service_per_equipment.pdf' AND TRUNC(generated_at) = TRUNC(SYSDATE)
                AND ROWNUM = 1
                """;

        List<GeneratedReportDTO> reports = jdbcTemplate.query(sql, (rs, rowNum) -> {
            GeneratedReportDTO r = new GeneratedReportDTO();
            r.setId(rs.getInt("id"));
            r.setReportName(rs.getString("report_name"));
            r.setReportFile(rs.getBytes("report_file"));
            r.setGeneratedAt(rs.getTimestamp("generated_at").toLocalDateTime());
            return r;
        });

        return reports.isEmpty() ? null : reports.get(0);
    }

    public void saveReport(String name, byte[] file) {
        String sql = """
                    INSERT INTO generated_reports (report_name, report_file, generated_at)
                    VALUES (?, ?, NOW())
                """;
        jdbcTemplate.update(sql, name, file);
    }
}
