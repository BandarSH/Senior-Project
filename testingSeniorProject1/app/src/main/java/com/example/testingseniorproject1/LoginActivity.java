package com.example.testingseniorproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register, forgotPassword;
    private EditText email,password;
    private Button signin;
    private CheckBox checkBox;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//---------------------------------------------------------------------------------------------------
        register = (TextView) findViewById(R.id.Register);
        register.setOnClickListener(this);
        forgotPassword = (TextView) findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);
        checkBox = findViewById(R.id.checkbox);
        email = findViewById(R.id.email1);
        password = findViewById(R.id.password1);
        signin = findViewById(R.id.signin1);
        signin.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

//-------------------------------------------------------------------------------------------------------------------
        // method for show the password through a checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

            }
        });
//---------------------------------------------------------------------------------------------------------------------




    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.Register:
                startActivity(new Intent(this,Register.class));
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(this,ForgotPassword.class));
                break;

            case R.id.signin1:
                userLogin();
                break;
        }
    }

    private void userLogin(){
        String email1 = email.getText().toString().trim();
        String password1 = password.getText().toString().trim();

        if(email1.isEmpty()){
            email.setError("Email is required!");
            email.requestFocus();
            return;
        } else if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches()){
            password.setError("Please enter a valid email!");
            password.requestFocus();
            return;
        } else if(password1.isEmpty()){
            password.setError("Password is required!");
            password.requestFocus();
            return;
        }

        if(password1.length() < 6){
            password.setError("Min password length should be 6 characters!");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                   // FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    //---------------------------------------------------------------------------------------
                    // Send a verification email if user isn't verified
                   // if(user.isEmailVerified()) {
                        //redirect to main menu
                        startActivity(new Intent(LoginActivity.this, HomePage.class));
                   // } else{
                     //   user.sendEmailVerification();
                     //   Toast.makeText(LoginActivity.this,"Check your email to verify your account!",Toast.LENGTH_LONG).show();
                  //  }
                    //---------------------------------------------------------------------------------------
                } else{
                    Toast.makeText(LoginActivity.this,"Failed to login! Please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}