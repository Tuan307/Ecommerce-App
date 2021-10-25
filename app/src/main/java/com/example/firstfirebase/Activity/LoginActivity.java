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
import android.widget.TextView;
import android.widget.Toast;


import com.example.firstfirebase.Activity.Admin.AdminHomeActivity;
import com.example.firstfirebase.Activity.Admin.SellerCategoryActivity;
import com.example.firstfirebase.Activity.Seller.SellerHomeActivity;
import com.example.firstfirebase.Activity.Seller.SellerLoginActivity;
import com.example.firstfirebase.Prevalent.Prevalent;
import com.example.firstfirebase.R;
import com.example.firstfirebase.ClassUse.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {
    EditText tendangnhap,matkhaudangnhap;
    Button btndangnhap;
    CheckBox checkBoxremember;
    TextView txtforgetpass,txtadmin,txtnotadmin,txtseller;
    ProgressDialog loadingdialog;
    DatabaseReference mDatabase;
    private String dbname="Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=tendangnhap.getText().toString();
                String pass=matkhaudangnhap.getText().toString();
                if(TextUtils.isEmpty(name))
                {
                    Toast.makeText(LoginActivity.this,"Please write your username",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(pass))
                {
                    Toast.makeText(LoginActivity.this,"Please write your password",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    loadingdialog.setTitle("logging in");
                    loadingdialog.setMessage("Please wait, while we are checking...");
                    loadingdialog.setCanceledOnTouchOutside(false);
                    loadingdialog.show();

                    LoginUser(name,pass);
                }
            }
        });
        txtadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btndangnhap.setText("Login as Admin");
                txtadmin.setVisibility(View.INVISIBLE);
                txtnotadmin.setVisibility(View.VISIBLE);
                dbname="Admins";
            }
        });
        txtnotadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btndangnhap.setText("Login ");
                txtadmin.setVisibility(View.VISIBLE);
                txtnotadmin.setVisibility(View.INVISIBLE);
                dbname="Users";
            }
        });
        txtforgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,ResetPasswordActivity.class);
                intent.putExtra("check","login");
                startActivity(intent);
            }
        });
        txtseller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SellerLoginActivity.class));
            }
        });
    }

    private void LoginUser(String name, String pass) {

        if(checkBoxremember.isChecked())
        {
            Paper.book().write(Prevalent.UserPhoneKey,name);
            Paper.book().write(Prevalent.UserPasswordKey,pass);
        }


        mDatabase= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((snapshot.child(dbname).child(name).exists()))
                {
                   User user=snapshot.child(dbname).child(name).getValue(User.class);
                   if(String.valueOf(user.getPhone()).equals(name))
                   {
                       if(user.getPass().equals(pass))
                       {
                           if(dbname.equals("Admins")) {
                               Toast.makeText(LoginActivity.this, "Logging in as admin successfully", Toast.LENGTH_SHORT).show();
                               startActivity(new Intent(LoginActivity.this, AdminHomeActivity.class));
                               loadingdialog.dismiss();
                           }
                           else if(dbname.equals("Users"))
                           {
                               Toast.makeText(LoginActivity.this, "Logging in successfully", Toast.LENGTH_SHORT).show();
                               loadingdialog.dismiss();
                               startActivity(new Intent(LoginActivity.this, HomeActivity2.class));
                               Prevalent.currentOnlineUser=user;
                           }
                       }
                       else
                       {
                           Toast.makeText(LoginActivity.this,"Your password is incorrect,try again",Toast.LENGTH_SHORT).show();
                           loadingdialog.dismiss();
                       }
                   }
                   else
                   {
                       Toast.makeText(LoginActivity.this,"Your password or your phone number is incorrect,try again",Toast.LENGTH_SHORT).show();
                       loadingdialog.dismiss();
                   }
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"This phone number is not registered yet... ",Toast.LENGTH_SHORT).show();
                    loadingdialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addControl() {
        tendangnhap=(EditText) findViewById(R.id.edttendangnhap);
        matkhaudangnhap=(EditText) findViewById(R.id.edtmatkhaudangnhap);
        btndangnhap=(Button)findViewById(R.id.btndangnhap);
        txtseller=(TextView) findViewById(R.id.txtseller) ;
        checkBoxremember=(CheckBox) findViewById(R.id.checkboxremember);
        Paper.init(LoginActivity.this);
        txtforgetpass=(TextView) findViewById(R.id.txtforgetpass);
        txtadmin=(TextView) findViewById(R.id.txtadmin);
        txtnotadmin=(TextView) findViewById(R.id.txtnotadmin);
        txtnotadmin.setVisibility(View.INVISIBLE);
        loadingdialog=new ProgressDialog(LoginActivity.this);
    }
}