BEGIN
  DBMS_SCHEDULER.create_job (
    job_name        => 'backup_nbp08_midnight_job',
    job_type        => 'PLSQL_BLOCK',
    job_action      => 'BEGIN BACKUP_NBP08_TABLES_DAILY; END;',
    start_date      => TRUNC(SYSDATE + 1),
    repeat_interval => 'FREQ=DAILY;BYHOUR=0;BYMINUTE=0;BYSECOND=0',
    enabled         => TRUE,
    comments        => 'Daily backup job for NBP08 tables at midnight'
  );
END;
/