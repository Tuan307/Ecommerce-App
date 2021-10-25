package com.example.firstfirebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstfirebase.Activity.User.SettingActivity;
import com.example.firstfirebase.Prevalent.Prevalent;
import com.example.firstfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResetPasswordActivity extends AppCompatActivity {
    TextView txttraloi;
    EditText edtphone,edtques,edtnewpass;
    Button btnver,btnsetpass;
    String check_status="";
    DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        addControl();
        addEvent();
    }

    private void addEvent() {
        Intent intent=getIntent();
        check_status=intent.getStringExtra("check");
        Toast.makeText(ResetPasswordActivity.this, check_status, Toast.LENGTH_LONG).show();

        if(check_status.equals("setting")) {
            edtphone.setVisibility(View.INVISIBLE);
            edtnewpass.setVisibility(View.INVISIBLE);
            btnsetpass.setVisibility(View.INVISIBLE);
           // String ques = edtques.getText().toString();
            btnver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

            if (edtques.getText().toString().equals("")) {
                Toast.makeText(ResetPasswordActivity.this, "Please type in your answer! ", Toast.LENGTH_LONG).show();
            } else {
                HashMap<String, Object> hm = new HashMap<>();
                hm.put("answer", edtques.getText().toString());
                databaseReference.child("Users").child(Prevalent.currentOnlineUser.getPhone()).child("security").updateChildren(hm).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(ResetPasswordActivity.this, "Successfully ", Toast.LENGTH_LONG).show();
                        Intent in=new Intent(ResetPasswordActivity.this, SettingActivity.class);
                        startActivity(in);
                        finish();
                    }
                });
            }
                }
            });
        }
        if(check_status.equals("login"))
        {
            edtnewpass.setVisibility(View.INVISIBLE);
            btnsetpass.setVisibility(View.INVISIBLE);
           btnver.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   if (edtques.getText().toString().equals("")) {
                       Toast.makeText(ResetPasswordActivity.this, "Please type in your answer! ", Toast.LENGTH_LONG).show();
                   }
                   else {
                       DatabaseReference databaseReference1= FirebaseDatabase.
                               getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app")
                               .getReference();
                       databaseReference1.child("Users").child(edtphone.getText().toString()).
                               addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot snapshot) {
                               if (snapshot.exists()) {
                                   if (snapshot.hasChild("security")) {
                                       if (snapshot.child("security").child("answer").getValue().toString().equals(edtques.getText().toString())){
                                           edtnewpass.setVisibility(View.VISIBLE);
                                       btnsetpass.setVisibility(View.VISIBLE);
                                       btnsetpass.setOnClickListener(new View.OnClickListener() {
                                           @Override
                                           public void onClick(View view) {
                                               if (edtnewpass.getText().toString().equals("")) {
                                                   Toast.makeText(ResetPasswordActivity.this, "Please type in your new password ", Toast.LENGTH_LONG).show();
                                               } else {
                                                   databaseReference1.child("Users").child(edtphone.getText().toString()).child("pass").
                                                           setValue(edtnewpass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                       @Override
                                                       public void onComplete(@NonNull Task<Void> task) {
                                                           Toast.makeText(ResetPasswordActivity.this, "Successfully changed your password", Toast.LENGTH_LONG).show();
                                                           startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                                                       }
                                                   });
                                               }
                                           }
                                       });
                                   }
                                       else
                                       {
                                           Toast.makeText(ResetPasswordActivity.this, "Dont have this ques! ", Toast.LENGTH_LONG).show();
                                       }
                                   }
                                   else
                                   {
                                       Toast.makeText(ResetPasswordActivity.this, "Dont have this key! ", Toast.LENGTH_LONG).show();
                                   }
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError error) {

                           }
                       });
                   }
               }
           });
        }
    }

    private void addControl() {
        txttraloi=(TextView) findViewById(R.id.txttraloi);
        edtphone=(EditText) findViewById(R.id.edtphonereset);
        edtques=(EditText) findViewById(R.id.edtcauhoireset);
        btnver=(Button) findViewById(R.id.btnvertify);
        edtnewpass=(EditText) findViewById(R.id.edtnewpass);
        btnsetpass=(Button) findViewById(R.id.btnsetpass);
    }
}