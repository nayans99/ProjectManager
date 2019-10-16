package com.example.android.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class employeeTask extends AppCompatActivity {
    RecyclerView recyclerView;
    String title;
    CardView cv;
    Button bvp;
    int donetasks;
    public ArrayList<projectTitles> taskList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.task_view);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        title = intent.getExtras().getString("title");
        getSupportActionBar().setTitle(title);
        cv = findViewById(R.id.cv);
        bvp=(Button)findViewById(R.id.buttonvp);
        recyclerView = findViewById(R.id.rveod);
        taskList = new ArrayList<>();
        donetasks=0;
        final String emailuser = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        Log.d("emailele", "onCreate: "+emailuser);
        FirebaseFirestore.getInstance().collection("Project").document(title).collection("tasks").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (final QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                taskm t = documentSnapshot.toObject(taskm.class);
                               Log.d("emailll", "onSuccess: "+emailuser+t.getEmployee());

                                    taskList.add(new projectTitles(t.getTitlet(),t.getDescription(),t.getStatus(),title,t.getEmployee()));
                                        if(t.getStatus().equalsIgnoreCase("complete"))
                                        {
                                            donetasks++;
                                        }
                            }

                           LinearLayoutManager layoutManager = new LinearLayoutManager(employeeTask.this);
                           final RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
                           recyclerView.setLayoutManager(rvLiLayoutManager);
                           eod_details dom = new eod_details(employeeTask.this, taskList);
                           recyclerView.setAdapter(dom);

                       }

                   });
        bvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(employeeTask.this,ViewPieChart.class);
                i.putExtra("donetaskno",donetasks);
                startActivity(i);
            }
        });

                }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_project, menu);
        return super.onCreateOptionsMenu(menu);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_add_post: {
                finish();
                Intent intent = new Intent(this, addtaskActivity.class);
                intent.putExtra("title",title);
                startActivity(intent);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
            }



