package com.example.firstfirebase.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstfirebase.Activity.Admin.CategoryActivity;
import com.example.firstfirebase.ClassUse.Product;
import com.example.firstfirebase.R;
import com.example.firstfirebase.ViewHoler.ProductViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailCategoty extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product> productArrayList;
    private ProductViewHolder productAdapter;
    private Button btnback;
    String keyword="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_categoty);
        addControl();
        addEvent();
        productArrayList.clear();
    }

    private void addEvent() {
        Intent intent=getIntent();
        keyword=intent.getStringExtra("value");
        DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        databaseReference.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data:snapshot.getChildren())
                {
                    Product p=data.getValue(Product.class);
                    if(p.getCategory().equals(keyword))
                    {
                        productArrayList.add(p);
                    }
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DetailCategoty.this, CategoryActivity.class));
                finish();
            }
        });
    }

    private void addControl() {
        btnback=(Button) findViewById(R.id.btnbackdetail);
        recyclerView=(RecyclerView) findViewById(R.id.detailrecy);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(DetailCategoty.this);
        recyclerView.setLayoutManager(layoutManager);
        productArrayList=new ArrayList<>();
        productAdapter=new ProductViewHolder(productArrayList);
        recyclerView.setAdapter(productAdapter);
    }
}