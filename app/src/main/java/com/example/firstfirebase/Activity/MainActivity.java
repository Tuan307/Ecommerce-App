package com.example.firstfirebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.firstfirebase.Activity.Seller.SellerHomeActivity;
import com.example.firstfirebase.Activity.Seller.SellerResActivity;
import com.example.firstfirebase.Prevalent.Prevalent;
import com.example.firstfirebase.R;
import com.example.firstfirebase.ClassUse.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
    Button btnjoinnow,btndangnhap;
    DatabaseReference mDatabase;
    ProgressDialog loadingdialog;
    private String dbname="Users";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControl();
        addEvent();
    }

    private void addEvent() {
        btndangnhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        btnjoinnow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        String UserPhoneKey=Paper.book().read(Prevalent.UserPhoneKey);
        String UserPasswordKey=Paper.book().read(Prevalent.UserPasswordKey);

        if(UserPasswordKey!="" &&UserPhoneKey!="")
        {
            if(!TextUtils.isEmpty(UserPhoneKey)&&!TextUtils.isEmpty(UserPasswordKey))
            {

                AllowAccess(UserPhoneKey,UserPasswordKey);
                loadingdialog.setTitle("Already logged in");
                loadingdialog.setMessage("Please wait, while we are checking...");
                loadingdialog.setCanceledOnTouchOutside(false);
                loadingdialog.show();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser!=null)
        {
            Intent intent=new Intent(MainActivity.this, SellerHomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void AllowAccess(String userPhoneKey, String userPasswordKey) {
        mDatabase= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if((snapshot.child(dbname).child(userPhoneKey).exists()))
                {
                    User user=snapshot.child(dbname).child(userPhoneKey).getValue(User.class);
                    if(user.getPhone().equals(userPhoneKey))
                    {
                        if(user.getPass().equals(userPasswordKey))
                        {
                            Toast.makeText(MainActivity.this,"Logging in"+userPhoneKey+" account",Toast.LENGTH_SHORT).show();
                            Prevalent.currentOnlineUser=user;
                            startActivity(new Intent(MainActivity.this, HomeActivity2.class));
                            loadingdialog.dismiss();
                        }
                        else
                        {
                            Toast.makeText(MainActivity.this,"Your password is incorrect,try again",Toast.LENGTH_SHORT).show();
                            loadingdialog.dismiss();
                        }
                    }
                    else
                    {
                        Toast.makeText( MainActivity.this,"Your password or your phone number is incorrect,try again",Toast.LENGTH_SHORT).show();
                        loadingdialog.dismiss();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,"This phone number is not registered yet... ",Toast.LENGTH_SHORT).show();
                    loadingdialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void addControl() {
        btndangnhap=(Button) findViewById(R.id.btnloginin);
        btnjoinnow=(Button) findViewById(R.id.btnjoinnow);
        loadingdialog=new ProgressDialog(MainActivity.this);
        Paper.init(MainActivity.this);
    }

}