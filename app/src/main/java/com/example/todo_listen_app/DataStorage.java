package com.example.todo_listen_app;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

public class DataStorage
{
    static ArrayList<Task_Item> tasks = new ArrayList<>();

    /**
     * This methods saves a new instance of class Task_Item in an ArrayList of type Task_Item (List of tasks).
     * @param task Received instance of class Task_item that is saved in an ArrayList of type Task_Item (List of tasks).
     */
    public static void SaveTask(Task_Item task)
    {
        tasks.add(task);

        SortData();
    }

    /**
     * Removes a task from list of tasks by its received index value.
     * @param index Index value of task to remove from list of tasks.
     */
    public static void RemoveTask(int index)
    {
        tasks.remove(index);
    }

    /**
     * This methods returns an ArrayList of type Task_Item (List of tasks).
     * @return Return ArrayList of type Task_Item (List of tasks).
     */
    public static ArrayList<Task_Item> GetTaskList()
    {
        return tasks;
    }

    /**
     * This methods returns the current number of tasks that are saved.
     * @return Return number of currently saved task instances.
     */
    public static int GetNumberOfTasks()
    {
        return tasks.size();
    }

    /**
     * This method sorts an ArrayList of type Task_Item (List of tasks).
     */
    static void SortData()
    {
        try
        {
            DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());

            Collections.sort(tasks, new TaskDateComparator());

            // Print out list item after ordering.
            for(Task_Item task : tasks)
            {
                System.out.println(task.toString());
            }

        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
