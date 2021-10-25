package com.example.firstfirebase.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.firstfirebase.ClassUse.Order;
import com.example.firstfirebase.R;
import com.example.firstfirebase.ViewHoler.OrderViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminCheckOrderActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private OrderViewHolder orderViewHolder;
    private ArrayList<Order> orderarrayList;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_check_order);
        addControl();
        getDataAdmin();
    }

    private void getDataAdmin() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference()
                .child("Orders");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren())
                {
                    for(DataSnapshot item:data.getChildren())
                    {
                        Order order=item.getValue(Order.class);
                        orderarrayList.add(order);
                    }
                }
                orderViewHolder.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminCheckOrderActivity.this,"Failed to load",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addControl() {
        recyclerView=(RecyclerView) findViewById(R.id.adminorderrecy);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(AdminCheckOrderActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        orderarrayList=new ArrayList<>();
        orderViewHolder=new OrderViewHolder(orderarrayList);
        recyclerView.setAdapter(orderViewHolder);
    }
}