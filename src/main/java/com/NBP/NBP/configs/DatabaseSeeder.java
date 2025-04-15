package com.NBP.NBP.configs;

import com.NBP.NBP.models.*;
import com.NBP.NBP.models.enums.EquipmentStatus;
import com.NBP.NBP.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseSeeder {

    private static final String FACULTY_NAME = "ETF";
    private static final String DEPARTMENT_NAME = "Računarstvo i informatika";
    private static final String CATEGORY_NAME = "Računar";
    private static final String LAB_NAME = "Lab 1";
    private static final String SUPPLIER_NAME = "Tech Shop";
    private static final String USER_FIRST_NAME = "Ermin";
    private static final String USER_LAST_NAME = "Jamaković";
    private static final String USER_EMAIL = "ermin@etf.unsa.ba";
    private static final String USER_PASSWORD = "password123";
    private static final String USER_USERNAME = "erminjamakovic";
    private static final String ROLE_NAME = "Admin";
    private static final String EQUIPMENT_NAME = "Dell Inspiron";
    private static final String SERVICE_DESCRIPTION = "Zamjena baterije";

    @Bean
    CommandLineRunner seedDatabase(
            CategoryRepository categoryRepository,
            DepartmentRepository departmentRepository,
            EquipmentRepository equipmentRepository,
            FacultyRepository facultyRepository,
            LaboratoryRepository laboratoryRepository,
            OrderRepository orderRepository,
            RentalRepository rentalRepository,
            ServiceRepository serviceRepository,
            SupplierRepository supplierRepository,
            UserRepository userRepository,
            RoleRepository roleRepository
    ) {
        return args -> {
            Faculty faculty = facultyRepository.findByName(FACULTY_NAME)
                    .orElseGet(() -> {
                        facultyRepository.save(new Faculty(FACULTY_NAME));
                        return facultyRepository.findByName(FACULTY_NAME).get();
                    });

            Department department = departmentRepository.findByName(DEPARTMENT_NAME)
                    .orElseGet(() -> {
                        departmentRepository.save(new Department(DEPARTMENT_NAME, faculty.getId()));
                        return departmentRepository.findByName(DEPARTMENT_NAME).get();
                    });

            Category category = categoryRepository.findByName(CATEGORY_NAME)
                    .orElseGet(() -> {
                        categoryRepository.save(new Category(CATEGORY_NAME));
                        return categoryRepository.findByName(CATEGORY_NAME).get();
                    });

            Laboratory lab = laboratoryRepository.findByName(LAB_NAME)
                    .orElseGet(() -> {
                        laboratoryRepository.save(new Laboratory(LAB_NAME, department.getId()));
                        return laboratoryRepository.findByName(LAB_NAME).get();
                    });

            Equipment equipment = equipmentRepository.findByName(EQUIPMENT_NAME)
                    .orElseGet(() -> {
                        equipmentRepository.save(new Equipment(EQUIPMENT_NAME, category.getId(), lab.getId(), EquipmentStatus.LABORATORY));
                        return equipmentRepository.findByName(EQUIPMENT_NAME).get();
                    });

            Supplier supplier = supplierRepository.findByName(SUPPLIER_NAME)
                    .orElseGet(() -> {
                        supplierRepository.save(new Supplier(SUPPLIER_NAME, equipment.getId()));
                        return supplierRepository.findByName(SUPPLIER_NAME).get();
                    });

            Role role = roleRepository.findByName(ROLE_NAME)
                    .orElseGet(() -> {
                        roleRepository.save(new Role(ROLE_NAME));
                        return roleRepository.findByName(ROLE_NAME).get();
                    });

            User user = userRepository.findByEmail(USER_EMAIL);
            if (user == null) {
                user = new User(
                        USER_FIRST_NAME,
                        USER_LAST_NAME,
                        USER_EMAIL,
                        USER_PASSWORD,
                        USER_USERNAME,
                        role.getId()
                );
                userRepository.save(user);
                user = userRepository.findByEmail(USER_EMAIL);
            }

//            orderRepository.findByEquipment(equipment).orElseGet(() ->
//                    orderRepository.save(new Order(equipment, supplier, LocalDate.now(), 1000.00))
//            );
//
//            rentalRepository.findByEquipment(equipment).orElseGet(() ->
//                    rentalRepository.save(new Rental(equipment, user, LocalDate.now(), LocalDate.now().plusDays(7)))
//            );
//
//            serviceRepository.findByEquipment(equipment).orElseGet(() ->
//                    serviceRepository.save(new Service(equipment, SERVICE_DESCRIPTION, LocalDate.now()))
//            );
        };
    }
}
