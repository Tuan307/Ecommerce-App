package com.example.firstfirebase.ViewHoler;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstfirebase.Activity.HomeActivity2;
import com.example.firstfirebase.Activity.Admin.MaintainActivity;
import com.example.firstfirebase.Activity.User.ProdutDetailActivity;
import com.example.firstfirebase.ClassUse.Product;
import com.example.firstfirebase.ClassUse.Product1;
import com.example.firstfirebase.Interface.ItemClickListener;
import com.example.firstfirebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductViewHolder extends RecyclerView.Adapter<ProductViewHolder.ProductViewHolder1> {

    private ArrayList<Product> productArrayList;
    private ItemClickListener itemClickListener;
    public ProductViewHolder(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }


    @NonNull
    @Override
    public ProductViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
        return new ProductViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder1 holder, int position) {
        Product product=productArrayList.get(position);
        if(product==null)
        {
            return;
        }
        holder.txtname.setText(product.getName());
        holder.txtdes.setText(product.getDescription());
        holder.txtprice.setText(product.getPrice());
        Picasso.get().load(product.getImage()).into(holder.imgpic);
        holder.imgpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //Toast.makeText(holder.itemView.getContext(),String.valueOf(holder.getAdapterPosition()),Toast.LENGTH_LONG).show();
                if(HomeActivity2.key.equals("Admin"))
                {
                    Intent inte=new Intent(holder.itemView.getContext(), MaintainActivity.class);
                    inte.putExtra("id",product.getPid());
                    holder.itemView.getContext().startActivity(inte);
                }
                else {
                    Intent intent = new Intent(holder.itemView.getContext(), ProdutDetailActivity.class);
                    intent.putExtra("pid", product.getPid());
                    intent.putExtra("sellerid",product.getSellerid());
                    holder.itemView.getContext().startActivity(intent);
                }
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
    public class ProductViewHolder1 extends RecyclerView.ViewHolder {
        public TextView txtname, txtdes, txtprice;
        public ImageView imgpic;

        public ProductViewHolder1(@NonNull View itemView) {
            super(itemView);
            imgpic = (ImageView) itemView.findViewById(R.id.imgproductpic);
            txtname = (TextView) itemView.findViewById(R.id.txtpname);
            txtdes = (TextView) itemView.findViewById(R.id.txtproductdesciption);
            txtprice = (TextView) itemView.findViewById(R.id.txtpricep);
        }
    }
}
