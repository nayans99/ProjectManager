package com.example.android.project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private Button buttonregister;
    private EditText email,password,role;
    private ProgressDialog PD;
    private FirebaseAuth FA;
    private FirebaseFirestore FS;
    private DocumentReference noteref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        PD=new ProgressDialog(this);
        FA= FirebaseAuth.getInstance();
        FS= FirebaseFirestore.getInstance();
        getSupportActionBar().setTitle("Home");
        buttonregister=(Button) findViewById(R.id.buttonregister);
        email=(EditText)findViewById(R.id.loginemail);
        password=(EditText)findViewById(R.id.loginpassword);
        role=(EditText)findViewById(R.id.role);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                storeData();
            }
        });

    }

    public void storeData()
    {

        String username=email.getText().toString().trim();
        String pass=password.getText().toString().trim();
        String task=role.getText().toString().trim();

        if(TextUtils.isEmpty(username))
        {
            Toast.makeText(this,"Please ENter Email Address",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(this,"Please Enter Password",Toast.LENGTH_SHORT).show();
            return;
        }
        PD.setMessage("Registering user......");
        PD.show();
        FA.createUserWithEmailAndPassword(username,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(RegisterActivity.this, "Registered SUccessfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,login.class);
                    finish();
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Could not register please try again", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this,RegisterActivity.class);
                }
            }

        });
        Map<String ,Object> usermap=new HashMap<>();
        usermap.put("UserName",username);
        usermap.put("Role",task);
       FS.collection("USERS").document(username).set(usermap);
    }

}