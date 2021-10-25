package com.example.firstfirebase.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.firstfirebase.ClassUse.Product;
import com.example.firstfirebase.R;
import com.example.firstfirebase.ViewHoler.SearchViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    EditText edtsearch;
    Button btnsearch;
    RecyclerView recyclersearch;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Product> arrayList;
    SearchViewHolder searchViewHolder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input=edtsearch.getText().toString().toLowerCase();
                DatabaseReference searchdata= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                searchdata.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot data:snapshot.getChildren())
                        {
                            if(data.child("productstate").getValue().toString().equals("approved")) {
                                Product p = data.getValue(Product.class);
                                if (p.getName().startsWith(edtsearch.getText().toString()) || p.getName().equalsIgnoreCase(edtsearch.getText().toString())
                                        || p.getName().toLowerCase().startsWith(input) || p.getName().contains(edtsearch.getText().toString()) == true
                                        || p.getName().toLowerCase().contains(edtsearch.getText().toString().toLowerCase()) == true) {
                                    arrayList.add(p);
                                }
                            }
                        }
                        searchViewHolder.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                arrayList.clear();
            }
        });
    }

    private void addControl() {
        edtsearch=(EditText) findViewById(R.id.edtsearch);
        btnsearch=(Button) findViewById(R.id.btnsearch);
        recyclersearch=(RecyclerView) findViewById(R.id.recylersearch);
        recyclersearch.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(SearchActivity.this);
        recyclersearch.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();
        searchViewHolder=new SearchViewHolder(arrayList);
        recyclersearch.setAdapter(searchViewHolder);
    }
}