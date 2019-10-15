package com.example.android.project;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ViewReport extends AppCompatActivity {
    TextView tv;

    FirebaseFirestore ff;
    String newString;
    PDFView pdfview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        tv = (TextView) findViewById(R.id.textviewn);
        Intent intent = getIntent();
        pdfview = (PDFView) findViewById(R.id.pdfview);
        newString = intent.getStringExtra("strurl");
        ff = FirebaseFirestore.getInstance();
        DocumentReference docRef = ff.collection("Project").document(newString);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                if (task.isSuccessful()) {

                    DocumentSnapshot document = task.getResult();
                    if (document != null) {
                        String urlvalue = document.getString("report");
                        tv.setText(urlvalue);

                        String url = tv.getText().toString();
                        //String url = "https://firebasestorage.googleapis.com/v0/b/realtime-chat-46f4c.appspot.com/o/documents%2Fbf307aa5-79ae-4532-8128-ee394537b357.pdf?alt=media&token=2d0c5329-4717-4adc-9418-6614913e5bfa";

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setDataAndType(Uri.parse(url), "application/pdf");
                        startActivity(intent);
                    } else {

                    }
                } else {
                    Toast.makeText(ViewReport.this, "exception" + task.getException(), Toast.LENGTH_LONG).show();
                }
            }
        });
        /*ff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DocumentSnapshot ds=null;
                ds.getData().toString();
                String value=dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        }); */
    }

    class Retrievepdfstream extends AsyncTask<String, Void, InputStream> {

        @Override
        protected InputStream doInBackground(String... strings) {
            Toast.makeText(ViewReport.this, "hello", Toast.LENGTH_LONG).show();
            InputStream is = null;
            try {

                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                if (urlConnection.getResponseCode() == 200) {

                    is = new BufferedInputStream(urlConnection.getInputStream());
                }
            } catch (IOException e) {
                return null;
            }
            return is;
        }

        @Override
        protected void onPostExecute(InputStream inputStream) {
            super.onPostExecute(inputStream);
            pdfview.fromStream(inputStream).load();
        }
    }

}


