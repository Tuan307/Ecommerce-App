package com.example.firstfirebase.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.firstfirebase.Activity.HomeActivity2;
import com.example.firstfirebase.ClassUse.Product;
import com.example.firstfirebase.Prevalent.Prevalent;
import com.example.firstfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ProdutDetailActivity extends AppCompatActivity {
    ImageView imgpdetail;
    TextView txttendetail,txtgiadetail,txtdesdetail;
    Button add_cart;
    String productId="";
    String sellerId="";
    ElegantNumberButton elegantNumberButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produt_detail);
        addControl();
        addEvent();
    }

    private void addEvent() {
        Intent intent=getIntent();
        productId=intent.getStringExtra("pid");
        sellerId=intent.getStringExtra("sellerid");
        DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference().child("Product");
        databaseReference.child(productId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    Product pr=snapshot.getValue(Product.class);
                    txttendetail.setText(pr.getName());
                    txtgiadetail.setText(pr.getPrice());
                    txtdesdetail.setText(pr.getDescription());
                    Picasso.get().load(pr.getImage()).into(imgpdetail);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        add_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addingToCartList();
            }
        });
    }

    private void addingToCartList() {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd, yyyy");
        String currentTime,currentDate;
        currentDate=simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("HH:mm:ss a");
        currentTime=simpleDateFormat1.format(calendar.getTime());

        DatabaseReference cartdatabase=FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").
                getReference().child("CartList");
        HashMap<String,Object> hm=new HashMap<>();
        hm.put("pid",productId);
        hm.put("pname",txttendetail.getText().toString());
        hm.put("pprice",txtgiadetail.getText().toString());
        hm.put("pdate",currentDate);
        hm.put("ptime",currentTime);
        hm.put("pquan",elegantNumberButton.getNumber());
        hm.put("pdiscount","");
        hm.put("sellerid",sellerId);

        cartdatabase.child("UserView").child(Prevalent.currentOnlineUser.getPhone()).child("Product").child(productId)
                .updateChildren(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
//                    cartdatabase.child("AdminView").child(Prevalent.currentOnlineUser.getPhone()).child(productId)
//                            .updateChildren(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
//                        @Override
//                        public void onComplete(@NonNull Task<Void> task) {
//                            if(task.isSuccessful())
//                            {
//                                Toast.makeText(ProdutDetailActivity.this,"Successfully adding item to your cart list",Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(ProdutDetailActivity.this,HomeActivity2.class));
//                            }
//                        }
//                    });
                    Toast.makeText(ProdutDetailActivity.this,"Successfully adding item to your cart list",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ProdutDetailActivity.this, HomeActivity2.class));
                }
            }
        });
    }

    private void addControl() {
        imgpdetail=(ImageView) findViewById(R.id.imgproaductdetail);
        txttendetail=(TextView) findViewById(R.id.txtpnamedetail1);
        txtdesdetail=(TextView) findViewById(R.id.txtpdesdetail1);
        txtgiadetail=(TextView) findViewById(R.id.txtppricedetail1);
        add_cart=(Button) findViewById(R.id.add_cart);
        elegantNumberButton=(ElegantNumberButton) findViewById(R.id.eleganbtn);

    }
}