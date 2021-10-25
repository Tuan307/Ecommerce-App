package com.example.firstfirebase.Activity.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstfirebase.Activity.HomeActivity2;
import com.example.firstfirebase.ClassUse.Cart;
import com.example.firstfirebase.Prevalent.Prevalent;
import com.example.firstfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class ConfirmInfomationActivity extends AppCompatActivity {
    private EditText ten,diachi,sdt,thanhpho;
    private Button btnconfirm;
    private String tota="";
    private String sellid="";
    String name[]=new String[10];
    ArrayList<String> word;
    ArrayList<Cart> arrayList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_infomation);
        addControl();
        getDta();
    }

    private void getDta() {
        Intent intent=getIntent();
        tota=intent.getStringExtra("totalprice");
        sellid=intent.getStringExtra("sellid");
        btnconfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Check();
            }
        });
    }

    private void Check() {
        if(TextUtils.isEmpty(ten.getText().toString()))
        {
            Toast.makeText(ConfirmInfomationActivity.this,"Please type in your name",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(sdt.getText().toString()))
        {
            Toast.makeText(ConfirmInfomationActivity.this,"Please type in your phone number",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(diachi.getText().toString()))
        {
            Toast.makeText(ConfirmInfomationActivity.this,"Please type in your address",Toast.LENGTH_LONG).show();
        }
        else if(TextUtils.isEmpty(thanhpho.getText().toString()))
        {
            Toast.makeText(ConfirmInfomationActivity.this,"Please type in your city",Toast.LENGTH_LONG).show();
        }
        else{
            ConfirmOrder();
        }
    }

    private void ConfirmOrder() {
        String currtime = "",currDate="";
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd yyyy");
        currDate=simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("HH:mm:ss a");
        currtime=simpleDateFormat1.format(calendar.getTime());

        DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        HashMap<String,Object> hm=new HashMap<>();
        hm.put("ototal",tota);
        hm.put("oname",ten.getText().toString());
        hm.put("ophone",sdt.getText().toString());
        hm.put("oaddress",diachi.getText().toString());
        hm.put("ocity",thanhpho.getText().toString());
        hm.put("otime",currtime);
        hm.put("odate",currDate);
        hm.put("state","not shipped");
        hm.put("sellerid",sellid);
        String finalCurrtime = currtime;

        databaseReference.child("Orders").child(Prevalent.currentOnlineUser.getPhone()).child(sdt.getText().toString()+currtime).updateChildren(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                DatabaseReference databaseReference1=FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                databaseReference1.child("CartList").child("UserView").child(Prevalent.currentOnlineUser.getPhone())
                        .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ConfirmInfomationActivity.this, "Successfully  receive order", Toast.LENGTH_LONG).show();
                        Intent inn = new Intent(ConfirmInfomationActivity.this, HomeActivity2.class);
                        inn.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(inn);
                        finish();
                    }
                });
            }
        });
        HashMap<String,Object> hm1=new HashMap<>();

        hm1.put("name",name[0]);
        hm1.put("total",tota);
        hm1.put("phone",sdt.getText().toString()+ finalCurrtime);
        hm1.put("sellerid",sellid);
        DatabaseReference databaseReference2=FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        databaseReference2 .child("CartList").child("OrderView").child(sdt.getText().toString()+ finalCurrtime).updateChildren(hm1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(ConfirmInfomationActivity.this,"Success",Toast.LENGTH_SHORT).show();
            }
        });
//       // arrayList.clear();
    }
    private void addControl() {
        ten=(EditText) findViewById(R.id.edtnameconfirm);
        diachi=(EditText) findViewById(R.id.edtaddressconfirm1);
        sdt=(EditText) findViewById(R.id.edtphoneconfirm);
        thanhpho=(EditText) findViewById(R.id.edtcityconfirm);
        btnconfirm=(Button) findViewById(R.id.btnconfirm);
        word=new ArrayList<>();
        arrayList=new ArrayList<>();
        DatabaseReference databaseRef= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").
                getReference().child("CartList").child("UserView").child(Prevalent.currentOnlineUser.getPhone()).child("Product");
        databaseRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    Cart item=dataSnapshot.getValue(Cart.class);
                    String a = "Product name: ["+item.getPname()+"] Quantity: ["+item.getPquan()+"] Price per:["+item.getPprice()+"]";
                    name[0]+=a+" .";
                    //arrayList.add(0,item);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}