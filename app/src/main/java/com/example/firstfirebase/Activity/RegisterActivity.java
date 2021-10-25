package com.example.firstfirebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    EditText tendangky,matkhaudangky,sodienthoaidangky;
    Button btndangky;
    ProgressDialog loadingdialog;
    DatabaseReference mDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btndangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addAccount();
            }
        });
    }

    private void addAccount() {
        String name=tendangky.getText().toString();
        String pass=matkhaudangky.getText().toString();
        String phone=sodienthoaidangky.getText().toString();

        if(TextUtils.isEmpty(name))
        {
            Toast.makeText(RegisterActivity.this,"Please write your username",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pass))
        {
            Toast.makeText(RegisterActivity.this,"Please write your password",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phone))
        {
            Toast.makeText(RegisterActivity.this,"Please write your phone",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadingdialog.setTitle("Create Account");
            loadingdialog.setMessage("Please wait, while we are checking...");
            loadingdialog.setCanceledOnTouchOutside(false);
            loadingdialog.show();

            checkingwhetherphoneisalreadysigned(name,pass,phone);
        }
    }

    private void checkingwhetherphoneisalreadysigned(String name, String pass, String phone) {
        mDatabase= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!(snapshot.child("Users").child(phone).exists()))
                {
                    HashMap<String,Object> hm=new HashMap<>();
                    hm.put("name",name);
                    hm.put("pass",pass);
                    hm.put("phone",phone);
                    hm.put("image","");
                    hm.put("address","");
                    mDatabase.child("Users").child(phone).updateChildren(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(RegisterActivity.this,"Your Account has been  registered",Toast.LENGTH_SHORT).show();
                                loadingdialog.dismiss();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            }
                            else
                            {
                                Toast.makeText(RegisterActivity.this,"Please check your network connection and try again",Toast.LENGTH_SHORT).show();
                                loadingdialog.dismiss();
                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(RegisterActivity.this,"This phone is already signed, please use another phone number",Toast.LENGTH_LONG).show();
                    loadingdialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addControl() {
        btndangky=(Button) findViewById(R.id.btndangkytaikhoan);
        tendangky=(EditText) findViewById(R.id.edttendangnhapdangky);
        matkhaudangky=(EditText) findViewById(R.id.edtmatkhaudangky);
        sodienthoaidangky=(EditText) findViewById(R.id.sodienthoaidangky);
        loadingdialog=new ProgressDialog(RegisterActivity.this);
    }
}