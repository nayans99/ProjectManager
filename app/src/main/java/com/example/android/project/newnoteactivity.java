package com.example.android.project;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class newnoteactivity extends AppCompatActivity {
    private EditText editTextTitle;
    private EditText editTextDescription;
    private NumberPicker numberPickerpriority;
    FirebaseAuth fa;
    String date;
    String notepro,notetask;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TextView mdisplaydate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        notepro = intent.getExtras().getString("project");
        notetask = intent.getExtras().getString("task");
        setContentView(R.layout.activity_newnoteactivity);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("MAKE EOD REPORT");
        mdisplaydate=(TextView) findViewById(R.id.datepick);
        mdisplaydate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal=Calendar.getInstance();
                int year=cal.get(Calendar.YEAR);
                int month=cal.get(Calendar.MONTH);
                int day=cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog =new DatePickerDialog(
                        newnoteactivity.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        mDateSetListener,year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDescription = findViewById(R.id.edit_text_description);




        fa = FirebaseAuth.getInstance();

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                //Log.d(TAG,"OnDateSet: mm/dd/yyyy: "+ month +"/"+day+"/"+year);
                date = month + "-"+ day + "-"+ year;
                mdisplaydate.setText(date);

            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_menu_note, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note:
                savenote();
            default:
                return super.onOptionsItemSelected(item);

        }

    }
    private void savenote()
    {
        String title=editTextTitle.getText().toString();
        String description=editTextDescription.getText().toString();
        if(title.trim().isEmpty()||description.trim().isEmpty())
        {
            Toast.makeText(newnoteactivity.this,"Please enter title and description",Toast.LENGTH_SHORT).show();
            return;
        }
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        HashMap<String,String> map =  new HashMap<>();
        map.put("Date",formattedDate);
        map.put("Description",description);
        map.put("Title",title);
        map.put("Project",notetask);
        map.put("Author",FirebaseAuth.getInstance().getCurrentUser().getEmail().toString());
        CollectionReference notebookref= FirebaseFirestore.getInstance().collection("Report");
        notebookref.document(notepro).set(map);
     //   notebookref.add(new note(title,description,priority));
        Toast.makeText(newnoteactivity.this,"note added",Toast.LENGTH_SHORT).show();
       // FirebaseFirestore.getInstance().collection("Project").document(notepro).collection("tasks").document(notetask).update("EOD",title);
        finish();
    }

}
