package com.example.firstfirebase.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.firstfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class MaintainActivity extends AppCompatActivity {
    private ImageView imganhmain;
    private EditText edtnamemain,edtdesmain,edtpricemain;
    private String id="";
    private Button btnmain,btndel;
    private DatabaseReference databaseReference;
    private String name="",price="",des="",img="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintain);
        addControl();
        addEvent();
        saveDataToDataBase();
    }

    private void saveDataToDataBase() {
        btnmain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,Object> hm=new HashMap<String,Object>();
                hm.put("pid",id);
                hm.put("name",edtnamemain.getText().toString());
                hm.put("description",edtdesmain.getText().toString());
                hm.put("price",edtpricemain.getText().toString());
                databaseReference.child("Product").child(id).updateChildren(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(MaintainActivity.this,"Successfully change product",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MaintainActivity.this, SellerCategoryActivity.class));
                            finish();
                        }
                    }
                });
            }
        });
        btndel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("Product").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {

                            Toast.makeText(MaintainActivity.this,"Successfully delete product",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MaintainActivity.this,SellerCategoryActivity.class));
                            finish();
                        }
                    }
                });
            }
        });

    }

    private void addEvent() {

        databaseReference.child("Product").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    name=snapshot.child("name").getValue().toString();
                    des=snapshot.child("description").getValue().toString();
                    price=snapshot.child("price").getValue().toString();
                    Picasso.get().load(snapshot.child("image").getValue().toString()).into(imganhmain);
                    edtnamemain.setText(name);
                    edtdesmain.setText(des);
                    edtpricemain.setText(price);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addControl() {
        imganhmain=(ImageView) findViewById(R.id.imganhmaintain);
        edtnamemain=(EditText) findViewById(R.id.edtnamemaintain);
        edtdesmain=(EditText) findViewById(R.id.edtdesmaintain);
        edtpricemain=(EditText) findViewById(R.id.edtpricemaintain);
        btnmain=(Button) findViewById(R.id.btnapplychange);
        btndel=(Button) findViewById(R.id.btndeleteproduct);
        Intent intent=getIntent();
        id=intent.getStringExtra("id");
        databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        //Toast.makeText(MaintainActivity.this,id+" is the key",Toast.LENGTH_LONG).show();
    }
}