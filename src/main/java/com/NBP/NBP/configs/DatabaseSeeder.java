package com.NBP.NBP.configs;

import com.NBP.NBP.models.*;
import com.NBP.NBP.models.enums.EquipmentStatus;
import com.NBP.NBP.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.util.Optional;

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
            CategoryService categoryService,
            DepartmentService departmentService,
            EquipmentService equipmentService,
            FacultyService facultyService,
            LaboratoryService laboratoryService,
            OrderService orderService,
            RentalService rentalService,
            ServiceService serviceService,
            SupplierService supplierService,
            UserService userService,
            RoleService roleService
    ) {
        return args -> {
            Faculty faculty = facultyService.findByName(FACULTY_NAME)
                    .orElseGet(() -> facultyService.create(new Faculty(FACULTY_NAME)));

            Department department = departmentService.findByName(DEPARTMENT_NAME)
                    .orElseGet(() -> departmentService.create(new Department(DEPARTMENT_NAME, faculty.getId())));

            Category category = categoryService.findByName(CATEGORY_NAME)
                    .orElseGet(() -> categoryService.create(new Category(CATEGORY_NAME)));

            Laboratory lab = laboratoryService.findByName(LAB_NAME)
                    .orElseGet(() -> laboratoryService.create(new Laboratory(LAB_NAME, department.getId())));

            Equipment equipment = equipmentService.findByName(EQUIPMENT_NAME)
                    .orElseGet(() -> equipmentService.create(new Equipment(
                            EQUIPMENT_NAME, category.getId(), lab.getId(), EquipmentStatus.LABORATORY)));

            Supplier supplier = supplierService.findByName(SUPPLIER_NAME)
                    .orElseGet(() -> supplierService.create(new Supplier(SUPPLIER_NAME, equipment.getId())));

            Role role = roleService.findByName(ROLE_NAME)
                    .orElseGet(() -> roleService.create(new Role(ROLE_NAME)));

            User user = userService.findByEmail(USER_EMAIL)
                    .orElseGet(() -> userService.create(new User(
                            USER_FIRST_NAME,
                            USER_LAST_NAME,
                            USER_EMAIL,
                            USER_PASSWORD,
                            USER_USERNAME,
                            role.getId()
                    )));

            if (orderService.findByEquipment(equipment).isEmpty()) {
                orderService.create(new Order(
                        equipment.getId(),
                        user.getId(),
                        1000.00,
                        supplier.getId(),
                        "NEW",
                        "INV-001"
                ));
            }

            if (rentalService.findByEquipment(equipment).isEmpty()) {
                rentalService.create(new Rental(
                        equipment.getId(),
                        LocalDate.now(),
                        LocalDate.now().plusDays(7)
                ));
            }

            if (serviceService.findByEquipment(equipment).isEmpty()) {
                serviceService.create(new Service(
                        equipment.getId(),
                        LocalDate.now(),
                        SERVICE_DESCRIPTION
                ));
            }
        };
    }
}
