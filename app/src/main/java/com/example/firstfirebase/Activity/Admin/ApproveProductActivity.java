package com.example.firstfirebase.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.firstfirebase.ClassUse.Order;
import com.example.firstfirebase.ClassUse.Product;
import com.example.firstfirebase.R;
import com.example.firstfirebase.ViewHoler.OrderViewHolder;
import com.example.firstfirebase.ViewHoler.ProductViewHolder;
import com.example.firstfirebase.ViewHoler.ProductViewHolder2;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ApproveProductActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ProductViewHolder2 orderViewHolder;
    private ArrayList<Product> orderarrayList;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_product);
        addControl();
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        databaseReference.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren())
                {
                    if(data.child("productstate").getValue().toString().equals("not approved"))
                    {
                        Product p=data.getValue(Product.class);
                        orderarrayList.add(p);
                    }
                }
                orderViewHolder.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addControl() {
        recyclerView=(RecyclerView) findViewById(R.id.approverecy);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(ApproveProductActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        orderarrayList=new ArrayList<>();
        orderViewHolder=new ProductViewHolder2(orderarrayList);
        recyclerView.setAdapter(orderViewHolder);

    }
}