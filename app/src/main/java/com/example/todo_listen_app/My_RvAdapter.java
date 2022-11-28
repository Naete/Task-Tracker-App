package com.example.todo_listen_app;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class My_RvAdapter extends RecyclerView.Adapter<My_RvAdapter.My_ViewHolder>
{

    public class My_ViewHolder extends RecyclerView.ViewHolder
    {
        TextView taskName_View;
        TextView taskDesc_View;
        TextView time_View;
        TextView date_View;
        ImageButton imgBtn_View;

        public My_ViewHolder(@NonNull View itemView)
        {
            super(itemView);

            taskName_View = itemView.findViewById(R.id.taskName);
            taskDesc_View = itemView.findViewById(R.id.taskDescription);
            time_View = itemView.findViewById(R.id.taskTime);
            date_View = itemView.findViewById(R.id.taskDate);
            imgBtn_View = itemView.findViewById(R.id.deleteBtn);
        }
    }


    @NonNull
    @Override
    public My_ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.task_layout, null);

        return new My_ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull My_ViewHolder my_viewHolder,final int i)
    {
        // Set task values in task list
        my_viewHolder.taskName_View.setText(MainActivity.tasks.get(i).getTaskName());
        my_viewHolder.taskDesc_View.setText(MainActivity.tasks.get(i).getTaskDescription());
        my_viewHolder.time_View.setText(MainActivity.tasks.get(i).getTaskTime());
        my_viewHolder.date_View.setText(MainActivity.tasks.get(i).getTaskDate());

        // Delete button view to delete its task
        my_viewHolder.imgBtn_View.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                DataStorage.RemoveTask(i);
                MainActivity.UpdateViews();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return MainActivity.tasks.size();
    }
}
