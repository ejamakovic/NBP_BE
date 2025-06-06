package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Log;
import com.NBP.NBP.services.LogService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @GetMapping
    public List<Log> getAllLogs() {
        return logService.getAllLogs();
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @PostMapping("/log")
    public void logAction(@RequestBody Log log) {
        logService.createLog(log);
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @GetMapping("/table/{tableName}")
    public List<Log> getLogsByTable(@PathVariable String tableName) {
        return logService.getLogsByTableName(tableName);
    }

    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteLog(@PathVariable int id) {
        logService.deleteLog(id);
    }
}
