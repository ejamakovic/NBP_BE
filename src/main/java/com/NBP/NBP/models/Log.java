package com.NBP.NBP.models;

import java.sql.Timestamp;

public class Log {
    private int id;
    private String actionName;
    private String tableName;
    private Timestamp dateTime;
    private String dbUser;

    public Log(int id, String actionName, String tableName, Timestamp dateTime, String dbUser) {
        this.id = id;
        this.actionName = actionName;
        this.tableName = tableName;
        this.dateTime = dateTime;
        this.dbUser = dbUser;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }
}
