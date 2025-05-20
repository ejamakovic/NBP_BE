-- =============================================
-- IZVJEŠTAJ 1: Broj opreme po laboratoriji
-- =============================================
CREATE TABLE NBP08.EQUIPMENT_BY_LAB_REPORT (
                                               laboratory_id NUMBER,
                                               laboratory_name VARCHAR2(100),
                                               equipment_count NUMBER,
                                               report_generated_at DATE
);

CREATE OR REPLACE PROCEDURE NBP08.FILL_EQUIPMENT_BY_LAB_REPORT AS
BEGIN
    -- Brisanje postojećih podataka u izveštajnoj tabeli
    DELETE FROM NBP08.EQUIPMENT_BY_LAB_REPORT;

    -- Unos novih podataka u izveštajnu tabelu
    FOR rec IN (SELECT l.id, l.name, COUNT(e.id) AS equipment_count
                FROM NBP08.LABORATORY l
                         LEFT JOIN NBP08.EQUIPMENT e ON l.id = e.laboratory_id
                GROUP BY l.id, l.name) LOOP
            INSERT INTO NBP08.EQUIPMENT_BY_LAB_REPORT (laboratory_id, laboratory_name, equipment_count, report_generated_at)
            VALUES (rec.id, rec.name, rec.equipment_count, SYSDATE);
        END LOOP;

END;
/

BEGIN
    NBP08.FILL_EQUIPMENT_BY_LAB_REPORT;
END;
/

CREATE OR REPLACE TRIGGER TRG_REFRESH_EQUIPMENT_BY_LAB_REPORT
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.EQUIPMENT
BEGIN
    NBP08.FILL_EQUIPMENT_BY_LAB_REPORT;
END;
/

-- =============================================
-- IZVJEŠTAJ 2: Broj opreme po kategoriji
-- =============================================
CREATE TABLE NBP08.EQUIPMENT_BY_CATEGORY_REPORT (
                                                    category_id NUMBER,
                                                    category_name VARCHAR2(100),
                                                    equipment_count NUMBER,
                                                    report_generated_at DATE
);

CREATE OR REPLACE PROCEDURE NBP08.FILL_EQUIPMENT_BY_CATEGORY_REPORT AS
BEGIN
    DELETE FROM NBP08.EQUIPMENT_BY_CATEGORY_REPORT;
    FOR rec IN (SELECT c.id, c.name, COUNT(e.id) AS equipment_count
                FROM NBP08.CATEGORY c
                         LEFT JOIN NBP08.EQUIPMENT e ON c.id = e.category_id
                GROUP BY c.id, c.name) LOOP
            INSERT INTO NBP08.EQUIPMENT_BY_CATEGORY_REPORT (category_id, category_name, equipment_count, report_generated_at)
            VALUES (rec.id, rec.name, rec.equipment_count, SYSDATE);
        END LOOP;

END;
/

BEGIN
    NBP08.FILL_EQUIPMENT_BY_CATEGORY_REPORT;
END;
/

CREATE OR REPLACE TRIGGER TRG_REFRESH_EQUIPMENT_BY_CATEGORY_REPORT
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.EQUIPMENT
BEGIN
    NBP08.FILL_EQUIPMENT_BY_CATEGORY_REPORT;
END;
/

-- =============================================
-- IZVJEŠTAJ 3: Broj servisa po opremi
-- =============================================
CREATE TABLE NBP08.SERVICE_COUNT_PER_EQUIPMENT (
                                                   equipment_id NUMBER,
                                                   equipment_name VARCHAR2(100),
                                                   service_count NUMBER,
                                                   report_generated_at DATE
);

CREATE OR REPLACE PROCEDURE NBP08.FILL_SERVICE_COUNT_PER_EQUIPMENT AS
BEGIN
    DELETE FROM NBP08.SERVICE_COUNT_PER_EQUIPMENT;
    FOR rec IN (SELECT e.id, e.name, COUNT(s.id) AS service_count
                FROM NBP08.EQUIPMENT e
                         LEFT JOIN NBP08.SERVICE s ON e.id = s.equipment_id
                GROUP BY e.id, e.name) LOOP
            INSERT INTO NBP08.SERVICE_COUNT_PER_EQUIPMENT (equipment_id, equipment_name, service_count, report_generated_at)
            VALUES (rec.id, rec.name, rec.service_count, SYSDATE);
        END LOOP;

END;
/

BEGIN
    NBP08.FILL_SERVICE_COUNT_PER_EQUIPMENT;
END;
/

CREATE OR REPLACE TRIGGER TRG_REFRESH_SERVICE_COUNT_PER_EQUIPMENT
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.SERVICE
BEGIN
    NBP08.FILL_SERVICE_COUNT_PER_EQUIPMENT;
END;
/


-- =============================================
-- IZVJEŠTAJ 4: Broj narudžbi po dobavljaču
-- =============================================
CREATE TABLE NBP08.ORDERS_PER_SUPPLIER (
                                           supplier_id NUMBER,
                                           supplier_name VARCHAR2(100),
                                           order_count NUMBER,
                                           report_generated_at DATE
);

CREATE OR REPLACE PROCEDURE NBP08.FILL_ORDERS_PER_SUPPLIER AS
BEGIN
    DELETE FROM NBP08.ORDERS_PER_SUPPLIER;
    FOR rec IN (SELECT s.id, s.name, COUNT(o.id) AS order_count
                FROM NBP08.SUPPLIER s
                         LEFT JOIN NBP08.ORDERS o ON s.id = o.supplier_id
                GROUP BY s.id, s.name) LOOP
            INSERT INTO NBP08.ORDERS_PER_SUPPLIER (supplier_id, supplier_name, order_count, report_generated_at)
            VALUES (rec.id, rec.name, rec.order_count, SYSDATE);
        END LOOP;
