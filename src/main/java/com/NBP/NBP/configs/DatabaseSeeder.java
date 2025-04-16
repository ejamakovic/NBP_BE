package com.NBP.NBP.configs;

import com.NBP.NBP.models.*;
import com.NBP.NBP.models.enums.EquipmentStatus;
import com.NBP.NBP.models.enums.OrderStatus;
import com.NBP.NBP.models.enums.UserType;
import com.NBP.NBP.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DatabaseSeeder {

    private static final String FACULTY_NAME = "ETF";
    private static final String DEPARTMENT_NAME = "Računarstvo i informatika";
    private static final String CATEGORY_NAME = "Računar";
    private static final String LAB_NAME = "Lab 1";
    private static final String SUPPLIER_NAME = "Tech Shop";
    private static final String USER_FIRST_NAME = "Ermina";
    private static final String USER_LAST_NAME = "Jamaković";
    private static final String USER_EMAIL = "ermina@etf.unsa.ba";
    private static final String USER_PASSWORD = "password123";
    private static final String USER_USERNAME = "erminajamakovic";
    private static final String ROLE_NAME = "Slastičar";
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
            RoleService roleService,
            CustomUserService customUserService
    ) {
        return args -> {
            Faculty faculty = facultyService.findByName(FACULTY_NAME)
                    .orElseGet(() -> {
                        facultyService.saveFaculty(new Faculty(FACULTY_NAME));
                        return facultyService.findByName(FACULTY_NAME).orElseThrow();
                    });

            Department department = departmentService.findByName(DEPARTMENT_NAME)
                    .orElseGet(() -> {
                        departmentService.saveDepartment(new Department(DEPARTMENT_NAME, faculty.getId()));
                        return departmentService.findByName(DEPARTMENT_NAME).orElseThrow();
                    });

            Category category = categoryService.findByName(CATEGORY_NAME)
                    .orElseGet(() -> {
                        categoryService.saveCategory(new Category(CATEGORY_NAME));
                        return categoryService.findByName(CATEGORY_NAME).orElseThrow();
                    });

            Laboratory lab = laboratoryService.findByName(LAB_NAME)
                    .orElseGet(() -> {
                        laboratoryService.saveLaboratory(new Laboratory(LAB_NAME, department.getId()));
                        return laboratoryService.findByName(LAB_NAME).orElseThrow();
                    });

            Equipment equipment = equipmentService.findByName(EQUIPMENT_NAME)
                    .orElseGet(() -> {
                        equipmentService.saveEquipment(new Equipment(
                                EQUIPMENT_NAME, category.getId(), lab.getId(), EquipmentStatus.LABORATORY));
                        return equipmentService.findByName(EQUIPMENT_NAME).orElseThrow();
                    });

            Supplier supplier = supplierService.findByName(SUPPLIER_NAME)
                    .orElseGet(() -> {
                        supplierService.saveSupplier(new Supplier(SUPPLIER_NAME, equipment.getId()));
                        return supplierService.findByName(SUPPLIER_NAME).orElseThrow();
                    });

            Role role = roleService.findByName(ROLE_NAME)
                    .orElseGet(() -> {
                        roleService.saveRole(new Role(ROLE_NAME));
                        return roleService.findByName(ROLE_NAME).orElseThrow();
                    });

            User user = userService.findByEmail(USER_EMAIL)
                    .orElseGet(() -> {
                        userService.saveUser(new User(
                                USER_FIRST_NAME,
                                USER_LAST_NAME,
                                USER_EMAIL,
                                USER_PASSWORD,
                                USER_USERNAME,
                                role.getId()
                        ));
                        return userService.findByEmail(USER_EMAIL).orElseThrow();
                    });

            CustomUser customUser = customUserService.getByUserId(user.getId())
                    .orElseGet(() -> {
                        CustomUser newCustomUser = new CustomUser(user.getId(), UserType.USER, 2025,department.getId());
                        customUserService.saveUser(newCustomUser);
                        return customUserService.getByUserId(user.getId())
                                .orElseThrow(() -> new IllegalArgumentException("User could not be created or found"));
                    });

            if (orderService.findByEquipment(equipment).isEmpty()) {
                orderService.saveOrder(new Order(
                        customUser.getId(),
                        equipment.getId(),
                        (int) 1000.00,
                        supplier.getId(),
                        OrderStatus.APPROVED,
                        "INV-001"
                ));
            }

            if (rentalService.findByEquipment(equipment).isEmpty()) {
                rentalService.saveRental(new Rental(
                        equipment.getId(),
                        LocalDate.now(),
                        LocalDate.now().plusDays(7)
                ));
            }

            if (serviceService.findByEquipment(equipment).isEmpty()) {
                serviceService.saveService(new Service(
                        equipment.getId(),
                        SERVICE_DESCRIPTION,
                        LocalDate.now()
                ));
            }
        };
    }
}
