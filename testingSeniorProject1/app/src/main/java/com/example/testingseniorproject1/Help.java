package com.example.testingseniorproject1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Help extends AppCompatActivity implements View.OnClickListener{

    TextView textView;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        textView = (TextView) findViewById(R.id.textView6);
        backButton = (ImageView) findViewById(R.id.backButton6);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.backButton6){
            startActivity(new Intent(this, HomePage.class));
        }

    }
}
