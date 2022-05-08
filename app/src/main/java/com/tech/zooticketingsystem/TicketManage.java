package com.tech.zooticketingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tech.zooticketingsystem.Models.Tickets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class TicketManage extends AppCompatActivity {

    Button button;
    ListView listView;
    List<Tickets> user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_manage);

        button = (Button)findViewById(R.id.addticket);
        listView = (ListView)findViewById(R.id.listview);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TicketManage.this, AddTicket.class);
                startActivity(intent);
            }
        });

        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Tickets");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    Tickets tickets = studentDatasnap.getValue(Tickets.class);
                    user.add(tickets);
                }

                MyAdapter adapter = new MyAdapter(TicketManage.this, R.layout.custom_ticket_details, (ArrayList<Tickets>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        TextView COL5;
        Button button1;
        Button button2;
    }

    class MyAdapter extends ArrayAdapter<Tickets> {
        LayoutInflater inflater;
        Context myContext;
        List<Tickets> user;


        public MyAdapter(Context context, int resource, ArrayList<Tickets> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_ticket_details, null);

                holder.COL1 = (TextView) view.findViewById(R.id.ownername);
                holder.COL2 = (TextView) view.findViewById(R.id.date);
                holder.COL3 = (TextView) view.findViewById(R.id.adults);
                holder.COL4 = (TextView) view.findViewById(R.id.child);
                holder.COL5 = (TextView) view.findViewById(R.id.total);
                holder.button1 = (Button) view.findViewById(R.id.edit);
                holder.button2 = (Button) view.findViewById(R.id.delete);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Name:- "+user.get(position).getCustomerName());
            holder.COL2.setText("Date:- "+user.get(position).getDate());
            holder.COL3.setText("Adults Tickets:- "+user.get(position).getAdultsTickets());
            holder.COL4.setText("Children Tickets:- "+user.get(position).getChildTickets());
            holder.COL5.setText("Total:- "+user.get(position).getTotal());
            System.out.println(holder);

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final String idd = user.get(position).getId();
                                    FirebaseDatabase.getInstance().getReference("Tickets").child(idd).removeValue();
                                    //remove function not written
                                    Toast.makeText(myContext, "Item deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_update_ticket_details, null);
                    dialogBuilder.setView(view1);

                    final EditText editText1 = (EditText) view1.findViewById(R.id.nameup);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.dateup);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.adultsup);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.childup);
                    final EditText editText5 = (EditText) view1.findViewById(R.id.total);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.updatebtn);
                    final Button buttoncal = (Button) view1.findViewById(R.id.calup);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Tickets").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = (String) snapshot.child("customerName").getValue();
                            String date = (String) snapshot.child("date").getValue();
                            String adults = (String) snapshot.child("adultsTickets").getValue();
                            String child = (String) snapshot.child("childTickets").getValue();
                            String total = (String) snapshot.child("total").getValue();

                            editText1.setText(name);
                            editText2.setText(date);
                            editText3.setText(adults);
                            editText4.setText(child);
                            editText5.setText(total);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                    buttoncal.setOnClickListener(new View.OnClickListener() {
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


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault());
                            String currentDateandTime = sdf.format(new Date());
                            editText2.setText(currentDateandTime);

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

                                HashMap map = new HashMap();
                                map.put("customerName", Ownername);
                                map.put("date", editText2.getText().toString());
                                map.put("adultsTickets", adults);
                                map.put("childTickets", child);
                                map.put("total", total);
                                reference.updateChildren(map);

                                Toast.makeText(TicketManage.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            });

            return view;

        }
    }
}