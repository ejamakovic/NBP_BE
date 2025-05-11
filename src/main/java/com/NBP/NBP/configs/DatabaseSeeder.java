package com.NBP.NBP.configs;

import com.NBP.NBP.models.*;
import com.NBP.NBP.models.enums.EquipmentStatus;
import com.NBP.NBP.models.enums.OrderStatus;
import com.NBP.NBP.models.enums.UserType;
import com.NBP.NBP.services.*;
import com.opencsv.CSVReader;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@Configuration
public class DatabaseSeeder {

    //@Bean
    CommandLineRunner seedDatabase(
            CategoryService categoryService,
            DepartmentService departmentService,
            EquipmentService equipmentService,
            LaboratoryService laboratoryService,
            OrderService orderService,
            RentalService rentalService,
            ServiceService serviceService,
            SupplierService supplierService,
            UserService userService,
            RoleService roleService,
            CustomUserService customUserService,
            RentalRequestService rentalRequestService
    ) {
        return args -> {

            // Seed departments
            seedFromCSV("seedData/departments.csv", line -> {
                try {
                    String departmentName = line[0];
                    departmentService.saveDepartment(new Department(departmentName));
                } catch (Exception e) {
                    System.err.println("Skipping department due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Departments have been added.");

            // Seed categories
            seedFromCSV("seedData/categories.csv", line -> {
                try {
                    categoryService.saveCategory(new Category(line[0], line[1]));
                } catch (Exception e) {
                    System.err.println("Skipping category due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Categories have been added.");

            // Seed laboratories
            seedFromCSV("seedData/labs.csv", line -> {
                try {
                    String labName = line[0];
                    String departmentName = line[1];
                    Optional<Department> department = departmentService.findByName(departmentName);
                    if (department.isPresent()) {
                        laboratoryService.saveLaboratory(new Laboratory(labName, department.get().getId()));
                    } else {
                        System.err.println("Department not found for name: " + departmentName);
                    }
                } catch (Exception e) {
                    System.err.println("Skipping lab due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Laboratories have been added.");

            // Seed equipment
            seedFromCSV("seedData/equipment.csv", line -> {
                try {
                    String equipmentName = line[0];
                    String categoryName = line[1];
                    String labName = line[2];
                    EquipmentStatus status = EquipmentStatus.valueOf(line[3]);

                    Optional<Category> category = categoryService.findByName(categoryName);
                    if (!category.isPresent()) {
                        System.err.println("Category not found: " + categoryName);
                        return;
                    }

                    Optional<Laboratory> laboratory = laboratoryService.findByName(labName);
                    if (!laboratory.isPresent()) {
                        System.err.println("Laboratory not found: " + labName);
                        return;
                    }

                    equipmentService.saveEquipment(new Equipment(equipmentName, category.get().getId(), laboratory.get().getId(), status));
                } catch (Exception e) {
                    System.err.println("Skipping equipment due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Equipment has been added.");

            // Seed suppliers
            seedFromCSV("seedData/suppliers.csv", line -> {
                try {
                    String supplierName = line[0];
                    String equipmentName = line[1];
                    Optional<Equipment> equipment = equipmentService.findByName(equipmentName);
                    if (!equipment.isPresent()) {
                        System.err.println("Equipment not found: " + equipmentName);
                        return;
                    }
                    supplierService.saveSupplier(new Supplier(supplierName, equipment.get().getId()));
                } catch (Exception e) {
                    System.err.println("Skipping supplier due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Suppliers have been added.");

            // Seed roles
            seedFromCSV("seedData/roles.csv", line -> {
                try {
                    roleService.saveRole(new Role(line[0]));
                } catch (Exception e) {
                    System.err.println("Skipping role due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Roles have been added.");

            // Seed users
            seedFromCSV("seedData/users.csv", line -> {
                try {
                    int roleId = Integer.parseInt(line[5]);
                    Optional<Role> role = Optional.ofNullable(roleService.getRoleById(roleId));

                    if (role.isPresent()) {
                        Role userRole = role.get();
                        User user = new User(line[0], line[1], line[2], line[3], line[4], userRole);
                        userService.saveUser(user);
                    } else {
                        System.err.println("Role not found: " + role);
                    }
                } catch (Exception e) {
                    System.err.println("Skipping user due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Users have been added.");

            // Seed custom users
            seedFromCSV("seedData/custom_users.csv", line -> {
                try {
                    String username = line[0];
                    User user = userService.findByUsername(username)
                            .orElseThrow(() -> new RuntimeException("User with username '" + username + "' not found."));

                    String departmentName = line[3];
                    Department department = departmentService.findByName(departmentName)
                            .orElseThrow(() -> new RuntimeException("Department with name '" + departmentName + "' not found."));

                    customUserService.saveUser(new CustomUser(
                            user.getId(),
                            UserType.valueOf(line[1]),
                            Integer.parseInt(line[2]),
                            department.getId()
                    ));
                } catch (Exception e) {
                    System.err.println("Skipping custom user due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Custom users have been added.");

            // Seed orders
            seedFromCSV("seedData/orders.csv", line -> {
                try {
                    String username = line[0];

                    Optional<User> user = userService.findByUsername(username);
                    if (!user.isPresent()) {
                        System.err.println("User not found: " + username);
                        return;
                    }

                    Optional<CustomUser> customUser = customUserService.getByUserId(user.get().getId());
                    if (!customUser.isPresent()) {
                        System.err.println("CustomUser not found for username: " + username);
                        return;
                    }

                    String equipmentName = line[1];
                    int price = Integer.parseInt(line[2]);
                    String supplierName = line[3];
                    OrderStatus status = OrderStatus.valueOf(line[4]);
                    String invoiceNumber = line[5];

                    Optional<Equipment> equipment = equipmentService.findByName(equipmentName);
                    if (!equipment.isPresent()) {
                        System.err.println("Equipment not found: " + equipmentName);
                        return;
                    }

                    Optional<Supplier> supplier = supplierService.findByName(supplierName);
                    if (!supplier.isPresent()) {
                        System.err.println("Supplier not found: " + supplierName);
                        return;
                    }

                    Order order = new Order(
                            customUser.get().getId(),
                            equipment.get().getId(),
                            price,
                            supplier.get().getId(),
                            status,
                            invoiceNumber
                    );
                    orderService.saveOrder(order);
                } catch (Exception e) {
                    System.err.println("Skipping order due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Orders have been added.");

            // Seed rentals
            seedFromCSV("seedData/rentals.csv", line -> {
                try {
                    String equipmentName = line[0];

                    Optional<Equipment> equipment = equipmentService.findByName(equipmentName);
                    if (!equipment.isPresent()) {
                        System.err.println("Equipment not found: " + equipmentName);
                        return;
                    }
                    rentalService.saveRental(new Rental(equipment.get().getId(), LocalDate.parse(line[1]), LocalDate.parse(line[2])));
                } catch (Exception e) {
                    System.err.println("Skipping rental due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Rentals have been added.");

            // Seed services
            seedFromCSV("seedData/services.csv", line -> {
                try {
                    String equipmentName = line[0];
                    Optional<Equipment> equipment = equipmentService.findByName(equipmentName);
                    if (!equipment.isPresent()) {
                        System.err.println("Equipment not found: " + equipmentName);
                        return;
                    }
                    serviceService.saveService(new Service(equipment.get().getId(), line[1], LocalDate.parse(line[2])));
                } catch (Exception e) {
                    System.err.println("Skipping service due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Services have been added.");

            // Seed rental requests
            seedFromCSV("seedData/request.csv", line -> {
                try {
                    String equipmentName = line[0];
                    String username = line[1];
                    Date requestDate = Date.valueOf(line[2]);
                    String status = line[3];

                    Optional<Equipment> equipment = equipmentService.findByName(equipmentName);
                    if (!equipment.isPresent()) {
                        System.err.println("Equipment not found: " + equipmentName);
                        return;
                    }

                    Optional<User> user = userService.findByUsername(username);
                    if (!user.isPresent()) {
                        System.err.println("User not found: " + username);
                        return;
                    }

                    Optional<CustomUser> customUser = customUserService.getByUserId(user.get().getId());
                    if (!customUser.isPresent()) {
                        System.err.println("CustomUser not found for username: " + username);
                        return;
                    }

                    RentalRequest request = new RentalRequest(
                            equipment.get().getId(),
                            customUser.get().getId(),
                            requestDate.toLocalDate(),
                            status
                    );

                    rentalRequestService.save(request);
                } catch (Exception e) {
                    System.err.println("Skipping rental request due to error: " + Arrays.toString(line));
                    System.err.println("Reason: " + e.getMessage());
                }
            });
            System.out.println("Rental requests have been added.");

        };
    }

    private void seedFromCSV(String filePath, CSVConsumer consumer) {
        try {
            Resource resource = new ClassPathResource(filePath);
            try (CSVReader csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()))) {
                String[] line;
                csvReader.skip(1);

                while ((line = csvReader.readNext()) != null) {
                    if (line.length == 0 || line[0].trim().isEmpty()) {
                        continue;
                    }
                    consumer.accept(line);
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to seed from " + filePath + ": " + e.getMessage());
        }
    }

    @FunctionalInterface
    interface CSVConsumer {
        void accept(String[] line) throws Exception;
    }
}
