package com.NBP.NBP.services;

import com.NBP.NBP.models.Service;
import com.NBP.NBP.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@org.springframework.stereotype.Service
public class ServiceService {
    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceService(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    public List<Service> getAllServices() {
        return serviceRepository.findAll();
    }

    public Service getServiceById(int id) {
        return serviceRepository.findById(id);
    }

    public int saveService(Service service) {
        return serviceRepository.save(service);
    }

    public int updateService(Service service) {
        return serviceRepository.update(service);
    }

    public int deleteService(int id) {
        return serviceRepository.delete(id);
    }
}
