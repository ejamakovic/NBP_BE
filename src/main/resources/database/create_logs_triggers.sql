-- Kreiranje triggera za log tabelu

CREATE OR REPLACE TRIGGER trg_log_category_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.CATEGORY
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP08.CATEGORY',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/

CREATE OR REPLACE TRIGGER trg_log_department_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.DEPARTMENT
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP08.DEPARTMENT',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/

CREATE OR REPLACE TRIGGER trg_log_equipment_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.EQUIPMENT
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP08.EQUIPMENT',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/

CREATE OR REPLACE TRIGGER TRG_LOG_FACULTY_NBP08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.RENTAL_REQUEST
    FOR EACH ROW
    WHEN (USER = 'NBP08')
DECLARE
    PRAGMA AUTONOMOUS_TRANSACTION;
    v_action_name VARCHAR2(10);
BEGIN
    IF INSERTING THEN
        v_action_name := 'INSERT';
    ELSIF UPDATING THEN
        v_action_name := 'UPDATE';
    ELSIF DELETING THEN
        v_action_name := 'DELETE';
    END IF;

    INSERT INTO NBP.NBP_LOG (
        ACTION_NAME,
        TABLE_NAME,
        DATE_TIME,
        DB_USER
    )
    VALUES (
               v_action_name,
               'NBP08.RENTAL_REQUEST',
               SYSTIMESTAMP,
               USER
           );

    COMMIT;
END;
/

CREATE OR REPLACE TRIGGER trg_log_laboratory_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.LABORATORY
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP08.LABORATORY',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/

CREATE OR REPLACE TRIGGER trg_log_order_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.ORDERS
    FOR EACH ROW
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP08.ORDERS',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/

CREATE OR REPLACE TRIGGER trg_log_rental_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.RENTAL
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP08.RENTAL',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/

CREATE OR REPLACE TRIGGER trg_log_service_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.SERVICE
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP08.SERVICE',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/

CREATE OR REPLACE TRIGGER trg_log_supplier_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.SUPPLIER
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP08.SUPPLIER',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/

CREATE OR REPLACE TRIGGER trg_log_customUser_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.CUSTOM_USER
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP08.CUSTOM_USER',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/

CREATE OR REPLACE TRIGGER trg_log_user_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP.NBP_USER
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP.NBP_USER',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/

CREATE OR REPLACE TRIGGER trg_log_role_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP.NBP_ROLE
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP.NBP_ROLE',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/

CREATE OR REPLACE TRIGGER trg_log_apps_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP.NBP_APPS
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    DECLARE
        v_action_name VARCHAR2(10);
    BEGIN
        IF INSERTING THEN
            v_action_name := 'INSERT';
        ELSIF UPDATING THEN
            v_action_name := 'UPDATE';
        ELSIF DELETING THEN
            v_action_name := 'DELETE';
        END IF;

        INSERT INTO NBP.NBP_LOG (
            ACTION_NAME,
            TABLE_NAME,
            DATE_TIME,
            DB_USER
        )
        VALUES (
                   v_action_name,
                   'NBP.NBP_APPS',
                   SYSTIMESTAMP,
                   USER
               );
    END;
END;
/




