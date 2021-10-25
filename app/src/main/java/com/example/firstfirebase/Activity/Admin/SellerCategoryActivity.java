package com.example.firstfirebase.Activity.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.firstfirebase.R;

public class SellerCategoryActivity extends AppCompatActivity {
    ImageView imgtshirt,imgsport,imgdress,imgweather,imgglass,imgpurse,imghat,imgshoes,
            imgheadphone,imglaptop,imgwatch,imgmobilephone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_category);
        addControl();
        addEvent();
    }

    private void addEvent() {
       imgtshirt.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(SellerCategoryActivity.this, SellerAddActivity.class);
               intent.putExtra("category","tshirt");
               startActivity(intent);
           }
       });
        imgsport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerCategoryActivity.this,SellerAddActivity.class);
                intent.putExtra("category","sport");
                startActivity(intent);
            }
        });
        imgdress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerCategoryActivity.this,SellerAddActivity.class);
                intent.putExtra("category","dress");
                startActivity(intent);
            }
        });
        imgweather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerCategoryActivity.this,SellerAddActivity.class);
                intent.putExtra("category","weather");
                startActivity(intent);
            }
        });
        imgglass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerCategoryActivity.this,SellerAddActivity.class);
                intent.putExtra("category","glass");
                startActivity(intent);
            }
        });
        imgpurse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerCategoryActivity.this,SellerAddActivity.class);
                intent.putExtra("category","purse");
                startActivity(intent);
            }
        });
        imghat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerCategoryActivity.this,SellerAddActivity.class);
                intent.putExtra("category","hat");
                startActivity(intent);
            }
        });
        imgshoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerCategoryActivity.this,SellerAddActivity.class);
                intent.putExtra("category","shoes");
                startActivity(intent);
            }
        });
        imgheadphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerCategoryActivity.this,SellerAddActivity.class);
                intent.putExtra("category","headphone");
                startActivity(intent);
            }
        });
        imglaptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerCategoryActivity.this,SellerAddActivity.class);
                intent.putExtra("category","laptop");
                startActivity(intent);
            }
        });
        imgwatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerCategoryActivity.this,SellerAddActivity.class);
                intent.putExtra("category","watch");
                startActivity(intent);
            }
        });
        imgmobilephone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SellerCategoryActivity.this,SellerAddActivity.class);
                intent.putExtra("category","mobilephone");
                startActivity(intent);
            }
        });


    }

    private void addControl() {
        imgtshirt=(ImageView) findViewById(R.id.imgtshirt);
        imgsport=(ImageView) findViewById(R.id.imgsports);
        imgdress=(ImageView) findViewById(R.id.imgfemale_dress);
        imgweather=(ImageView) findViewById(R.id.imgsweather);
        imgglass=(ImageView) findViewById(R.id.imgglasses);
        imgpurse=(ImageView) findViewById(R.id.imgpurse_bag);
        imghat=(ImageView) findViewById(R.id.imghats);
        imgshoes=(ImageView) findViewById(R.id.imgshoess);
        imgheadphone=(ImageView) findViewById(R.id.imgheadphone);
        imglaptop=(ImageView) findViewById(R.id.imglaptop);
        imgwatch=(ImageView) findViewById(R.id.imgwatches);
        imgmobilephone=(ImageView) findViewById(R.id.imgmobilephone);

    }
}