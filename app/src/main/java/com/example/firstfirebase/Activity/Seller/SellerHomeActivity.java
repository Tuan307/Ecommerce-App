package com.example.firstfirebase.Activity.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.firstfirebase.Activity.Admin.SellerCategoryActivity;
import com.example.firstfirebase.Activity.MainActivity;
import com.example.firstfirebase.ClassUse.Product;
import com.example.firstfirebase.ClassUse.Product1;
import com.example.firstfirebase.R;
import com.example.firstfirebase.ViewHoler.ProductViewHolder;
import com.example.firstfirebase.ViewHoler.ProductViewHolder4;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SellerHomeActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Product1> arrayList;
    private ProductViewHolder4 productViewHolder4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home2);
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.nav_view1);
        recyclerView=(RecyclerView)findViewById(R.id.sellerrecyunapproved);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(SellerHomeActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList=new ArrayList<>();
        productViewHolder4=new ProductViewHolder4(arrayList);
        recyclerView.setAdapter(productViewHolder4);
        getData();
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> { // using lamda
            myClickItem(item); //call here
            return true;
        });
    }
    private void myClickItem(MenuItem item){
        switch (item.getItemId()) {
            case R.id.navigation_home:

                break;
            case R.id.navigation_add:
                Intent intent1=new Intent(SellerHomeActivity.this, SellerCategoryActivity.class);
                startActivity(intent1);
                break;
            case R.id.navigation_logout:
                FirebaseAuth mAuth;
                mAuth=FirebaseAuth.getInstance();
                mAuth.signOut();
                Intent intent=new Intent(SellerHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.navigation_check:
                Intent intent3=new Intent(SellerHomeActivity.this, SellerCheckOrderActivity.class);
                //intent3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent3);
                finish();
                break;
        }


    }


    private void getData() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        databaseReference.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    if(dataSnapshot.child("sellerid").getValue().toString().equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString()))
                    {
                        Product1 p=dataSnapshot.getValue(Product1.class);
                        arrayList.add(p);
                    }
                    else
                    {
                        Toast.makeText(SellerHomeActivity.this,"Cant find "+arrayList.size(),Toast.LENGTH_SHORT).show();
                    }
                }
                productViewHolder4.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}