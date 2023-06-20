package com.example.sqllitetest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private RecyclerView recyclerView;
    private TaskAdapter taskAdapter;
    private TaskDatabase taskDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        recyclerView = findViewById(R.id.recyclerView);

        taskDatabase = Room.databaseBuilder(getApplicationContext(), TaskDatabase.class, "task-db")
                .allowMainThreadQueries()
                .build();

        List<Task> taskList = taskDatabase.taskDao().getAllTasks();

        taskAdapter = new TaskAdapter(taskList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(taskAdapter);

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String taskTitle = editTextTask.getText().toString().trim();

                if (!taskTitle.isEmpty()) {
                    Task newTask = new Task( taskTitle, "");
                    taskDatabase.taskDao().insert(newTask);

                    taskAdapter.addTask(newTask);
                    editTextTask.setText("");
                }
            }
        });
    }
}