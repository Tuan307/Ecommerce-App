package com.example.firstfirebase.ViewHoler;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstfirebase.Activity.Admin.AdminHomeActivity;
import com.example.firstfirebase.Activity.Admin.MaintainActivity;
import com.example.firstfirebase.Activity.HomeActivity2;
import com.example.firstfirebase.Activity.User.ProdutDetailActivity;
import com.example.firstfirebase.ClassUse.Product;
import com.example.firstfirebase.Interface.ItemClickListener;
import com.example.firstfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductViewHolder2 extends RecyclerView.Adapter<ProductViewHolder2.ProductViewHolder3>{
    private ArrayList<Product> productArrayList;
    private ItemClickListener itemClickListener;
    public ProductViewHolder2(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ProductViewHolder2.ProductViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
        return new ProductViewHolder2.ProductViewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ProductViewHolder3 holder, int position) {
        Product product=productArrayList.get(position);
        if(product==null)
        {
            return;
        }
        holder.txtname.setText(product.getName());
        holder.txtdes.setText(product.getDescription());
        holder.txtprice.setText(product.getPrice());
        Picasso.get().load(product.getImage()).into(holder.imgpic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Approvement Confirm");
                builder.setMessage("Do you want to approve this product");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference databaseReference= FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                        databaseReference.child("Product").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot data:snapshot.getChildren())
                                {
                                    data.child("productstate").getRef().setValue("approved").addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), AdminHomeActivity.class));
                                        }
                                    });
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(productArrayList!=null)
        {
            return productArrayList.size();
        }
        return 0;
    }
    public class ProductViewHolder3 extends RecyclerView.ViewHolder {
        public TextView txtname, txtdes, txtprice;
        public ImageView imgpic;

        public ProductViewHolder3(@NonNull View itemView) {
            super(itemView);
            imgpic = (ImageView) itemView.findViewById(R.id.imgproductpic);
            txtname = (TextView) itemView.findViewById(R.id.txtpname);
            txtdes = (TextView) itemView.findViewById(R.id.txtproductdesciption);
            txtprice = (TextView) itemView.findViewById(R.id.txtpricep);
        }
    }
}
