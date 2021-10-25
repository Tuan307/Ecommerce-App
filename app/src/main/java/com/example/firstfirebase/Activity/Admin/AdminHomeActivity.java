package com.example.firstfirebase.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firstfirebase.Activity.HomeActivity2;
import com.example.firstfirebase.Activity.MainActivity;
import com.example.firstfirebase.R;

public class AdminHomeActivity extends AppCompatActivity {
    Button btnlogout,btncheckorder,btnmaintain,btncheckapprove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        addControl();
        addEvent();
    }

    private void addEvent() {

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
        btncheckorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, AdminCheckOrderActivity.class);
                startActivity(intent);
            }
        });
        btnmaintain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AdminHomeActivity.this, HomeActivity2.class);
                intent.putExtra("Admin","Admin");
                startActivity(intent);
            }
        });
        btncheckapprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(AdminHomeActivity.this, ApproveProductActivity.class);
                startActivity(intent1);
            }
        });
    }

    private void addControl() {
        btncheckapprove=(Button) findViewById(R.id.btnadmincheckandapproveorder);
        btncheckorder=(Button) findViewById(R.id.btnadmincheckorder);
        btnlogout=(Button) findViewById(R.id.btnadminlogout);
        btnmaintain=(Button) findViewById(R.id.btnmaitain);
    }
}