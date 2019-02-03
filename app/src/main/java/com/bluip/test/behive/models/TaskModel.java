package com.bluip.test.behive.models;

import java.util.List;

public class TaskModel {


    private String description;

    private String workspaces;

    private List<Assignee> assigneeList;

    private DueDate dueDate;

    private String priority;

    private boolean isCompleted;
    private Integer id;


    public TaskModel(Integer id, String description, String workspaces, List<Assignee> assigneeList, DueDate dueDate, String priority, boolean isCompleted) {
        this.description = description;
        this.workspaces = workspaces;
        this.assigneeList = assigneeList;
        this.dueDate = dueDate;
        this.priority = priority;
        this.isCompleted = isCompleted;
        this.id = id;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWorkspaces() {
        return workspaces;
    }

    public void setWorkspaces(String workspaces) {
        this.workspaces = workspaces;
    }

    public List<Assignee> getAssigneeList() {
        return assigneeList;
    }

    public void setAssigneeList(List<Assignee> assigneeList) {
        this.assigneeList = assigneeList;
    }

    public DueDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(DueDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public Integer getId() {
        return  id;

    }
}
