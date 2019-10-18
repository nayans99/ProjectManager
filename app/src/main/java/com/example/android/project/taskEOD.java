package com.example.android.project;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class taskEOD extends AppCompatActivity {

    TextView EODtitle,author,Report;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.employee_eod);
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");

        EODtitle = findViewById(R.id.textViewTitle);
        author = findViewById(R.id.textViewAuthor);
        Report = findViewById(R.id.textViewReport);

        Log.d("nayanna", "onCreate: "+title);

        FirebaseFirestore.getInstance().collection("Report").document(title).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                    DocumentSnapshot document = task.getResult();
                    Log.d("nayanna", "onCreate: ");

                    if(document.exists()){
                        EODtitle.setText(document.getString("Title"));
                        author.setText(document.getString("Author"));
                        Report.setText(document.getString("Description"));
                    }
                }
            }
        });
    }


}
