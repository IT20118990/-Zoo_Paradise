package com.rukshan.firebasecrudapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCardActivity extends AppCompatActivity {

    // creating variables for our button, edit text,
    // firebase database, database reference, progress bar.
    private Button addCardBtn;
    private TextInputEditText cardNameEdt, cardDescEdt, cardPriceEdt, bestCardSuitedEdt, cardImgEdt, cardLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private ProgressBar loadingPB;
    private String courseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card_);
        // initializing all our variables.
        addCardBtn = findViewById(R.id.idBtnAddCard);
        cardNameEdt = findViewById(R.id.idEdtCardName);
        cardDescEdt = findViewById(R.id.idEdtCardDescription);
        cardPriceEdt = findViewById(R.id.idEdtCardPrice);
        bestCardSuitedEdt = findViewById(R.id.idEdtCardSuitedFor);
        cardImgEdt = findViewById(R.id.idEdtCardImageLink);
        cardLinkEdt = findViewById(R.id.idEdtCardLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line creating our database reference.
        databaseReference = firebaseDatabase.getReference("Courses");
        // adding click listener for our add course button.
        addCardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingPB.setVisibility(View.VISIBLE);
                // getting data from our edit text.
                String cardName = cardNameEdt.getText().toString();
                String cardDesc = cardDescEdt.getText().toString();
                String cardPrice = cardPriceEdt.getText().toString();
                String bestCardSuited = bestCardSuitedEdt.getText().toString();
                String cardImg = cardImgEdt.getText().toString();
                String cardLink = cardLinkEdt.getText().toString();
                courseID = cardName;
                // on below line we are passing all data to our modal class.
                CourseRVModal courseRVModal = new CourseRVModal(courseID, cardName, cardDesc, cardPrice, bestCardSuited, cardImg, cardLink);
                // on below line we are calling a add value event
                // to pass data to firebase database.
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // on below line we are setting data in our firebase database.
                        databaseReference.child(courseID).setValue(courseRVModal);
                        // displaying a toast message.
                        Toast.makeText(AddCardActivity.this, "Card Added..", Toast.LENGTH_SHORT).show();
                        // starting a main activity.
                        startActivity(new Intent(AddCardActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on below line.
                        Toast.makeText(AddCardActivity.this, "Fail to add Card..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}