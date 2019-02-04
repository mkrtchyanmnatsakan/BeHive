package com.bluip.test.behive.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bluip.test.behive.models.Assignee;
import com.bluip.test.behive.models.DueDate;
import com.bluip.test.behive.models.TaskModel;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    // Database Information
    private static final String DB_NAME = "Task.DB";

    // Table columns
    // database version
    private static final int DB_VERSION = 1;
    // Table Name
    private final String TABLE_TASK = "TABLE_TASK";

    private String _id = "id";
    private String _description = "description";
    private String _workspaces = "workspaces";
    private String _assigneeList = "assigneeList";
    private String _dueDate = "dueDate";
    private String _priority = "priority";
    private String _isCompleted = "isCompleted";


    // Creating table query
    private final String CREATE_TABLE = "create table " + TABLE_TASK + "("
            + _id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + _workspaces + " TEXT NOT NULL, "
            + _dueDate + " TEXT NOT NULL, "
            + _priority + " TEXT NOT NULL, "
            + _isCompleted + " INTEGER, "
            + _description + " TEXT NOT NULL, "
            + _assigneeList + " TEXT)";
    private DBUpdateListener dbUpdateListener;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        // Create tables again
        onCreate(db);
    }


    public synchronized void saveTasks(List<TaskModel> taskModels) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        for (TaskModel task : taskModels) {
            if (task.getId() != null) {
                values.put(_id, task.getId());

            }
            values.put(_workspaces, task.getWorkspaces());
            values.put(_description, task.getDescription());
            values.put(_priority, task.getPriority());
            values.put(_isCompleted, task.isCompleted() ? 1 : 0);
            values.put(_assigneeList, new Gson().toJson(task.getAssigneeList()));
            values.put(_dueDate, new Gson().toJson(task.getDueDate()));


            if (task.getId() == null || !existData(task.getId())) {
                db.insertOrThrow(TABLE_TASK, null, values);
            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        db.close(); // Closing database connection
    }


    public synchronized void saveTask(TaskModel task) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();

        if (task.getId() != null) {
            values.put(_id, task.getId());

        }
        values.put(_workspaces, task.getWorkspaces());
        values.put(_description, task.getDescription());
        values.put(_priority, task.getPriority());
        values.put(_isCompleted, task.isCompleted() ? 1 : 0);
        values.put(_assigneeList, new Gson().toJson(task.getAssigneeList()));
        values.put(_dueDate, new Gson().toJson(task.getDueDate()));


        if (task.getId() == null || !existData(task.getId())) {


            db.insertOrThrow(TABLE_TASK, null, values);
        }


        db.setTransactionSuccessful();
        db.endTransaction();
        db.close(); // Closing database connection
    }


    public synchronized boolean existData(int id) {
        boolean dataExit = false;
        Cursor cursor = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            cursor = db.rawQuery("SELECT * FROM " + TABLE_TASK + " WHERE " + id + " = ?", new String[]{id + ""});
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                dataExit = true;
            }
            dataExit = false;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cursor.close();
        }

        return dataExit;
    }


    public synchronized List<TaskModel> getAllTasks() {
        List<TaskModel> taskModels = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_TASK + " ORDER BY id DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                int id = cursor.getInt(cursor.getColumnIndex(_id));
                List<Assignee> assigneeList = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(_assigneeList)), List.class);
                // List<Assignee> assigneeList = new ArrayList<>();
                String description = cursor.getString(cursor.getColumnIndex(_description));
                String priority = cursor.getString(cursor.getColumnIndex(_priority));
                DueDate date = new Gson().fromJson(cursor.getString(cursor.getColumnIndex(_dueDate)), DueDate.class);

                // DueDate date = new DueDate("12","pm","awgust 1");
                boolean isCompleted = (cursor.getInt(cursor.getColumnIndex(_isCompleted))) == 1;
                TaskModel model = new TaskModel(id, description, _workspaces, assigneeList, date, priority, isCompleted);
                taskModels.add(model);
            } while (cursor.moveToNext());
        }
        db.setTransactionSuccessful();
        db.endTransaction();
        db.close();
        return taskModels;
    }

    public synchronized int getTasksCount() {
        String countQuery = "SELECT  * FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    public synchronized void deletAllTasks() {
        // DELETE All Query
        String deleteQUery = "DELETE FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(deleteQUery);
    }


    public synchronized void updateTask(TaskModel task) {
        SQLiteDatabase sqldb = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_workspaces, task.getWorkspaces());
        values.put(_description, task.getDescription());
        values.put(_priority, task.getPriority());
        values.put(_isCompleted, task.isCompleted() ? 1 : 0);
        values.put(_assigneeList, new Gson().toJson(task.getAssigneeList()));
        values.put(_dueDate, new Gson().toJson(task.getDueDate()));
        int result = sqldb.update(TABLE_TASK, values, "id = " + task.getId(), null);
        sqldb.close();
        if (result == 1 && dbUpdateListener != null) {
            dbUpdateListener.onTaskUpdate(task);
        }
    }

    public void attachDBUpdateListener(DBUpdateListener dbUpdateListener) {

        this.dbUpdateListener = dbUpdateListener;

    }

    public void detachDBUpdateListener() {
        this.dbUpdateListener = null;
    }

    public interface DBUpdateListener {
        void onTaskUpdate(TaskModel updatedTask);
    }

}