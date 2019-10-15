package com.example.android.project;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;
import java.util.Map;

public class submitActivity extends AppCompatActivity {
    Button chfile, upfile;
    TextView textView;
    Uri pdf;
    FirebaseStorage storage;
    Intent intent = getIntent();
    FirebaseFirestore firebaseFirestore;
    String news, news2, news3, news4;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);
        chfile = (Button) findViewById(R.id.bchoose);
        upfile = (Button) findViewById(R.id.bupload);
        storage = FirebaseStorage.getInstance();
        textView = (TextView) findViewById(R.id.notification);
        firebaseFirestore = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        news = i.getStringExtra("str");
        news2 = i.getStringExtra("str1");
        news3 = i.getStringExtra("str2");
        news4 = i.getStringExtra("str3");
        chfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(submitActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    selectpdf();
                } else {
                    ActivityCompat.requestPermissions(submitActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 9);
                }
            }


        });
        upfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pdf != null) {

                    uploadpdf(pdf);
                } else {
                    Toast.makeText(submitActivity.this, "error2", Toast.LENGTH_LONG).show();
                }
            }


        });
    }

    private void uploadpdf(Uri pdffile) {
        //Toast.makeText(submitActivity.this,"uyfiu",Toast.LENGTH_LONG).show();

        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        progressDialog.show();
        final String filename = System.currentTimeMillis() + "";
        final StorageReference storageReference = storage.getReference();
        final Map<String, Object> promap = new HashMap<>();
        promap.put("Status", "Done");

        storageReference.child("uploads").child(filename).putFile(pdffile).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                //Toast.makeText(submitActivity.this,"bgcjk",Toast.LENGTH_LONG).show();

                final String url = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                // final String downloadUrl = String.valueOf(task.getResult().getStorage().getDownloadUrl());
                final Map<String, Object> map = new HashMap<>();
                map.put("Title", news);
                map.put("Description", news2);
                map.put("TeamLead", news3);
                map.put("Status", "Done");
                map.put("Date", "abc");
                map.put("report", url);
                firebaseFirestore.collection("Project").document(news).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            final StorageReference finalpath = storageReference.child("uploads").child(filename);
                            finalpath.putFile(pdf).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                    final String downloadUrl = String.valueOf(task.getResult().getStorage().getDownloadUrl());
                                    map.put("Title", news);
                                    map.put("Description", news2);
                                    map.put("TeamLead", news3);
                                    map.put("Status", "Done");
                                    map.put("Date", "abc");
                                    map.put("report", downloadUrl);
                                    firebaseFirestore.collection("Project").document(news).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            finalpath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String Url = uri.toString();
                                                    map.put("Title", news);
                                                    map.put("Description", news2);
                                                    map.put("TeamLead", news3);
                                                    map.put("Status", "Done");
                                                    map.put("Date", "abc");
                                                    map.put("report", Url);
                                                    firebaseFirestore.collection("Project").document(news).set(map);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        } else {
                            Toast.makeText(submitActivity.this, "Failed to Upload File", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(submitActivity.this, "file not uploaded successfully", Toast.LENGTH_LONG).show();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                int currentProgress = (int) (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                progressDialog.setProgress(currentProgress);
            }
        });
    }

    private void selectpdf() {
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectpdf();
        } else {
            Toast.makeText(submitActivity.this, "error1", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {

            pdf = data.getData();
            textView.setText("File is Selected :" + data.getData().getLastPathSegment());

        } else
            Toast.makeText(submitActivity.this, "Please select FILE", Toast.LENGTH_SHORT).show();
    }
}
