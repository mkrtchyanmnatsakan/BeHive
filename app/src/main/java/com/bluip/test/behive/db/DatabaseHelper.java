package com.bluip.test.behive.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class  DatabaseHelper extends SQLiteOpenHelper {

    // Table Name
    final String TABLE_TASK = "TABLE_TASK";

    // Table columns

    String id = "id";

    String description = "description";

    String workspaces = "workspaces";

    private String assigneeList = "assigneeList";

    private String dueDate = "dueDate";

    private String priority = "priority";

    private String isCompleted = "isCompleted";

    // Database Information
    static final String DB_NAME = "Task.DB";

    // database version
    static final int DB_VERSION = 1;

    // Creating table query
    private final String CREATE_TABLE = "create table " + TABLE_TASK + "("
            + id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + workspaces + " TEXT NOT NULL, "
            + dueDate + " TEXT NOT NULL, "
            + priority + " TEXT NOT NULL, "
            + isCompleted + " INTEGER, "
            + description + " TEXT NOT NULL, "
            + assigneeList + " TEXT)";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(db);
    }
}