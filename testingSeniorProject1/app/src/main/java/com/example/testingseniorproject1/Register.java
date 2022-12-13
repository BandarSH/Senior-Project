package com.example.testingseniorproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener{


    private EditText username,password,repassword, email;
    private Button signup, signin, alreadyHaveAcc;
    private FirebaseAuth mAuth;
    private double credit = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//--------------------------------------------------------------
        // Declaring the variables in the xml design page
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repassword = findViewById(R.id.repassword);
        signup = findViewById(R.id.signup);
        signup.setOnClickListener(this);
        signin = findViewById(R.id.signin);
        alreadyHaveAcc = (Button) findViewById(R.id.signin);
        alreadyHaveAcc.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }
//-------------------------------------------------------------------------------------
    // This step for transferring the user to the other pages
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signin:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.signup:
                registerUser();
                break;
        }
    }
//------------------------------------------------------------------------------------------------
    // This step for verifying the information entered and making sure the user has entered a valid information
    private void registerUser(){
        String username1 = username.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();
        String repassword1 = repassword.getText().toString().trim();

        if(username1.isEmpty()){
            username.setError("Username is required!");
            username.requestFocus();
            return;
        } else if(email1.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        } else if(password1.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        } else if(repassword1.isEmpty()){
            repassword.setError("Password is required!");
            repassword.requestFocus();
            return;
        }


        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            email.setError("Please provide a valid email!");
            email.requestFocus();
            return;
        }

        if(password1.length() < 6){
            password.setError("Min password length should be 6 characters!");
            password.requestFocus();
            return;
        }
        boolean check = checkPassword(password1, repassword1);
        if(check == false){
            repassword.setError("Your passwords must match");
            repassword.requestFocus();

            return;
        }

//---------------------------------------------------------------------------------------------------------------------
        //Creates a user object and send the information to the firebase database

        mAuth.createUserWithEmailAndPassword(email1,password1)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    User user = new User(username1,email1,password1,credit);

                                    FirebaseDatabase.getInstance().getReference("Users")
                                       .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                       .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){

                                                Toast.makeText(Register.this, "User has been registered successfully!",Toast.LENGTH_LONG).show();


                                            } else{
                                                Toast.makeText(Register.this,"Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                } else{
                                    Toast.makeText(Register.this,"Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
    }
//----------------------------------------------------------------------
    // Method for checking whether the passwords match in the registeration step
    private boolean checkPassword(String pass, String repass){
      if (pass.equalsIgnoreCase(repass))
          return true;
      else
          return false;
    }
//------------------------------------------------------------------------
}