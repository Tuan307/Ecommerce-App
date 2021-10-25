package com.example.firstfirebase.ViewHoler;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstfirebase.Activity.User.ProdutDetailActivity;
import com.example.firstfirebase.ClassUse.Product;
import com.example.firstfirebase.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchViewHolder extends RecyclerView.Adapter<SearchViewHolder.SearchViewHolder1> {
    private ArrayList<Product> productArrayList;

    public SearchViewHolder(ArrayList<Product> productArrayList) {
        this.productArrayList = productArrayList;
    }

    @NonNull
    @Override
    public SearchViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item_layout,parent,false);
        return new SearchViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder1 holder, int position) {
        Product product1=productArrayList.get(position);
        if(product1==null)
        {
            return ;
        }
        holder.txtname1.setText(product1.getName());
        holder.txtdes1.setText(product1.getDescription());
        holder.txtprice1.setText(product1.getPrice());
        Picasso.get().load(product1.getImage()).into(holder.imgpic1);
        holder.imgpic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(holder.itemView.getContext(),String.valueOf(holder.getAdapterPosition()),Toast.LENGTH_LONG).show();

                Intent intent=new Intent(holder.itemView.getContext(), ProdutDetailActivity.class);
                intent.putExtra("pid",product1.getPid());
                holder.itemView.getContext().startActivity(intent);
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

    public class SearchViewHolder1 extends RecyclerView.ViewHolder {
        public TextView txtname1, txtdes1, txtprice1;
        public ImageView imgpic1;
        public SearchViewHolder1(@NonNull View itemView) {
            super(itemView);
            imgpic1 = (ImageView) itemView.findViewById(R.id.imgproductpic);
            txtname1 = (TextView) itemView.findViewById(R.id.txtpname);
            txtdes1 = (TextView) itemView.findViewById(R.id.txtproductdesciption);
            txtprice1 = (TextView) itemView.findViewById(R.id.txtpricep);
        }
    }
}
