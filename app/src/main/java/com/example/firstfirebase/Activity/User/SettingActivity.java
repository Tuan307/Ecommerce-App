package com.example.firstfirebase.Activity.User;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstfirebase.Activity.MainActivity;
import com.example.firstfirebase.Activity.ResetPasswordActivity;
import com.example.firstfirebase.Prevalent.Prevalent;
import com.example.firstfirebase.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingActivity extends AppCompatActivity {
    private CircleImageView circleImageViewprofile;
    private EditText edtten, edtphone, edtaddress;
    private Button btnsetques;
    private TextView txtclose, txtupdate;
    private int Gallery_Code = 1;
    private Uri imageUri;
    private String myUrl = "";
    private StorageReference storageReference;
    private String checker = "";
    private StorageTask storageTask;
    public static String nav_namemenu;
    public static String urlanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        storageReference = FirebaseStorage.getInstance("gs://my-demo-project-dfedb.appspot.com").getReference().child("Profile pictures");
        addControl();
        addEvent();

    }

    private void addInfo() {
    }

    private void addEvent() {
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        txtupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checker.equals("clicked")) {
                    userInfoSave(); //update all info
                } else {
                    updateUserInfoOnly(); //update only edit text(name,phone,address)
                }
            }
        });
        circleImageViewprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checker = "clicked";
                CropImage.activity(imageUri).setAspectRatio(1, 1)
                        .start(SettingActivity.this);
//                Intent gallery=new Intent();
//                gallery.setAction(Intent.ACTION_GET_CONTENT);
//                gallery.setType("image/*");
//                startActivityForResult(gallery,Gallery_Code);
            }
        });
        btnsetques.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SettingActivity.this, ResetPasswordActivity.class);
                intent.putExtra("check","setting");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            circleImageViewprofile.setImageURI(imageUri);
        } else {
            //error
            Toast.makeText(SettingActivity.this, "Try Again", Toast.LENGTH_LONG).show();
            startActivity(new Intent(SettingActivity.this, SettingActivity.class));
            finish();
        }
    }

    private void updateUserInfoOnly() {

        DatabaseReference ref = FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users");
        HashMap<String, Object> hm = new HashMap<>();
        hm.put("name", edtten.getText().toString());
        hm.put("phone", edtphone.getText().toString());
        hm.put("address", edtaddress.getText().toString());
        ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(hm);
        startActivity(new Intent(SettingActivity.this, MainActivity.class));
        finish();
    }

    private void userInfoSave() {
        if (TextUtils.isEmpty(edtten.getText().toString())) {
            Toast.makeText(SettingActivity.this, "Please type in your name", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edtphone.getText().toString())) {
            Toast.makeText(SettingActivity.this, "Please type in your phone", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edtaddress.getText().toString())) {
            Toast.makeText(SettingActivity.this, "Please type in your address", Toast.LENGTH_SHORT).show();
        } else if (checker.equals("clicked")) {
            uploadImage();
        }
    }

    private void uploadImage() {
        final ProgressDialog dialog = new ProgressDialog(SettingActivity.this);
        dialog.setTitle("Update Profile");
        dialog.setMessage("Updating...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        final StorageReference fileup = storageReference.child(Prevalent.currentOnlineUser.getPhone() + ".jpg");
        storageTask = fileup.putFile(imageUri);
        storageTask.continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return fileup.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri download = task.getResult();
                    myUrl = download.toString();

                    DatabaseReference ref = FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users");
                    HashMap<String, Object> hm = new HashMap<>();
                    hm.put("name", edtten.getText().toString());
                    hm.put("phone", edtphone.getText().toString());
                    hm.put("address", edtaddress.getText().toString());
                    hm.put("image", myUrl);
                    ref.child(Prevalent.currentOnlineUser.getPhone()).updateChildren(hm);
                    dialog.dismiss();
                    startActivity(new Intent(SettingActivity.this, MainActivity.class));
                    finish();
                } else {
                    Toast.makeText(SettingActivity.this, "faied to action", Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        });
    }

    private void addControl() {
        circleImageViewprofile = (CircleImageView) findViewById(R.id.imganhsetting);
        edtten = (EditText) findViewById(R.id.edtfullnamesetting);
        edtphone = (EditText) findViewById(R.id.edtphonesetting);
        edtaddress = (EditText) findViewById(R.id.edtaddresssetting);
        txtclose = (TextView) findViewById(R.id.txtclosesetting);
        txtupdate = (TextView) findViewById(R.id.txtupdatesetting);
        btnsetques=(Button)findViewById(R.id.btnsetser) ;
        userInfoDisplay(circleImageViewprofile, edtten, edtphone, edtaddress);
    }

    private void userInfoDisplay(CircleImageView circleImageViewprofile, EditText edtten, EditText edtphone, EditText edtaddress) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference().child("Users").child(Prevalent.currentOnlineUser.getPhone());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if (snapshot.child("image").exists()) {
                        String image = snapshot.child("image").getValue().toString();
                        String name = snapshot.child("name").getValue().toString();
                        String phone = snapshot.child("phone").getValue().toString();
                        String address = snapshot.child("address").getValue().toString();
                        if(!image.equals("")) {
                            Picasso.get().load(image).into(circleImageViewprofile);
                        }
                        edtten.setText(name);
                        edtphone.setText(phone);
                        edtaddress.setText(address);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
