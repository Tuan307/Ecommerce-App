package com.example.firstfirebase.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.firstfirebase.Activity.User.DetailCategoty;
import com.example.firstfirebase.R;

public class CategoryActivity extends AppCompatActivity {
    ImageView imgtshirt1,imgsport1,imgdress1,imgweather1,imgglass1,imgpurse1,imghat1,imgshoes1,
            imgheadphone1,imglaptop1,imgwatch1,imgmobilephone1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        addControl();
        addEvent();
    }

    private void addEvent() {
        imgtshirt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this, DetailCategoty.class);
                intent.putExtra("value","tshirt");
                startActivity(intent);
            }
        });
        imgsport1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this,DetailCategoty.class);
                intent.putExtra("value","sport");
                startActivity(intent);
            }
        });
        imgdress1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this,DetailCategoty.class);
                intent.putExtra("value","dress");
                startActivity(intent);
            }
        });
        imgweather1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this,DetailCategoty.class);
                intent.putExtra("value","weather");
                startActivity(intent);
            }
        });
        imgglass1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this,DetailCategoty.class);
                intent.putExtra("value","glass");
                startActivity(intent);
            }
        });
        imgpurse1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this,DetailCategoty.class);
                intent.putExtra("value","purse");
                startActivity(intent);
            }
        });
        imghat1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this,DetailCategoty.class);
                intent.putExtra("value","hat");
                startActivity(intent);
            }
        });
        imgshoes1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this,DetailCategoty.class);
                intent.putExtra("value","shoes");
                startActivity(intent);
            }
        });
        imgheadphone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this,DetailCategoty.class);
                intent.putExtra("value","headphone");
                startActivity(intent);
            }
        });
        imglaptop1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this,DetailCategoty.class);
                intent.putExtra("value","laptop");
                startActivity(intent);
            }
        });
        imgwatch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this,DetailCategoty.class);
                intent.putExtra("value","watch");
                startActivity(intent);
            }
        });
        imgmobilephone1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(CategoryActivity.this,DetailCategoty.class);
                intent.putExtra("value","mobilephone");
                startActivity(intent);
            }
        });
    }

    private void addControl() {
        imgdress1=(ImageView) findViewById(R.id.imgfemale_dress1);
        imgtshirt1=(ImageView) findViewById(R.id.imgtshirt1);
        imgsport1=(ImageView) findViewById(R.id.imgsports1);
        imgweather1=(ImageView) findViewById(R.id.imgsweather1);
        imgglass1=(ImageView) findViewById(R.id.imgglasses1);
        imgpurse1=(ImageView) findViewById(R.id.imgpurse_bag1);
        imghat1=(ImageView) findViewById(R.id.imghats1);
        imgshoes1=(ImageView) findViewById(R.id.imgshoess1);
        imgheadphone1=(ImageView) findViewById(R.id.imgheadphone1);
        imglaptop1=(ImageView) findViewById(R.id.imglaptop1);
        imgwatch1=(ImageView) findViewById(R.id.imgwatches1);
        imgmobilephone1=(ImageView) findViewById(R.id.imgmobilephone1);
    }
}