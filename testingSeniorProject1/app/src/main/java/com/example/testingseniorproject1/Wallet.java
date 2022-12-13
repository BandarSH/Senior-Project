package com.example.testingseniorproject1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewDebug;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Wallet extends AppCompatActivity implements View.OnClickListener{

    TextView textView;
    ImageView backButton;
    TextView textViewCredit;
    //TextView textViewCreditValue;
    TextView textView2;
    EditText EditTextCoupon;
    Button buttonSubmit;
    private FirebaseUser user;
    private String userID;
    DatabaseReference reference;
    DatabaseReference reference2;

    private double credit;
    ArrayList<String> Coupons;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        textView = (TextView) findViewById(R.id.textView1);
        backButton = (ImageView) findViewById(R.id.backButton1);
        backButton.setOnClickListener(this);

        textViewCredit = (TextView) findViewById(R.id.textView_credit);
        // textViewcreditValue = (TextView) findViewById(R.id.textView_creditValue);

        textView2 = (TextView) findViewById(R.id.textView2);
        EditTextCoupon = (EditText) findViewById(R.id.editText_coupon);


        user = FirebaseAuth.getInstance().getCurrentUser();
        //reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        final TextView credit = findViewById(R.id.textView_creditValue);
        Coupons = new ArrayList<>();



        getCredit(credit);

        buttonSubmit = (Button) findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        if (view.getId() == R.id.backButton1){
            startActivity(new Intent(this, HomePage.class));
        }


        if (view.getId() == R.id.button_submit){
            AddCoupon(EditTextCoupon.getText().toString());

            //Toast.makeText(this, EditTextCoupon.getText().toString(), Toast.LENGTH_SHORT).show();
        }


    }
    boolean done = false;
    public void AddCoupon(String coupon){

        getCoupons();
        if (!coupon.isEmpty()){//<--------done
            //Check if the coupon not used in the database


            for (int i = 0; i < Coupons.size(); i++){

                String s = Coupons.get(i);
                reference = FirebaseDatabase.getInstance().getReference();
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.child("Coupons").child(s).child("code").getValue().equals(coupon)){

                            if (snapshot.child("Coupons").child(s).child("isUsed").getValue(boolean.class)){//<-----------There is problem
                                EditTextCoupon.setError("This coupon is used!");
                                done = true;
                                return;
                            }else{
                                Toast.makeText(Wallet.this, "Amount added successfully", Toast.LENGTH_SHORT).show();
                                double b = snapshot.child("Coupons").child(s).child("amount").getValue(double.class);
                                reference.child("Users").child(userID).child("credit").setValue(b + credit);
                                reference.child("Coupons").child(s).child("isUsed").setValue(true);
                                done = true;
                                final TextView credit = findViewById(R.id.textView_creditValue);
                                getCredit(credit);
                                return;

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //if there is same coupon add vale to the credit and remove the coupon

            }
            if (done == false){
                EditTextCoupon.setError("Please enter correct coupon!");
            }

        }else {
            EditTextCoupon.setError("Please Enter The Coupon!");
        }


    }

    public void getCredit(TextView textView){
        reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);
                if (userProfile != null){
                    credit = userProfile.credit;
                    String s = credit + " SAR";

                    textView.setText(s);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //textView.setText(credit + " SAR");// add credit value from database
    }


    public void getCoupons(){

        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("Coupons").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    for (DataSnapshot dss:snapshot.getChildren()){
                        String couponsID = (String) dss.getKey();
                        Coupons.add(couponsID);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

}
