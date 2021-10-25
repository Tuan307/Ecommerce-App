package com.example.firstfirebase.Activity.Admin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.firstfirebase.Activity.Seller.SellerHomeActivity;
import com.example.firstfirebase.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class SellerAddActivity extends AppCompatActivity {
    private String Categoryname,Description,Price,PName,saveCurrentDate,saveCurrentTime;
    private ImageView imgselectpic;
    private EditText edtten,edtmota,edtgia;
    private Button btnaddsanpham;
    private static  final  int GalleryPick=1;
    private Uri imageUri;
    private String productkey,downloadUrlImage;
    private StorageReference storageReference;
    private DatabaseReference pdatabaseReference,sellerDataRef;
    private ProgressDialog dialog;
    private String sname,semail,sphone,sid,saddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_add);
        Categoryname=getIntent().getExtras().get("category").toString();
        storageReference= FirebaseStorage.getInstance("gs://my-demo-project-dfedb.appspot.com").getReference().child("Product Images");
        pdatabaseReference=FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").
                getReference().child("Product");
        sellerDataRef=FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").
                getReference().child("Sellers");
        addControl();
        sellerDataRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    sname=snapshot.child("sname").getValue().toString();
                    saddress=snapshot.child("saddress").getValue().toString();
                    sphone=snapshot.child("sphone").getValue().toString();
                    sid=snapshot.child("sid").getValue().toString();
                    semail=snapshot.child("semail").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        addEvent();
    }

    private void addEvent() {
        imgselectpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
        btnaddsanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
    }

    private void ValidateProductData() {
        Description=edtmota.getText().toString();
        PName=edtten.getText().toString();
        Price=edtgia.getText().toString();
        if(imageUri==null)
        {
            Toast.makeText(SellerAddActivity.this,"Image of product is empty,please choose your product image",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Description))
        {
            Toast.makeText(SellerAddActivity.this,"Please type in product description !",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(PName))
        {
            Toast.makeText(SellerAddActivity.this,"Please type in product name !",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(Price))
        {
            Toast.makeText(SellerAddActivity.this,"Please type in product price !",Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreProductInfomation();
        }
    }

    private void StoreProductInfomation() {
        dialog.setTitle("Add product");
        dialog.setMessage("Adding Product to your Database...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("MMM dd yyyy");
        saveCurrentDate=simpleDateFormat.format(calendar.getTime());
        SimpleDateFormat simpleTime=new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime=simpleTime.format(calendar.getTime());

        productkey=saveCurrentDate+saveCurrentTime;

        StorageReference filepath=storageReference.child(imageUri.getLastPathSegment()+productkey+".jpg");
        final UploadTask uploadTask=filepath.putFile(imageUri);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
                String error=e.toString();
                Toast.makeText(SellerAddActivity.this,"Upload Failure due to :"+error,Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(SellerAddActivity.this,"Upload Successfully :",Toast.LENGTH_LONG).show();
                Task<Uri> urltask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if(!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadUrlImage=filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if(task.isSuccessful())
                        {
                            downloadUrlImage=task.getResult().toString();
                            Toast.makeText(SellerAddActivity.this,"Saved product image to database",Toast.LENGTH_LONG).show();
                            SaveProductIntoDatabase();
                        }
                    }
                });
            }
        });

    }

    private void SaveProductIntoDatabase() {
        HashMap<String,Object> producthm=new HashMap<>();
        producthm.put("pid",productkey);// product id
        producthm.put("data",saveCurrentDate);
        producthm.put("time",saveCurrentTime);
        producthm.put("description",Description);
        producthm.put("image",downloadUrlImage);
        producthm.put("category",Categoryname);
        producthm.put("price",Price);
        producthm.put("name",PName);

        producthm.put("sellername",sname);
        producthm.put("selleraddress",saddress);
        producthm.put("selleremail",semail);
        producthm.put("sellerphone",sphone);
        producthm.put("sellerid",sid);
        producthm.put("productstate","not approved");

        pdatabaseReference.child(productkey).updateChildren(producthm).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    startActivity(new Intent(SellerAddActivity.this, SellerHomeActivity.class));
                    dialog.dismiss();
                    Toast.makeText(SellerAddActivity.this,"Product add into Database Successfully",Toast.LENGTH_LONG).show();
                }
                else
                {
                    dialog.dismiss();
                    String mess=task.getException().toString();
                    Toast.makeText(SellerAddActivity.this,mess,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent gallery=new Intent();
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        gallery.setType("image/*");
        startActivityForResult(gallery,GalleryPick);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GalleryPick && resultCode== RESULT_OK && data!=null)
        {
            imageUri=data.getData();
            imgselectpic.setImageURI(imageUri);
        }
    }

    private void addControl() {
        imgselectpic=(ImageView) findViewById(R.id.imgselectpic);
        edtten=(EditText) findViewById(R.id.edtaddname);
        edtmota=(EditText) findViewById(R.id.edtadddescription);
        edtgia=(EditText) findViewById(R.id.edtadddprice);
        btnaddsanpham=(Button) findViewById(R.id.btnaddnewproduct);
        dialog=new ProgressDialog(SellerAddActivity.this);
    }

}