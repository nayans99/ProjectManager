package com.example.android.project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class addProjectForm extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{


  //  String state = intent.getStringExtra("boolea");
    String titlev ;

    TextView submit;
    EditText name;
    EditText desc;
    EditText lead;
    String Date;
    Spinner status;
    String title,descr,leader,stat;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.add_project_form);
        super.onCreate(savedInstanceState);
       // Intent intent = getIntent();
       // titlev = intent.getStringExtra("title");
        submit = (TextView)findViewById(R.id.Submit);
        name = (EditText) findViewById(R.id.title);
        desc = (EditText) findViewById(R.id.desc);
        lead = (EditText) findViewById(R.id.lead);
        status = (Spinner) findViewById(R.id.status);
        Button post_date = findViewById(R.id.DatePicker);
        post_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new com.example.android.project.DatePicker();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });
        ArrayAdapter<String> deptAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.status_list));
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        status.setAdapter(deptAdapter);

        submit.setEnabled(true);
     /*  if(!titlev.isEmpty()){
            FirebaseFirestore.getInstance().collection("Project").document(titlev).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> tas
                k) {
                    final ProjectDetails pd = task.getResult().toObject(ProjectDetails.class);
                    title = pd.getTitle();
                    descr = pd.getDescription();
                    leader = pd.getTeamLead();
                    stat = pd.getStatus();
                }
            });
            name.setText(title);
            desc.setText(descr);
            lead.setText(leader);
        }*/

        if (name != null && desc != null && lead != null && status!=null) {
            submit.setEnabled(true);
            onNextClicked();
        }

    }


    private void onNextClicked() {

        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Map<String, Object> map = new HashMap<>();
                map.put("Title", name.getText().toString().trim());
                map.put("Description", desc.getText().toString().trim());
                map.put("TeamLead", lead.getText().toString().trim());
                map.put("Status", status.getSelectedItem().toString().trim());
                map.put("Date",Date);
                map.put("taskg",0);
                map.put("task",0);


                FirebaseFirestore db = FirebaseFirestore.getInstance();
                // add entry to firestore
                FirebaseFirestore.getInstance().collection("Project").document(name.getText().toString()).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(addProjectForm.this, "Project added",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(addProjectForm.this, Admin.class);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(addProjectForm.this, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

        });

    }
    @Override
    public void onDateSet(android.widget.DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,year);
        c.set(Calendar.MONTH,month);
        c.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        Date = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
    }
}
