package com.example.android.project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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
    public ArrayList<projectTitles> taskList;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.task_view);
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        title = intent.getExtras().getString("title");
        getSupportActionBar().setTitle("Admin eod");
        cv = findViewById(R.id.cv);
        recyclerView = findViewById(R.id.rveod);
        taskList = new ArrayList<>();
        final String emailuser = FirebaseAuth.getInstance().getCurrentUser().getEmail().toString();
        Log.d("emailele", "onCreate: "+emailuser);
        FirebaseFirestore.getInstance().collection("Project").document(title).collection("tasks").get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                for (final QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {

                                taskm t = documentSnapshot.toObject(taskm.class);
                               Log.d("emailll", "onSuccess: "+emailuser+t.getEmployee());

                                    taskList.add(new projectTitles(t.getTitlet(),t.getDescription(),t.getStatus(),title));

                            }
                           LinearLayoutManager layoutManager = new LinearLayoutManager(employeeTask.this);
                           final RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
                           recyclerView.setLayoutManager(rvLiLayoutManager);
                           eod_details dom = new eod_details(employeeTask.this, taskList);
                           recyclerView.setAdapter(dom);

                       }

                   });
                }
            }



