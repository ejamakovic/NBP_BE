package com.NBP.NBP.services;

import com.NBP.NBP.models.Apps;
import com.NBP.NBP.repositories.AppsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppsService {
    private final AppsRepository appsRepository;

    @Autowired
    public AppsService(AppsRepository appsRepository) {
        this.appsRepository = appsRepository;
    }

    public List<Apps> getAllApps() {
        return appsRepository.findAll();
    }

    public Apps getAppById(int id) {
        return appsRepository.findById(id);
    }

    public int saveApp(Apps app) {
        return appsRepository.save(app);
    }

    public int updateApp(Apps app) {
        return appsRepository.update(app);
    }

    public int deleteApp(int id) {
        return appsRepository.delete(id);
    }
}