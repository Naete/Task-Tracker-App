package com.example.todo_listen_app;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    RecyclerView recyclerView;
    RecyclerView.Adapter rv_Adapter;
    RecyclerView.LayoutManager rv_layoutManager;

    static ArrayList<Task_Item> tasks = new ArrayList<>();

    static TextView numberOfTask_View;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Recyclerview in which your tasks are transfered
        recyclerView = findViewById(R.id.recyclerView);
        rv_layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(rv_layoutManager);
        rv_Adapter = new My_RvAdapter();
        recyclerView.setAdapter(rv_Adapter);

        // View to show on the current number of tasks
        numberOfTask_View = findViewById(R.id.taskCountView);

        // Updates all displayed views content
        UpdateViews();

        // View to show on the current date
        TextView dateView = findViewById(R.id.dateView);
        SetCurrentDate(dateView);

        // Add button view to start new task activity
        FloatingActionButton addBtn = findViewById(R.id.addTaskBtn);
        addBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                StartNewTask(MainActivity.this, NewTask.class);
            }
        });
    }

    /**
     * This method switches from your current activity to your targeted activity.
     * @param fromActivity Your current activity you are in.
     * @param toActivity Activity to go to.
     */
    void StartNewTask(Activity fromActivity, Class toActivity)
    {
        startActivity(new Intent(fromActivity, toActivity));
    }

    /**
     * This method displays your systems current date on a given view.
     * @param dateView Your view you want to display on your current date.
     */
    void SetCurrentDate(TextView dateView)
    {
        DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.getDefault());
        dateView.setText(df.format(new Date()));
    }

    /**
     * This method updates the content of views that are displayed..
     */
    public static void UpdateViews()
    {
        tasks = DataStorage.GetTaskList();
        numberOfTask_View.setText("" + DataStorage.GetNumberOfTasks());
    }
}

