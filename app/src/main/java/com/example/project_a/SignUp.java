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

public class SignUp extends AppCompatActivity {
    private EditText username,firstname,secondname,password,mail,dob;
    Button signup;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        username = findViewById(R.id.signup_username);
        firstname  = findViewById(R.id.signup_firstname);
        secondname = findViewById(R.id.signup_secondname);
        password = findViewById(R.id.signup_password);
        mail  = findViewById(R.id.signup_mail);
        dob  = findViewById(R.id.signup_dob);

        progressBar = findViewById(R.id.progressBarSignup);
        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();


        signup = findViewById(R.id.signup_button);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cusername,cfirstname,csecondname,cpassword,cmail,cdob;
                cusername =username.getText().toString();
                cfirstname = firstname.getText().toString();
                csecondname = secondname.getText().toString();
                cpassword = password.getText().toString();
                cmail = mail.getText().toString();
                cdob = dob.getText().toString();

                progressBar.setVisibility(View.VISIBLE);
                if(cfirstname.isEmpty()){
                    firstname.setError("Enter First Name");
                    firstname.requestFocus();
                    return;
                }
                if(csecondname.isEmpty()){
                    secondname.setError("Enter Second Name");
                    secondname.requestFocus();
                    return;
                }
                if(cpassword.isEmpty()){
                    password.setError("Enter a Password");
                    password.requestFocus();
                    return;
                }
                if(cmail.isEmpty()){
                    mail.setError("Enter mail Id");
                    mail.requestFocus();
                    return;
                }
                if(cdob.isEmpty()){
                    dob.setError("Enter DOB");
                    dob.requestFocus();
                    return;
                }
                if(cusername.isEmpty()){
                    username.setError("Enter username");
                    username.requestFocus();
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(cmail).matches()){
                    mail.setError("Enter Valid Email");
                    mail.requestFocus();
                    return;
                }

                DatabaseReference reference = firebaseDatabase.getReference("Users");
                Query checkUser = reference.child(cusername);
                checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            Toast.makeText(SignUp.this, "Sorry! Username Already Taken", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                        else{
                            mAuth.createUserWithEmailAndPassword(cmail,cpassword)
                                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if(task.isSuccessful()){
                                                userDetails userDetails = new userDetails(username.getText().toString(),
                                                        firstname.getText().toString(),
                                                        secondname.getText().toString(),
                                                        password.getText().toString(),
                                                        mail.getText().toString(),
                                                        dob.getText().toString()
                                                );
                                                FirebaseDatabase.getInstance().getReference("Users")
                                                        .child(cusername).setValue(userDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            Toast.makeText(SignUp.this, "Registartion Successful", Toast.LENGTH_LONG).show();
                                                            progressBar.setVisibility(View.GONE);
                                                        }
                                                        else{
                                                            Toast.makeText(SignUp.this, "Somethings Wrong!", Toast.LENGTH_LONG).show();
                                                            progressBar.setVisibility(View.GONE);
                                                        }
                                                    }
                                                });
                                            }
                                            else{
                                                Toast.makeText(SignUp.this, "Somethings Wrong! Check Email and Password", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }

                                        }
                                    });
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