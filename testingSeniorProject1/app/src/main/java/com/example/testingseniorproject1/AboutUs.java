package com.example.testingseniorproject1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AboutUs extends AppCompatActivity implements View.OnClickListener{

    TextView textView;
    ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        textView = (TextView) findViewById(R.id.textView7);
        backButton = (ImageView) findViewById(R.id.backButton7);
        backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.backButton7){
            startActivity(new Intent(this, HomePage.class));
        }

    }
}
