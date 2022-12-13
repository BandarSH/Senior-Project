package com.example.testingseniorproject1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PaymentActivity extends AppCompatActivity {

    private DatabaseReference reference;
    private DatabaseReference reference1;
    private FirebaseUser user;
    private String userID, balance;
    private int credit;
    private int time;
    TextView currentBalance, rideCost;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);


        //---------------------------------------------------------------------------------------------------------
        //get the user current balance

        currentBalance = findViewById(R.id.currentBalance);
        rideCost = findViewById(R.id.rideCost);


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        reference.child(userID).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if(task.isSuccessful()){

                    if(task.getResult().exists()){
                        DataSnapshot dataSnapshot = task.getResult();
                      //  credit =  dataSnapshot.child("credit").getValue();
                        String balance = String.valueOf(dataSnapshot.child("credit").getValue());
                        credit = Integer.parseInt(balance);
                        currentBalance.setText(balance);

                    }else{
                        Toast.makeText(PaymentActivity.this, "Failed to get balance", Toast.LENGTH_SHORT).show();

                    }

                } else {

                    Toast.makeText(PaymentActivity.this, "Failed to get balance", Toast.LENGTH_SHORT).show();
                }
            }
        });






//---------------------------------------------------------------------------------------------------------
        // get the ride cost
     time = getIntent().getIntExtra("time", 5);
     //String timeString = String.valueOf(time);
    // rideCost.setText(timeString);
        switch(time){
            case 5:
            rideCost.setText("3");
                break;
            case 10:
                rideCost.setText("5");
                break;
            case 15:
                rideCost.setText("7");
                break;
            case 20:
                rideCost.setText("13");
                break;
            case 30:
                rideCost.setText("16");
                break;
            case 60:
                rideCost.setText("20");
                break;
        }
//----------------------------------------------------------------------------------------------------------
// get escooter id
       // Bundle extras = getIntent().getExtras();
       // String qrID = extras.getString("qrID");



//----------------------------------------------------------------------------------------------------------

        findViewById(R.id.payBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//----------------------------------------------------------------------------------------------------------
// calculate the total by getting the current balance minus the ride cost if the total is less than zero then the
// user needs to add balance before booking a ride, otherwise allow the user to book a ride.
     //   reference1 = FirebaseDatabase.getInstance().getReference("SCOOTER");
    if(credit>=time) {
         switch (time){
        case 5:
                credit = credit - 3;

               // reference1.child(qrID).child("isVisible").setValue(false);
                reference.child(userID).child("credit").setValue(credit);

//---------------------------------------------------------------------------------------------------------------------
  // sends the date and time (for count down to DestinationActivity class)
                startActivity(new Intent(PaymentActivity.this, DestinationActivity.class)
                        .putExtra("date", getIntent().getStringExtra("date"))
                        .putExtra("time", getIntent().getIntExtra("time", 0))
                );
//---------------------------------------------------------------------------------------------------------------------
                break;


        case 10:
            credit = credit - 5;

            reference.child(userID).child("credit").setValue(credit);
//---------------------------------------------------------------------------------------------------------------------
            // sends the date and time (for count down to DestinationActivity class)
            startActivity(new Intent(PaymentActivity.this, DestinationActivity.class)
                    .putExtra("date", getIntent().getStringExtra("date"))
                    .putExtra("time", getIntent().getIntExtra("time", 0))
            );
//---------------------------------------------------------------------------------------------------------------------
            break;

        case 15:
            credit = credit - 7;

            reference.child(userID).child("credit").setValue(credit);
//---------------------------------------------------------------------------------------------------------------------
            // sends the date and time (for count down to DestinationActivity class)
            startActivity(new Intent(PaymentActivity.this, DestinationActivity.class)
                    .putExtra("date", getIntent().getStringExtra("date"))
                    .putExtra("time", getIntent().getIntExtra("time", 0))
            );
//---------------------------------------------------------------------------------------------------------------------
            break;

        case 20:
            credit = credit - 13;

            reference.child(userID).child("credit").setValue(credit);
//---------------------------------------------------------------------------------------------------------------------
            // sends the date and time (for count down to DestinationActivity class)
            startActivity(new Intent(PaymentActivity.this, DestinationActivity.class)
                    .putExtra("date", getIntent().getStringExtra("date"))
                    .putExtra("time", getIntent().getIntExtra("time", 0))
            );
//---------------------------------------------------------------------------------------------------------------------
            break;


        case 30:
            credit = credit - 16;

            reference.child(userID).child("credit").setValue(credit);
//---------------------------------------------------------------------------------------------------------------------
            // sends the date and time (for count down to DestinationActivity class)
            startActivity(new Intent(PaymentActivity.this, DestinationActivity.class)
                    .putExtra("date", getIntent().getStringExtra("date"))
                    .putExtra("time", getIntent().getIntExtra("time", 0))
            );
//---------------------------------------------------------------------------------------------------------------------
            break;

        case 60:
            credit = credit - 20;

            reference.child(userID).child("credit").setValue(credit);
//---------------------------------------------------------------------------------------------------------------------
            // sends the date and time (for count down to DestinationActivity class)
            startActivity(new Intent(PaymentActivity.this, DestinationActivity.class)
                    .putExtra("date", getIntent().getStringExtra("date"))
                    .putExtra("time", getIntent().getIntExtra("time", 0))
            );
//---------------------------------------------------------------------------------------------------------------------
            break;





    }



    } else{
        Toast.makeText(PaymentActivity.this, "You don't have enough credits!", Toast.LENGTH_SHORT).show();
    }
            }
        });

    }

}