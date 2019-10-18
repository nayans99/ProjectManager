package com.example.android.project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addtaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Intent intent = getIntent();
    EditText ename;
    EditText tname,tdesc;
    String Date;
    Button sub;
    Bundle extras;
    String news;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_addtask);
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        news = i.getStringExtra("title");
        ename = (EditText) findViewById(R.id.assignedEmployee);
        tname = (EditText) findViewById(R.id.tasktitle);
        sub = (Button) findViewById(R.id.submit);
        tdesc = findViewById(R.id.tdesc);

        Button post_date = findViewById(R.id.DatePicker2);
        post_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.example.android.project.DatePicker();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
        if (ename != null && tname != null) {
            sub.setEnabled(true);
            onSubClicked();
        }
    }

    private void onSubClicked() {
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("Titlet", tname.getText().toString().trim());
                map.put("Employee", ename.getText().toString().trim());
                map.put("Deadline", Date);
                map.put("Status","Incomplete");
                map.put("Description",tdesc.getText().toString().trim());


                final Map<String, Object> promap = new HashMap<>();
                promap.put("Status","Ongoing");

                final Map<String,Object> emp = new HashMap<>();
                emp.put("taskname",tname.getText().toString());
                emp.put("projectname",news);
                emp.put("taskdesc",tdesc.getText().toString().trim());
                emp.put("status","Incomplete");

                FirebaseFirestore.getInstance().collection("Project")
                        .document(news).collection("tasks")
                        .document(tname.getText().toString()).set(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(addtaskActivity.this, "Assigned", Toast.LENGTH_LONG).show();
                        FirebaseFirestore.getInstance().collection("Project").document(news).update(promap);
                        FirebaseFirestore.getInstance().collection("USERS").document(ename.getText().toString()).collection("tasks").
                        document(tname.getText().toString()).set(emp);
                        FirebaseFirestore.getInstance().collection("Project").document(news)
                                .update("task", FieldValue.increment(1));
                        Intent intent = new Intent(addtaskActivity.this,Manager.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {


                        Toast.makeText(addtaskActivity.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });    }

    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
    }
}
