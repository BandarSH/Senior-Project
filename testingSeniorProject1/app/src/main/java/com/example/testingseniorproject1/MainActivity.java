package com.example.testingseniorproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

        Button signUp, signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//--------------------------------------------------------------
        // declaring the variables in the xml design page

        signUp = (Button) findViewById(R.id.signupHomePage);
        signUp.setOnClickListener(this);
        signIn = (Button) findViewById(R.id.signinHomePage);
        signIn.setOnClickListener(this);


    }
//-------------------------------------------------------------------------------------
    // this step for transferring the user to the other pages

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signupHomePage:
                startActivity(new Intent(this,Register.class));
                break;

            case R.id.signinHomePage:
                startActivity(new Intent(this,LoginActivity.class));
                break;
        }
    }
}