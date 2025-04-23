-- Kreiranje tabela

CREATE TABLE NBP08.FACULTY (
                               id NUMBER PRIMARY KEY,
                               name VARCHAR2(255) NOT NULL,
                               abbreviation VARCHAR2(10) NOT NULL,
                               CONSTRAINT unique_faculty_name UNIQUE (name),  -- Jedinstveno ime fakulteta
                               CONSTRAINT unique_faculty_abbreviation UNIQUE (abbreviation) -- Jedinstvena skraćenica fakulteta
);

CREATE TABLE NBP08.DEPARTMENT (
                                  id NUMBER PRIMARY KEY,
                                  name VARCHAR2(100) NOT NULL,
                                  faculty_id NUMBER NOT NULL,
                                  CONSTRAINT fk_department_faculty
                                      FOREIGN KEY (faculty_id) REFERENCES NBP08.FACULTY(id)
                                          ON DELETE CASCADE,
                                  CONSTRAINT unique_department_name UNIQUE (name) -- Jedinstveno ime odeljenja
);

CREATE TABLE NBP08.CUSTOM_USER (
                                   id NUMBER PRIMARY KEY,
                                   user_id NUMBER NOT NULL,
                                   user_type VARCHAR2(100) CHECK (user_type IN ('ADMIN', 'USER', 'GUEST')),
                                   year NUMBER NOT NULL,
                                   department_id NUMBER NOT NULL,
                                   CONSTRAINT fk_customUser_user
                                       FOREIGN KEY (user_id) REFERENCES NBP.NBP_USER(id)
                                           ON DELETE CASCADE,
                                   CONSTRAINT fk_customUser_department
                                       FOREIGN KEY (department_id) REFERENCES NBP08.DEPARTMENT(id)
                                           ON DELETE CASCADE,
                                   CONSTRAINT unique_user_id UNIQUE (user_id)
);

CREATE TABLE NBP08.LABORATORY (
                                  id NUMBER PRIMARY KEY,
                                  name VARCHAR2(100) NOT NULL,
                                  department_id NUMBER NOT NULL,
                                  CONSTRAINT fk_lab_department
                                      FOREIGN KEY (department_id) REFERENCES NBP08.DEPARTMENT(id)
                                          ON DELETE CASCADE,
                                  CONSTRAINT unique_laboratory_name UNIQUE (name) -- Jedinstveno ime laboratorije
);

CREATE TABLE NBP08.CATEGORY (
                                id NUMBER PRIMARY KEY,
                                name VARCHAR2(100) NOT NULL,
                                description VARCHAR2(255),
                                CONSTRAINT unique_category_name UNIQUE (name) -- Jedinstveno ime kategorije
);

CREATE TABLE NBP08.EQUIPMENT (
                                 id NUMBER PRIMARY KEY,
                                 name VARCHAR2(100) NOT NULL,
                                 description VARCHAR2(255),
                                 category_id NUMBER NOT NULL,
                                 laboratory_id NUMBER NOT NULL,
                                 status VARCHAR2(50) CHECK (status IN ('LABORATORY', 'STORAGE', 'RENTED', 'SERVICE')),
                                 CONSTRAINT fk_equipment_category
                                     FOREIGN KEY (category_id) REFERENCES NBP08.CATEGORY(id)
                                         ON DELETE CASCADE,
                                 CONSTRAINT fk_equipment_laboratory
                                     FOREIGN KEY (laboratory_id) REFERENCES NBP08.LABORATORY(id)
                                         ON DELETE CASCADE,
                                 CONSTRAINT unique_equipment_name UNIQUE (name) -- Jedinstveno ime opreme
);

CREATE TABLE NBP08.SUPPLIER (
                                id NUMBER PRIMARY KEY,
                                name VARCHAR2(100),
                                equipment_id NUMBER NOT NULL,
                                CONSTRAINT fk_supplier_equipment
                                    FOREIGN KEY (equipment_id) REFERENCES NBP08.EQUIPMENT(id)
                                        ON DELETE CASCADE,
                                CONSTRAINT unique_supplier_name UNIQUE (name) -- Jedinstveno ime dobavljača
);

CREATE TABLE NBP08.ORDERS (
                              id NUMBER PRIMARY KEY,
                              equipment_id NUMBER NOT NULL,
                              custom_user_id NUMBER NOT NULL,
                              price NUMBER NOT NULL,
                              supplier_id NUMBER NOT NULL,
                              order_status VARCHAR2(50) CHECK (order_status IN ('NEW', 'APPROVED', 'IN_PROGRESS', 'COMPLETED', 'DECLINED', 'REMOVED')),
                              invoice_number VARCHAR2(50),
                              CONSTRAINT fk_order_equipment
                                  FOREIGN KEY (equipment_id) REFERENCES NBP08.EQUIPMENT(id)
                                      ON DELETE CASCADE,
                              CONSTRAINT fk_order_customUser
                                  FOREIGN KEY (custom_user_id) REFERENCES NBP08.CUSTOM_USER(id)
                                      ON DELETE CASCADE,
                              CONSTRAINT fk_order_supplier
                                  FOREIGN KEY (supplier_id) REFERENCES NBP08.SUPPLIER(id)
                                      ON DELETE CASCADE,
                              CONSTRAINT unique_order_invoice UNIQUE (invoice_number) -- Jedinstveni broj fakture
);

CREATE TABLE NBP08.RENTAL (
                              id NUMBER PRIMARY KEY,
                              equipment_id NUMBER NOT NULL,
                              rent_date DATE NOT NULL,
                              return_date DATE,
                              CONSTRAINT fk_rental_equipment
                                  FOREIGN KEY (equipment_id) REFERENCES NBP08.EQUIPMENT(id)
                                      ON DELETE CASCADE,
                              CONSTRAINT unique_rental_date UNIQUE (equipment_id, rent_date) -- Jedinstveni datum iznajmljivanja po opremi
);

CREATE TABLE NBP08.SERVICE (
                               id NUMBER PRIMARY KEY,
                               equipment_id NUMBER NOT NULL,
                               service_date DATE NOT NULL,
                               description VARCHAR2(255),
                               CONSTRAINT fk_service_equipment
                                   FOREIGN KEY (equipment_id) REFERENCES NBP08.EQUIPMENT(id)
                                       ON DELETE CASCADE,
                               CONSTRAINT unique_service_date UNIQUE (equipment_id, service_date) -- Jedinstveni datum servisa po opremi
);
