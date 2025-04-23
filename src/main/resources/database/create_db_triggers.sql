
-- Triggeri za ažuriranje ID vrednosti u povezanim tabelama

CREATE OR REPLACE TRIGGER update_category_id
    AFTER UPDATE OF id ON NBP08.CATEGORY
    FOR EACH ROW
BEGIN
    UPDATE NBP08.EQUIPMENT
    SET category_id = :NEW.id
    WHERE category_id = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER update_department_id
    AFTER UPDATE OF id ON NBP08.DEPARTMENT
    FOR EACH ROW
BEGIN
    UPDATE NBP08.CUSTOM_USER
    SET department_id = :NEW.id
    WHERE department_id = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER update_faculty_id
    AFTER UPDATE OF id ON NBP08.FACULTY
    FOR EACH ROW
BEGIN
    UPDATE NBP08.DEPARTMENT
    SET faculty_id = :NEW.id
    WHERE faculty_id = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER update_laboratory_id
    AFTER UPDATE OF id ON NBP08.LABORATORY
    FOR EACH ROW
BEGIN
    UPDATE NBP08.EQUIPMENT
    SET laboratory_id = :NEW.id
    WHERE laboratory_id = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER update_equipment_id_in_supplier
    AFTER UPDATE OF id ON NBP08.EQUIPMENT
    FOR EACH ROW
BEGIN
    UPDATE NBP08.SUPPLIER
    SET equipment_id = :NEW.id
    WHERE equipment_id = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER update_equipment_id_in_orders
    AFTER UPDATE OF id ON NBP08.EQUIPMENT
    FOR EACH ROW
BEGIN
    UPDATE NBP08.ORDERS
    SET equipment_id = :NEW.id
    WHERE equipment_id = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER update_equipment_id_in_rental
    AFTER UPDATE OF id ON NBP08.EQUIPMENT
    FOR EACH ROW
BEGIN
    UPDATE NBP08.RENTAL
    SET equipment_id = :NEW.id
    WHERE equipment_id = :OLD.id;
END;
/

CREATE OR REPLACE TRIGGER update_equipment_id_in_service
    AFTER UPDATE OF id ON NBP08.EQUIPMENT
    FOR EACH ROW
BEGIN
    UPDATE NBP08.SERVICE
    SET equipment_id = :NEW.id
    WHERE equipment_id = :OLD.id;
END;
/

