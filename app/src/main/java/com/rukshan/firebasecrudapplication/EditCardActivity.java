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

import java.util.HashMap;
import java.util.Map;

public class EditCardActivity extends AppCompatActivity {

    // creating variables for our edit text, firebase database,
    // database reference, course rv modal,progress bar.
    private TextInputEditText cardNameEdt, cardDescEdt, cardPriceEdt, bestCardSuitedEdt, cardImgEdt, cardLinkEdt;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    CourseRVModal courseRVModal;
    private ProgressBar loadingPB;
    // creating a string for our course id.
    private String cardID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
        // initializing all our variables on below line.
        Button addCourseBtn = findViewById(R.id.idBtnAddCard);
        cardNameEdt = findViewById(R.id.idEdtCardName);
        cardDescEdt = findViewById(R.id.idEdtCardDescription);
        cardPriceEdt = findViewById(R.id.idEdtCardPrice);
        bestCardSuitedEdt = findViewById(R.id.idEdtCardSuitedFor);
        cardImgEdt = findViewById(R.id.idEdtCardImageLink);
        cardLinkEdt = findViewById(R.id.idEdtCardLink);
        loadingPB = findViewById(R.id.idPBLoading);
        firebaseDatabase = FirebaseDatabase.getInstance();
        // on below line we are getting our modal class on which we have passed.
        courseRVModal = getIntent().getParcelableExtra("course");
        Button deleteCourseBtn = findViewById(R.id.idBtnDeleteCard);

        if (courseRVModal != null) {
            // on below line we are setting data to our edit text from our modal class.
            cardNameEdt.setText(courseRVModal.getCardName());
            cardPriceEdt.setText(courseRVModal.getCardPrice());
            bestCardSuitedEdt.setText(courseRVModal.getBestCardSuitedFor());
            cardImgEdt.setText(courseRVModal.getCardImg());
            cardLinkEdt.setText(courseRVModal.getCardLink());
            cardDescEdt.setText(courseRVModal.getCardDescription());
            cardID = courseRVModal.getCardId();
        }

        // on below line we are initialing our database reference and we are adding a child as our course id.
        databaseReference = firebaseDatabase.getReference("Courses").child(cardID);
        // on below line we are adding click listener for our add course button.
        addCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are making our progress bar as visible.
                loadingPB.setVisibility(View.VISIBLE);
                // on below line we are getting data from our edit text.
                String cardName = cardNameEdt.getText().toString();

                String cardPrice = cardPriceEdt.getText().toString();
                String bestCardSuited = bestCardSuitedEdt.getText().toString();
                String cardImg = cardImgEdt.getText().toString();
                String cardLink = cardLinkEdt.getText().toString();
                String cardDesc = cardDescEdt.getText().toString();
                // on below line we are creating a map for
                // passing a data using key and value pair.
                Map<String, Object> map = new HashMap<>();
                map.put("cardName", cardName);

                map.put("cardPrice", cardPrice);
                map.put("bestCardSuitedFor", bestCardSuited);
                map.put("cardImg", cardImg);
                map.put("cardLink", cardLink);
                map.put("cardDescription",cardDesc);
                map.put("cardId", cardID);

                // on below line we are calling a database reference on
                // add value event listener and on data change method
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        // making progress bar visibility as gone.
                        loadingPB.setVisibility(View.GONE);
                        // adding a map to our database.
                        databaseReference.updateChildren(map);
                        // on below line we are displaying a toast message.
                        Toast.makeText(EditCardActivity.this, "Card Updated..", Toast.LENGTH_SHORT).show();
                        // opening a new activity after updating our course.
                        startActivity(new Intent(EditCardActivity.this, MainActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // displaying a failure message on toast.
                        Toast.makeText(EditCardActivity.this, "Fail to update card..", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // adding a click listener for our delete course button.
        deleteCourseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calling a method to delete a course.
                deleteCard();
            }
        });

    }

    private void deleteCard() {
        // on below line calling a method to delete the course.
        databaseReference.removeValue();
        // displaying a toast message on below line.
        Toast.makeText(this, "Card Deleted..", Toast.LENGTH_SHORT).show();
        // opening a main activity on below line.
        startActivity(new Intent(EditCardActivity.this, MainActivity.class));
    }
}