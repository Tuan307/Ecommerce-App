package com.example.firstfirebase.Activity.Seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.firstfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SellerLoginActivity extends AppCompatActivity {
    private EditText name,pass;
    private Button btnlogin,btnres;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_login);
        mAuth=FirebaseAuth.getInstance();
        addControl();
        addEvent();
    }

    private void addEvent() {
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Login();


            }
        });
        btnres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerLoginActivity.this,SellerResActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void Login() {
        String email=name.getText().toString();
        String mk=pass.getText().toString();
        if(!email.equals("")&&!mk.equals(""))
        {
            mAuth.signInWithEmailAndPassword(email,mk).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Intent intent=new Intent(SellerLoginActivity.this,SellerHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        Toast.makeText(SellerLoginActivity.this,"Seem like your password or email is incorrect",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else
        {
            Toast.makeText(SellerLoginActivity.this,"Please finish type in all information",Toast.LENGTH_LONG).show();
        }
    }

    private void addControl() {
        btnlogin=(Button) findViewById(R.id.btnlogseller);
        btnres=(Button) findViewById(R.id.btnresseller);
        name=(EditText) findViewById(R.id.edtnameseller);
        pass=(EditText) findViewById(R.id.edtpassseller);
    }
}