package com.example.android.project;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class employeeTask extends AppCompatActivity {
    RecyclerView recyclerView;
    String title;
    private noteadapter adapter;
    CardView cv;
    Button bvp;
    int donetasks;
    int num,den;
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

                            }

                           LinearLayoutManager layoutManager = new LinearLayoutManager(employeeTask.this);
                           final RecyclerView.LayoutManager rvLiLayoutManager = layoutManager;
                           recyclerView.setLayoutManager(rvLiLayoutManager);
                           final eod_details dom = new eod_details(employeeTask.this, taskList);
                           recyclerView.setAdapter(dom);


                new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                        new AlertDialog.Builder(employeeTask.this)
                                .setTitle("Delete Task")
                                .setMessage("Do ypu want to delete Task")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        dom.deleteitem(viewHolder.getAdapterPosition());
                                        Intent intent1 = new Intent(employeeTask.this,employeeTask.class);
                                        startActivity(intent1);
                                    }})
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Intent intent1 = new Intent(employeeTask.this,employeeTask.class);
                                        startActivity(intent1);
                                    }
                                }).show();

                    }
                }).attachToRecyclerView(recyclerView);
            }
        });

        FirebaseFirestore.getInstance().collection("Project").document(title).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Log.d("nayanna", "onCreate: ");

                    if(document.exists()){
                        num = document.getLong("taskg").intValue();
                        den = document.getLong("task").intValue();
                    }
                }
            }
        });

        bvp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(employeeTask.this,ViewPieChart.class);
                i.putExtra("taskg",num);
                i.putExtra("task",den);
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



