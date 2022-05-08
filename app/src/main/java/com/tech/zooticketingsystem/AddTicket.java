package com.tech.zooticketingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tech.zooticketingsystem.Models.Tickets;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddTicket extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    Button button;
    Button button1;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        ref = FirebaseDatabase.getInstance().getReference("Tickets");

        editText1 = (EditText)findViewById(R.id.name);
        editText2 = (EditText)findViewById(R.id.date);
        editText3 = (EditText)findViewById(R.id.adults);
        editText4 = (EditText)findViewById(R.id.child);
        editText5 = (EditText)findViewById(R.id.total);
        button = (Button)findViewById(R.id.add);
        button1 = (Button)findViewById(R.id.cal);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
        String currentDateandTime = sdf.format(new Date());
        editText2.setText(currentDateandTime);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Ownername = editText1.getText().toString();
                final String adults = editText3.getText().toString();
                final String child = editText4.getText().toString();
                final String total = editText5.getText().toString();

                if (Ownername.isEmpty()) {
                    editText1.setError("Name is required");
                }else if (editText2.getText().toString().equals("")) {
                    editText2.setError("DateTime is required");
                }else if (adults.isEmpty()) {
                    editText3.setError("Adults ticket Number is required");
                }else if (child.isEmpty()) {
                    editText4.setError("Child ticket number is invalid");
                }else if (total.isEmpty()) {
                    editText5.setError("Total is required");
                }else {

                    String key = ref.push().getKey();

                    Tickets tickets = new Tickets(key,adults,child,Ownername,editText2.getText().toString(),total);
                    ref.child(key).setValue(tickets);

                    Toast.makeText(AddTicket.this, "Successfully added", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(AddTicket.this, TicketManage.class);
                    startActivity(intent);
                }
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int adultsPrice = 100;
                int childPrice = 50;
                int adults = Integer.parseInt(editText3.getText().toString());
                int child = Integer.parseInt(editText4.getText().toString());

                int totaladults = adultsPrice*adults;
                int totalchilds = childPrice*child;

                int calTotal = totaladults+totalchilds;
                editText5.setText(String.valueOf(calTotal));
            }
        });

    }
}