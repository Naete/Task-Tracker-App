package com.example.todo_listen_app;

import java.util.Comparator;

public class TaskDateComparator implements Comparator<Task_Item>
{
    @Override
    public int compare(Task_Item o1, Task_Item o2)
    {
        int ret = o1.getTaskDate().compareTo(o2.getTaskDate());

        return ret;
    }
}
