-- Kreiranje triggera za log tabelu

CREATE OR REPLACE TRIGGER trg_log_category_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.CATEGORY
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    INSERT INTO NBP.NBP_LOG (
        ACTION_NAME,
        TABLE_NAME,
        DATE_TIME,
        DB_USER
    )
    VALUES (
               CASE
                   WHEN INSERTING THEN 'INSERT'
                   WHEN UPDATING THEN 'UPDATE'
                   WHEN DELETING THEN 'DELETE'
                   END,
               'NBP08.CATEGORY',
               SYSTIMESTAMP,
               USER
           );
END;
/


CREATE OR REPLACE TRIGGER trg_log_department_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.DEPARTMENT
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    INSERT INTO NBP.NBP_LOG (
        ACTION_NAME,
        TABLE_NAME,
        DATE_TIME,
        DB_USER
    )
    VALUES (
               CASE
                   WHEN INSERTING THEN 'INSERT'
                   WHEN UPDATING THEN 'UPDATE'
                   WHEN DELETING THEN 'DELETE'
                   END,
               'NBP08.DEPARTMENT',
               SYSTIMESTAMP,
               USER
           );
END;
/


CREATE OR REPLACE TRIGGER trg_log_equipment_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.EQUIPMENT
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    INSERT INTO NBP.NBP_LOG (
        ACTION_NAME,
        TABLE_NAME,
        DATE_TIME,
        DB_USER
    )
    VALUES (
               CASE
                   WHEN INSERTING THEN 'INSERT'
                   WHEN UPDATING THEN 'UPDATE'
                   WHEN DELETING THEN 'DELETE'
                   END,
               'NBP08.EQUIPMENT',
               SYSTIMESTAMP,
               USER
           );
END;
/

CREATE OR REPLACE TRIGGER TRG_LOG_FACULTY_NBP08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.FACULTY
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
               'NBP08.FACULTY',
               SYSTIMESTAMP,
               USER
           );

    COMMIT;
END;
/




SELECT * FROM USER_ERRORS
WHERE NAME = 'TRG_LOG_FACULTY_NBP08' AND TYPE = 'TRIGGER';

DELETE FROM NBP08.FACULTY WHERE NAME = 'ETF';


select * from nbp.nbp_log;
INSERT INTO NBP.NBP_LOG (ACTION_NAME, TABLE_NAME, DATE_TIME, DB_USER)
VALUES ('TEST', 'NBP08.FACULTY', SYSTIMESTAMP, 'NBP08');


DROP TRIGGER NBP08.TRG_LOG_FACULTY_NBP08;



CREATE OR REPLACE TRIGGER trg_log_laboratory_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.LABORATORY
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    INSERT INTO NBP.NBP_LOG (
        ACTION_NAME,
        TABLE_NAME,
        DATE_TIME,
        DB_USER
    )
    VALUES (
               CASE
                   WHEN INSERTING THEN 'INSERT'
                   WHEN UPDATING THEN 'UPDATE'
                   WHEN DELETING THEN 'DELETE'
                   END,
               'NBP08.LABORATORY',
               SYSTIMESTAMP,
               USER
           );
END;
/


CREATE OR REPLACE TRIGGER trg_log_order_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.ORDERS
    FOR EACH ROW
BEGIN
    INSERT INTO NBP.NBP_LOG (
        ACTION_NAME,
        TABLE_NAME,
        DATE_TIME,
        DB_USER
    )
    VALUES (
               CASE
                   WHEN INSERTING THEN 'INSERT'
                   WHEN UPDATING THEN 'UPDATE'
                   WHEN DELETING THEN 'DELETE'
                   END,
               'NBP08.ORDERS',
               SYSTIMESTAMP,
               USER
           );
END;
/


CREATE OR REPLACE TRIGGER trg_log_rental_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.RENTAL
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    INSERT INTO NBP.NBP_LOG (
        ACTION_NAME,
        TABLE_NAME,
        DATE_TIME,
        DB_USER
    )
    VALUES (
               CASE
                   WHEN INSERTING THEN 'INSERT'
                   WHEN UPDATING THEN 'UPDATE'
                   WHEN DELETING THEN 'DELETE'
                   END,
               'NBP08.RENTAL',
               SYSTIMESTAMP,
               USER
           );
END;
/


CREATE OR REPLACE TRIGGER trg_log_service_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.SERVICE
    FOR EACH ROW
    WHEN (USER = 'NBP08')
BEGIN
    INSERT INTO NBP.NBP_LOG (
        ACTION_NAME,
        TABLE_NAME,
        DATE_TIME,
        DB_USER
    )
    VALUES (
               CASE
                   WHEN INSERTING THEN 'INSERT'
                   WHEN UPDATING THEN 'UPDATE'
                   WHEN DELETING THEN 'DELETE'
                   END,
               'NBP08.SERVICE',
               SYSTIMESTAMP,
               USER
           );
END;
/

CREATE OR REPLACE TRIGGER trg_log_supplier_nbp08
    AFTER INSERT OR UPDATE OR DELETE ON NBP08.SUPPLIER
    FOR EACH ROW
    WHEN (USER ='NBP08')
BEGIN
    INSERT INTO NBP.NBP_LOG (
        ACTION_NAME,
        TABLE_NAME,
        DATE_TIME,
        DB_USER
    )
    VALUES (
               CASE
                   WHEN INSERTING THEN 'INSERT'
                   WHEN UPDATING THEN 'UPDATE'
                   WHEN DELETING THEN 'DELETE'
                   END,
               'NBP08.SUPPLIER',
               SYSTIMESTAMP,
               USER
           );
END;
/

