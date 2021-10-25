package com.example.firstfirebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstfirebase.Activity.User.ConfirmInfomationActivity;
import com.example.firstfirebase.ClassUse.Cart;
import com.example.firstfirebase.Prevalent.Prevalent;
import com.example.firstfirebase.R;
import com.example.firstfirebase.ViewHoler.CartViewHolder;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    RecyclerView recyclerViewcart;
    RecyclerView.LayoutManager layoutManager;
    Button btnnext,btncount;
    TextView txttotalamount;
    ArrayList<Cart> cartarrayList;
    CartViewHolder cartViewHolder;
    int proce = 0;
    String sellid="";
    String name;
    String quan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        addControl();
        addEvent();
    }

    private void addEvent() {
        DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").
                getReference().child("CartList").child("UserView").child(Prevalent.currentOnlineUser.getPhone()).child("Product");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Cart item=dataSnapshot.getValue(Cart.class);
                    String gia=item.getPprice();
                    sellid=item.getSellerid();
                    gia=gia.replace("$","");
                    proce = proce +Integer.parseInt(gia)*Integer.parseInt(item.getPquan());
                    cartarrayList.add(item);
                }
                cartViewHolder.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        btncount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txttotalamount.setText("Total Price= "+String.valueOf(proce)+"$");
            }
        });
        btnnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CartActivity.this,String.valueOf(proce),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(CartActivity.this, ConfirmInfomationActivity.class);
                intent.putExtra("totalprice",String.valueOf(proce));
                intent.putExtra("sellid",sellid);
                startActivity(intent);
                finish();
            }
        });
    }

    private void addControl() {
        recyclerViewcart=(RecyclerView) findViewById(R.id.cartrecycler);
        recyclerViewcart.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(CartActivity.this);
        recyclerViewcart.setLayoutManager(layoutManager);
        cartarrayList=new ArrayList<>();
        cartViewHolder=new CartViewHolder(cartarrayList);
        recyclerViewcart.setAdapter(cartViewHolder);
        btnnext=(Button) findViewById(R.id.btnlistcart);
        txttotalamount=(TextView) findViewById(R.id.txttotalcost);
        btncount=(Button) findViewById(R.id.btncountsum);
    }
}