END;
/

BEGIN
    NBP08.FILL_ORDERS_PER_SUPPLIER;
END;
/

CREATE OR REPLACE TRIGGER TRG_REFRESH_ORDERS_PER_SUPPLIER
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.ORDERS
BEGIN
    NBP08.FILL_ORDERS_PER_SUPPLIER;
END;
/

-- =============================================
-- IZVJEŠTAJ 5: Broj opreme po odjeljenju
-- =============================================
CREATE TABLE NBP08.EQUIPMENT_BY_DEPARTMENT (
                                               department_id NUMBER,
                                               department_name VARCHAR2(100),
                                               equipment_count NUMBER,
                                               report_generated_at DATE
);

CREATE OR REPLACE PROCEDURE NBP08.FILL_EQUIPMENT_BY_DEPARTMENT AS
BEGIN
    DELETE FROM NBP08.EQUIPMENT_BY_DEPARTMENT;
    FOR rec IN (SELECT d.id, d.name, COUNT(e.id) AS equipment_count
                FROM NBP08.DEPARTMENT d
                         LEFT JOIN NBP08.CUSTOM_USER cu ON d.id = cu.department_id
                         LEFT JOIN NBP08.RENTAL r ON cu.id = r.custom_user_id
                         LEFT JOIN NBP08.EQUIPMENT e ON r.equipment_id = e.id
                GROUP BY d.id, d.name) LOOP
            INSERT INTO NBP08.EQUIPMENT_BY_DEPARTMENT (department_id, department_name, equipment_count, report_generated_at)
            VALUES (rec.id, rec.name, rec.equipment_count, SYSDATE);
        END LOOP;

END;
/

BEGIN
    NBP08.FILL_EQUIPMENT_BY_DEPARTMENT;
END;
/

CREATE OR REPLACE TRIGGER TRG_REFRESH_EQUIPMENT_BY_DEPARTMENT
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.RENTAL
BEGIN
    NBP08.FILL_EQUIPMENT_BY_DEPARTMENT;
END;
/

-- SERVICE BY EQUIPMENT ID
CREATE TABLE SERVICE_BY_EQUIPMENT_SUMMARY (
    service_id NUMBER PRIMARY KEY,
    equipment_id NUMBER,
    service_date DATE,
    description VARCHAR2(4000),
    equipment_name VARCHAR2(255),
    laboratory_name VARCHAR2(255),
    category_name VARCHAR2(255),
    department_name VARCHAR2(255)
);

-- TRG - SERVICE 
CREATE OR REPLACE TRIGGER trg_service_summary
AFTER INSERT OR UPDATE OR DELETE ON service
FOR EACH ROW
DECLARE
    v_equipment_name   VARCHAR2(255);
    v_laboratory_name  VARCHAR2(255);
    v_category_name    VARCHAR2(255);
    v_department_name  VARCHAR2(255);
BEGIN
    IF INSERTING OR UPDATING THEN
        SELECT e.name, l.name, c.name, d.name
          INTO v_equipment_name, v_laboratory_name, v_category_name, v_department_name
          FROM equipment e
          JOIN laboratory l ON e.laboratory_id = l.id
          JOIN category c ON e.category_id = c.id
          JOIN department d ON l.department_id = d.id
         WHERE e.id = :NEW.equipment_id;

        MERGE INTO service_by_equipment_summary s
        USING (SELECT :NEW.id AS service_id FROM dual) src
          ON (s.service_id = src.service_id)
        WHEN MATCHED THEN
          UPDATE SET
            s.description = :NEW.description,
            s.service_date = :NEW.service_date,
            s.equipment_name = v_equipment_name,
            s.laboratory_name = v_laboratory_name,
            s.category_name = v_category_name,
            s.department_name = v_department_name
        WHEN NOT MATCHED THEN
          INSERT (service_id, equipment_id, service_date, description, equipment_name, laboratory_name, category_name, department_name)
          VALUES (:NEW.id, :NEW.equipment_id, :NEW.service_date, :NEW.description, v_equipment_name, v_laboratory_name, v_category_name, v_department_name);

    ELSIF DELETING THEN
        DELETE FROM service_by_equipment_summary WHERE service_id = :OLD.id;
    END IF;
END;
/

-- TRG - equip, lab, cat
CREATE OR REPLACE TRIGGER trg_equipment_update
AFTER UPDATE OF name, laboratory_id, category_id ON equipment
FOR EACH ROW
DECLARE
    v_equipment_name   VARCHAR2(255);
    v_laboratory_name  VARCHAR2(255);
    v_category_name    VARCHAR2(255);
    v_department_name  VARCHAR2(255);
BEGIN
    -- Fetch updated values once
    SELECT e.name,
           l.name,
           c.name,
           d.name
      INTO v_equipment_name, v_laboratory_name, v_category_name, v_department_name
      FROM equipment e
      LEFT JOIN laboratory l ON e.laboratory_id = l.id
      LEFT JOIN category c ON e.category_id = c.id
      LEFT JOIN department d ON l.department_id = d.id
     WHERE e.id = :NEW.id;

    -- Update the summary table
    UPDATE service_by_equipment_summary s
       SET equipment_name = v_equipment_name,
           laboratory_name = v_laboratory_name,
           category_name = v_category_name,
           department_name = v_department_name
     WHERE s.equipment_id = :NEW.id;
END;
/