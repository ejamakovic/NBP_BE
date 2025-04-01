package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Apps;
import com.NBP.NBP.services.AppsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/apps")
public class AppsController {
    private final AppsService appsService;

    @Autowired
    public AppsController(AppsService appsService) {
        this.appsService = appsService;
    }

    @GetMapping
    public List<Apps> getAllApps() {
        return appsService.getAllApps();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Apps> getAppById(@PathVariable int id) {
        Apps app = appsService.getAppById(id);
        return app != null ? ResponseEntity.ok(app) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<String> createApp(@RequestBody Apps app) {
        int result = appsService.saveApp(app);
        return result > 0 ? ResponseEntity.ok("App created successfully") : ResponseEntity.badRequest().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateApp(@PathVariable int id, @RequestBody Apps app) {
        app.setId(id);
        int result = appsService.updateApp(app);
        return result > 0 ? ResponseEntity.ok("App updated successfully") : ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteApp(@PathVariable int id) {
        int result = appsService.deleteApp(id);
        return result > 0 ? ResponseEntity.ok("App deleted successfully") : ResponseEntity.badRequest().build();
    }
}