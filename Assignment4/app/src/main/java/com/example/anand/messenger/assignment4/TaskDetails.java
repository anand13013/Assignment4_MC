package com.example.anand.messenger.assignment4;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import static com.example.anand.messenger.assignment4.R.id.Edittext_Task_Details;

public class TaskDetails extends AppCompatActivity {

    public static final String EXTRA_TASK_TITLE = "title";
    public static final String EXTRA_TASK_DETAILS = "details";

    private EditText Task_title, Task_Details;
    public TextView task_status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_description);
        Task_title = (EditText)findViewById(R.id.descriptionText);
        Task_Details = (EditText)findViewById(R.id.Edittext_Task_Details);
        task_status = (TextView)findViewById(R.id.Id_status);

    }

    public void doneClicked(View view) {
        String tasktitle = Task_title.getText().toString();
        String taskdetails = Task_Details.getText().toString();
        if (!tasktitle.isEmpty()&&!taskdetails.isEmpty()) {
            Intent result = new Intent();
            result.putExtra(EXTRA_TASK_TITLE, tasktitle);
            result.putExtra(EXTRA_TASK_DETAILS, taskdetails);
            setResult(RESULT_OK, result);
        } else {
            setResult(RESULT_CANCELED);
        }
        finish();
    }
    public void setDoneStatus()
    {
        task_status.setText("Status : Complete");
    }
    public void setUndoneStatus()
    {
        task_status.setText("Status : Complete");
    }
}
