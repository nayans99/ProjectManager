package com.example.android.project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class login extends AppCompatActivity {
    EditText email,password;
    Button login;
    private TextView signup;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setTitle("LOGIN PAGE");
        email=(EditText) findViewById(R.id.emailadmin);
        password=(EditText) findViewById(R.id.passwordadmin);
        login=(Button)findViewById(R.id.loginadmin);
        firebaseAuth = FirebaseAuth.getInstance();
        signup=(TextView)findViewById(R.id.textView2);
        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String username=email.getText().toString().trim();
                String epassword=password.getText().toString().trim();

                if(TextUtils.isEmpty(username))
                {
                    Toast.makeText(login.this,"Please Enter Email ID",Toast.LENGTH_LONG);
                    return;
                }
                if(TextUtils.isEmpty(epassword))
                {
                    Toast.makeText(login.this,"Please Enter Password",Toast.LENGTH_LONG);
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(username, epassword)
                        .addOnCompleteListener(login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseFirestore.getInstance().collection("USERS").document(firebaseAuth.getCurrentUser().getEmail())
                                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    final ModelUsers md = task.getResult().toObject(ModelUsers.class);
                                                    Log.d("desigmd", "onComplete: "+md.getRole());
                                                    String desig = md.getRole();

                                                    switch(desig){
                                                        case "Admin":
                                                            Intent intent=new Intent(login.this,Admin.class);
                                                            startActivity(intent);
                                                            break;
                                                        case "Manager":
                                                            Intent intent2 = new Intent(login.this, Manager.class);
                                                            startActivity(intent2);
                                                            break;
                                                        case "Employee":
                                                            Intent intent3=new Intent(login.this,Employee.class);
                                                            startActivity(intent3);
                                                            break;

                                                    }
                                                }
                                            });
                                } else {
                                    Toast.makeText(login.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openactivity5();
            }
        });
    }
    public void openactivity5()
    {
        Intent intent=new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }
}
