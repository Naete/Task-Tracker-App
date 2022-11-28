package com.example.todo_listen_app;

import android.support.v7.app.AppCompatActivity;

import java.text.DateFormat;
import java.util.Collections;
import java.util.Locale;

public class Task_Item implements java.lang.Comparable<Task_Item>
{
    String taskDate;
    String taskName;
    String taskDesc;
    String taskTime;

    public Task_Item (String date, String name, String description, String time)
    {
        taskDate = date;
        taskName = name;
        taskDesc = description;
        taskTime = time;
    }

    /**
     * Returns date of a task.
     * @return Return task date.
     */
    public String getTaskDate() {return taskDate;}

    /**
     * Return task name of a task.
     * @return Return task name.
     */
    public String getTaskName() {return taskName;}

    /**
     * Returns description of a task.
     * @return Return description.
     */
    public String getTaskDescription() {return taskDesc;}

    /**
     * Returns time of day of a task.
     * @return Return time of day.
     */
    public String getTaskTime() {return taskTime;}

    // Implementation of Comparable, doesn't have any use in this project
    @Override
    public int compareTo(Task_Item o)
    {
        return 0;
    }

}
