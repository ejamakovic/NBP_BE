package com.NBP.NBP.controllers;

import com.NBP.NBP.models.Equipment;
import com.NBP.NBP.models.Service;
import com.NBP.NBP.services.ServiceService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/services")
public class ServiceController {

    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public List<Service> getAllServices() {
        return serviceService.getAllServices();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public Service getServiceById(@PathVariable int id) {
        return serviceService.getServiceById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public void createService(@RequestBody Service service) {
        serviceService.saveService(service);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public void updateService(@PathVariable int id, @RequestBody Service service) {
        service.setId(id);
        serviceService.updateService(service);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('NBP08_ADMIN')")
    public void deleteService(@PathVariable int id) {
        serviceService.deleteService(id);
    }

    @GetMapping("/equipment/{equipmentId}")
    public List<Service> getServicesByEquipment(@PathVariable int equipmentId) {
        return serviceService.findByEquipmentId(equipmentId);
    }
}
