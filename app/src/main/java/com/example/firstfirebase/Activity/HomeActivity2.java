package com.example.firstfirebase.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firstfirebase.Activity.Admin.CategoryActivity;
import com.example.firstfirebase.Activity.User.SearchActivity;
import com.example.firstfirebase.Activity.User.SettingActivity;
import com.example.firstfirebase.ClassUse.LoaiMenu;
import com.example.firstfirebase.Adapter.LoaimenuAdapter;
import com.example.firstfirebase.ClassUse.Product;
import com.example.firstfirebase.Prevalent.Prevalent;
import com.example.firstfirebase.R;
import com.example.firstfirebase.ViewHoler.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class HomeActivity2 extends AppCompatActivity {
    private Toolbar toolbarhome;
    private DrawerLayout drawerLayout;
    private NavigationView navigationViewmenu;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ListView lvmanhinhchinh;
    private FloatingActionButton fab;
    private ArrayList<LoaiMenu> loaiMenuArrayList;
    private LoaimenuAdapter loaimenuAdapter;
    private TextView txttenuser;
    private CircleImageView civanhuser;
    private DatabaseReference databaseReference;
    private ProductViewHolder productAdapter;
    private ArrayList<Product> products;
    public static String key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference().child("Product");
        addControl();
        getData();
        addToolbar();
        addEvent();
        Intent in=getIntent();
        key=in.getStringExtra("Admin");
        if(key==null)
        {
            key="A";
        }
        getN();
    }

    private void getN() {
        if(key.equals("Admin")) {

        }
        else
        {
            txttenuser.setText(Prevalent.currentOnlineUser.getName().toString());
            if (!Prevalent.currentOnlineUser.getImage().equals("")) {
                Picasso.get().load(Prevalent.currentOnlineUser.getImage().toString()).into(civanhuser);
            }
        }
    }

    private void getData() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {

                    if(dataSnapshot.child("productstate").getValue().toString().equals("approved")) {
                        Product newproduct = dataSnapshot.getValue(Product.class);
                        products.add(newproduct);
                    }
                }
                productAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomeActivity2.this,"Failed to load",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addEvent() {
        lvmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i==0)
                {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else if(i==1)
                {
//                    if(key.equals("Admin"))
//                    {
//                        getPosition();
//                        startActivity(new Intent(HomeActivity2.this, MaintainActivity.class));
//                    }
                    //else {
                    if(!key.equals("Admin")) {
                        startActivity(new Intent(HomeActivity2.this, CartActivity.class));
                        //}
                    }
                }
                else if(i==2)
                {
                    startActivity(new Intent(HomeActivity2.this, SearchActivity.class));
                }
                else if(i==3)
                {
                    startActivity(new Intent(HomeActivity2.this, CategoryActivity.class));
                }
                else if(i==4)
                {
                    if(key.equals("Admin")) {

                    }
                    else {
                        Intent intent = new Intent(HomeActivity2.this, SettingActivity.class);
                        startActivity(intent);
                    }
                }
                else if(i==5)
                {
                    if(!key.equals("Admin")) {
                        Paper.book().destroy();
                        Intent intent = new Intent(HomeActivity2.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);// xoa toan bo lich su cua cac man hinh truoc do
                        startActivity(intent);
                    }
                }
            }
        });

    }

    private void getPosition() {

    }

    private void addToolbar() {
        setSupportActionBar(toolbarhome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarhome.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarhome.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        if(!key.equals("Admin")) {
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HomeActivity2.this, "You click the cart button", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(HomeActivity2.this, CartActivity.class));
                }
            });
        }
    }



    private void addControl() {
        toolbarhome=(Toolbar) findViewById(R.id.toolbarhomepage);
        navigationViewmenu=(NavigationView) findViewById(R.id.navigationmenu);
        products=new ArrayList<>();
        recyclerView=(RecyclerView) findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(HomeActivity2.this);
        recyclerView.setLayoutManager(layoutManager);
        productAdapter=new ProductViewHolder(products);
        recyclerView.setAdapter(productAdapter);
        lvmanhinhchinh=(ListView) findViewById(R.id.lvmanhinhmenu);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerlay);
        fab=(FloatingActionButton) findViewById(R.id.fab);
        txttenuser=(TextView)findViewById(R.id.userprofile_name);
        civanhuser=(CircleImageView) findViewById(R.id.user_profileimage);
        loaiMenuArrayList=new ArrayList<>();
        loaiMenuArrayList.add(0,new LoaiMenu("Home",R.drawable.ic_menu_camera));
        loaiMenuArrayList.add(1,new LoaiMenu("Cart",R.drawable.cart));
        loaiMenuArrayList.add(2,new LoaiMenu("Search",R.drawable.orders));
        loaiMenuArrayList.add(3,new LoaiMenu("Category",R.drawable.category));
        loaiMenuArrayList.add(4,new LoaiMenu("Setting",R.drawable.ic_menu_gallery));
        loaiMenuArrayList.add(5,new LoaiMenu("Log out",R.drawable.logout));
        loaimenuAdapter=new LoaimenuAdapter(loaiMenuArrayList,HomeActivity2.this);
        lvmanhinhchinh.setAdapter(loaimenuAdapter);

    }
}