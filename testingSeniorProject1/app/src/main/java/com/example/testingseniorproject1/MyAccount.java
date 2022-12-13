package com.example.testingseniorproject1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyAccount extends AppCompatActivity implements View.OnClickListener{
    private TextView textView;
    private ImageView backButton;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);

        textView = (TextView) findViewById(R.id.textView1);
        backButton = (ImageView) findViewById(R.id.backButton1);
        backButton.setOnClickListener(this);

        logout = findViewById(R.id.signOut);

         logout.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseAuth.getInstance().signOut();
                 startActivity(new Intent(MyAccount.this,MainActivity.class));
             }
         });

         user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
         userID = user.getUid();

        final TextView fullNameTextView = findViewById(R.id.fullName);
         final TextView emailAddressTextView = findViewById(R.id.emailAddress);

         reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
             @Override
             public void onDataChange(@NonNull DataSnapshot snapshot) {
                 User userProfile = snapshot.getValue(User.class);
                 if (userProfile != null){
                     String fullName = userProfile.username; // it will store full name not a username
                     String emailAddress = userProfile.email;

                     fullNameTextView.setText(fullName);
                     emailAddressTextView.setText(emailAddress);
                 }
             }

             @Override
             public void onCancelled(@NonNull DatabaseError error) {
                 Toast.makeText(MyAccount.this,"Something wrong happened!", Toast.LENGTH_LONG).show();
             }
         });


    }







    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.backButton1){
            startActivity(new Intent(this, HomePage.class));
        }

    }
}
