package com.example.firstfirebase.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.firstfirebase.ClassUse.ProOrdr;
import com.example.firstfirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderProductsActivity extends AppCompatActivity {
    TextView txtten,txttotal;
    String key;
    ArrayList<String> list=new ArrayList<>();
    ArrayList<String> list1;
    ArrayAdapter<String> adapter;
    ListView lvdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_products);
        addControl();
        lvdata=(ListView) findViewById(R.id.lvdata);
        list1=new ArrayList<>();
        addEvent();
    }

    private void addEvent() {
        DatabaseReference Uid= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        Uid.child("CartList").child("OrderView").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //txtten.setText(key);
                for (DataSnapshot data : snapshot.getChildren()) {
                    ProOrdr proOrdr = data.getValue(ProOrdr.class);
                    if (key.equals(proOrdr.getPhone())) {
                        String word = proOrdr.getName().replace("null", "");
                        word=word.replace("[","");
                        word=word.replace("]","\n");
                        String word1=word.replace(".",",");
                        String[] word2=word1.split(",");
                        for(String a:word2)
                        {
                            list.add(a);
                        }
                        adapter=new ArrayAdapter<String>(OrderProductsActivity.this, android.R.layout.simple_list_item_1,list);
                        lvdata.setAdapter(adapter);
                       txttotal.setText("Total: "+proOrdr.getTotal());
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void addControl() {
        txtten=(TextView)findViewById(R.id.ordername1);
        txttotal=(TextView) findViewById(R.id.ordertotal);
        Intent intent=getIntent();
        key=intent.getStringExtra("phone");
    }
}