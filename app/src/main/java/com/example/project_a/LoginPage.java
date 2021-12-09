package com.example.project_a;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginPage extends AppCompatActivity {
    EditText username,password;
    Button login;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);
        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        username = findViewById(R.id.signin_username);
        password = findViewById(R.id.signin_password);
        progressBar = findViewById(R.id.signin_progressBar);
        login = findViewById(R.id.signin_login);

        progressBar.setVisibility(View.GONE);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String cusername = username.getText().toString().trim();
                String cpassword = password.getText().toString().trim();

                if(cusername.isEmpty()){
                    username.setError("Please Enter Mail id");
                    username.requestFocus();
                    return;
                }
                if(cpassword.isEmpty()){
                    password.setError("Enter Password");
                    password.requestFocus();
                    return;
                }/**
                if(!Patterns.EMAIL_ADDRESS.matcher(mail).matches()){
                    username.setError("Please Valid Mail id");
                    username.requestFocus();
                    return;
                }
                */
                DatabaseReference reference = firebaseDatabase.getReference("Users");
                Query checkUser = reference.child(cusername);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String cmail = snapshot.child("mail").getValue(String.class);
                            mAuth.signInWithEmailAndPassword(cmail,cpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if(task.isSuccessful()){
                                        //redirect here
                                        Toast.makeText(LoginPage.this, "Sucesss!!!!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(LoginPage.this,UserProfile.class);
                                        String fname = snapshot.child("firstname").getValue(String.class);
                                        String sname = snapshot.child("secondname").getValue(String.class);
                                        String dob = snapshot.child("dob").getValue(String.class);
                                        intent.putExtra("fname",fname);
                                        intent.putExtra("sname",sname);
                                        intent.putExtra("mail",cmail);
                                        intent.putExtra("dob",dob);
                                        progressBar.setVisibility(View.GONE);
                                        startActivity(intent);


                                    }
                                    else{
                                        Toast.makeText(LoginPage.this, "No Exsiting User", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else{
                            Toast.makeText(LoginPage.this, "username doesn't Exist!", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }
        });


    }

    public void callHome(View view){
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}