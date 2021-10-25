package com.example.firstfirebase.Activity.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstfirebase.Activity.MainActivity;
import com.example.firstfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SellerResActivity extends AppCompatActivity {
    private EditText nameres,phoneres,passres,emailres,addressres;
    private Button btnres;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_res);
        mAuth=FirebaseAuth.getInstance();
        addControl();
        addEvent();
    }

    private void addEvent() {
       btnres.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Regis();
           }
       });
    }

    private void Regis() {
        String name=nameres.getText().toString();
        String pass=passres.getText().toString();
        String phone=phoneres.getText().toString();
        String email=emailres.getText().toString();
        String address=addressres.getText().toString();
        if(!name.equals("")&&!pass.equals("")&&!phone.equals("")&&!email.equals("")&&!address.equals(""))
        {

            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        //Toast.makeText(SellerResActivity.this,"",Toast.LENGTH_LONG).show();
                        DatabaseReference databaseReference;
                        databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                        String udi=mAuth.getCurrentUser().getUid();
                        HashMap<String ,Object> hm=new HashMap<>();
                        hm.put("sid",udi);
                        hm.put("sphone",phone);
                        hm.put("semail",email);
                        hm.put("saddress",address);
                        hm.put("sname",name);
                        Log.d("A",udi);
                        databaseReference.child("Sellers").child(udi).updateChildren(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(SellerResActivity.this,"Successfully register",Toast.LENGTH_LONG).show();
                                Intent intent=new Intent(SellerResActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(SellerResActivity.this,"Fail to register",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(SellerResActivity.this,"Please complete your registration form",Toast.LENGTH_LONG).show();
        }
    }

    private void addControl() {
        nameres=(EditText) findViewById(R.id.edtnamesellerres);
        passres=(EditText) findViewById(R.id.edtpasssellerres);
        phoneres=(EditText) findViewById(R.id.edtphonesellerres);
        emailres=(EditText) findViewById(R.id.edtemailsellerres);
        addressres=(EditText) findViewById(R.id.edtaddresssellerres);
        btnres=(Button) findViewById(R.id.btnsellerregis);
    }
}