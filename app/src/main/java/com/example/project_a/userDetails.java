package com.example.project_a;

import android.widget.EditText;

public class userDetails {
        private String username,firstname,secondname,password,mail,dob;

        public userDetails() { }

        public userDetails(String username, String firstname, String secondname, String password, String mail, String dob) {
                this.username = username;
                this.firstname = firstname;
                this.secondname = secondname;
                this.password = password;
                this.mail = mail;
                this.dob = dob;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getFirstname() {
                return firstname;
        }

        public void setFirstname(String firstname) {
                this.firstname = firstname;
        }

        public String getSecondname() {
                return secondname;
        }

        public void setSecondname(String secondname) {
                this.secondname = secondname;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getMail() {
                return mail;
        }

        public void setMail(String mail) {
                this.mail = mail;
        }

        public String getDob() {
                return dob;
        }

        public void setDob(String dob) {
                this.dob = dob;
        }
}
