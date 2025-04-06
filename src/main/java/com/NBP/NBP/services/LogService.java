package com.NBP.NBP.services;

import com.NBP.NBP.models.Log;
import com.NBP.NBP.repositories.LogRepository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Arrays;
import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;

    private static final List<String> VALID_TABLES = Arrays.asList("category", "department", "equipment", "faculty", "laboratory", "order", "rental", "service", "supplier", "user", "apps", "customUser");

    public LogService(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<Log> getAllLogs() {
        return logRepository.findAll();
    }

    public void createLog(Log log) throws IllegalArgumentException {
        if (!VALID_TABLES.contains(log.getTableName())) {
            throw new IllegalArgumentException("Invalid table name: " + log.getTableName());
        }

        logRepository.save(log);
    }

    public List<Log> getLogsByTableName(String tableName) {
        return logRepository.findByTableName(tableName);
    }

    public void deleteLog(int id) {
        logRepository.delete(id);
    }
}
