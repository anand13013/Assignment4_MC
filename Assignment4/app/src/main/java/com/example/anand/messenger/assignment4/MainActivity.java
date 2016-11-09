package com.example.anand.messenger.assignment4;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
  private static final String TAG = "MainActivity";

  private ArrayList<String> mList, array_details;
  private ArrayAdapter<String> mAdapter;
  private final int ADD_TASK_REQUEST = 1;
  private final String PREFS_TASKS = "prefs_tasks";
  private final String KEY_TASKS_LIST = "list";
  private String details, task;
    private CheckBox status;
    private TextView task_status;
    int index;
    TaskDetails tdes;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final ListView listview = (ListView) findViewById(R.id.taskListview);
    mList = new ArrayList<String>();
      array_details = new ArrayList<String>();
    tdes = new TaskDetails();
      String savedList = getSharedPreferences(PREFS_TASKS, MODE_PRIVATE).getString(KEY_TASKS_LIST, null);
    if (savedList != null) {
      String[] items = savedList.split(",");
      mList = new ArrayList<String>(Arrays.asList(items));
        array_details = new ArrayList<String>(Arrays.asList(items));

    }
    mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, mList);
    listview.setAdapter(mAdapter);
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        taskSelected(i);
         index = i;
      }
    });
     /* status = (CheckBox) findViewById(R.id.checkBox);
      status.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
                tdes.setDoneStatus();
              Toast.makeText(MainActivity.this, "Task complete",
                      Toast.LENGTH_LONG).show();
          }
      });
      if(!status.isChecked())
      {
            tdes.setUndoneStatus();
      }*/


  }
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  protected void onStop() {
    super.onStop();
    StringBuilder savedList_title = new StringBuilder();
      StringBuilder savedList_details = new StringBuilder();

      for (String s : mList) {
      savedList_title.append(s);
      savedList_title.append(",");
    }
      for (String s : array_details) {
          savedList_details.append(s);
          savedList_details.append(",");
      }
    getSharedPreferences(PREFS_TASKS, MODE_PRIVATE).edit()
            .putString(KEY_TASKS_LIST, savedList_title.toString()).commit();
      getSharedPreferences(PREFS_TASKS, MODE_PRIVATE).edit()
              .putString(KEY_TASKS_LIST, savedList_details.toString()).commit();
  }
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_add_task:
        Intent intent = new Intent(MainActivity.this, TaskDetails.class);
        startActivityForResult(intent, ADD_TASK_REQUEST);

      default:
        return super.onOptionsItemSelected(item);
    }
  }

  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == ADD_TASK_REQUEST) {
      if (resultCode == RESULT_OK) {
        task = data.getStringExtra(TaskDetails.EXTRA_TASK_TITLE);
        details = data.getStringExtra(TaskDetails.EXTRA_TASK_DETAILS);
        mList.add(task);
          array_details.add(details);
        mAdapter.notifyDataSetChanged();
      }
    }
  }

  private void taskSelected(final int position) {
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
    alertDialogBuilder.setTitle(mList.get(position)+ "'s Details");
    alertDialogBuilder
            .setMessage(array_details.get(position))
            .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                mList.remove(position);
                  array_details.remove(position);
                mAdapter.notifyDataSetChanged();
              }
            })
            .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
              public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
              }
            });

    AlertDialog alertDialog = alertDialogBuilder.create();
    alertDialog.show();
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }
}
