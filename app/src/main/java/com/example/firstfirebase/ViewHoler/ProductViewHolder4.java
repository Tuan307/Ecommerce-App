package com.example.firstfirebase.ViewHoler;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstfirebase.Activity.Admin.AdminHomeActivity;
import com.example.firstfirebase.ClassUse.Product;
import com.example.firstfirebase.ClassUse.Product1;
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

public class ProductViewHolder4 extends RecyclerView.Adapter<ProductViewHolder4.ProductViewHolder5>{
    private ArrayList<Product1> productArrayList;
    private ItemClickListener itemClickListener;
    public ProductViewHolder4(ArrayList<Product1> productArrayList) {
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public ProductViewHolder4.ProductViewHolder5 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_status_list,parent,false);
        return new ProductViewHolder4.ProductViewHolder5(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  ProductViewHolder5 holder, int position) {
        Product1 product=productArrayList.get(position);
        String id=product.getPid();
        if(product==null)
        {
            return;
        }
        holder.txtname.setText(product.getName());
        holder.txtdes.setText(product.getDescription());
        holder.txtprice.setText(product.getPrice());
        holder.txtstatus.setText("Status: "+product.getProductstate());
        Picasso.get().load(product.getImage()).into(holder.imgpic);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Delete Confirm");
                builder.setMessage("Do you want to delete this product");
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
                        databaseReference.child("Product").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(holder.itemView.getContext(),"Successfully deleted ",Toast.LENGTH_SHORT).show();
                                holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(),holder.itemView.getContext().getClass()));
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
    public class ProductViewHolder5 extends RecyclerView.ViewHolder {
        public TextView txtname, txtdes, txtprice,txtstatus;
        public ImageView imgpic;

        public ProductViewHolder5(@NonNull View itemView) {
            super(itemView);
            imgpic = (ImageView) itemView.findViewById(R.id.imgproductpic1);
            txtname = (TextView) itemView.findViewById(R.id.txtpname1);
            txtdes = (TextView) itemView.findViewById(R.id.txtproductdesciption1);
            txtprice = (TextView) itemView.findViewById(R.id.txtpricep1);
            txtstatus=(TextView) itemView.findViewById(R.id.txtstatusproduct);
        }
    }
}