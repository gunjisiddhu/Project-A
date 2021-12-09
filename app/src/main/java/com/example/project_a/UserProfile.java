package com.example.project_a;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class UserProfile extends AppCompatActivity {
    TextView fname,sname,mail,dob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        fname = findViewById(R.id.profile_fname);
        sname = findViewById(R.id.profile_lname);
        mail = findViewById(R.id.profile_mail);
        dob = findViewById(R.id.profile_dob);
        Intent intent = getIntent();
        String firstname = intent.getStringExtra("fname");
        String lastname = intent.getStringExtra("sname");
        String email = intent.getStringExtra("mail");
        String dateofb = intent.getStringExtra("dob");

        fname.setText(firstname);
        sname.setText(lastname);
        mail.setText(email);
        dob.setText(dateofb);
    }
}