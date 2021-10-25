package com.example.firstfirebase.ViewHoler;

import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstfirebase.Activity.CartActivity;
import com.example.firstfirebase.Activity.User.ProdutDetailActivity;
import com.example.firstfirebase.ClassUse.Cart;
import com.example.firstfirebase.Prevalent.Prevalent;
import com.example.firstfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartViewHolder extends RecyclerView.Adapter<CartViewHolder.CartViewHolder1> {

    private ArrayList<Cart> cartArrayList;

    public CartViewHolder(ArrayList<Cart> cartArrayList) {
        this.cartArrayList = cartArrayList;
    }

    @NonNull
    @Override
    public CartViewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new CartViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder1 holder, int position) {
        Cart cart=cartArrayList.get(position);
        if(cart==null)
        {
            return;
        }

        String gia=cart.getPprice().replace("$","");
        holder.txtten.setText(cart.getPname());
        int proce=Integer.parseInt(gia)*Integer.parseInt(cart.getPquan());
        holder.texgia.setText(String.valueOf(proce));
        holder.txtsoluong.setText(cart.getPquan());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence options[]=new CharSequence[]
                        {
                            "Edit",
                            "Delete"
                        };
                AlertDialog.Builder aBuilder=new AlertDialog.Builder(holder.itemView.getContext());
                aBuilder.setTitle("Cart Options");
                aBuilder.setItems(options,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(i==0)
                        {
                            Intent intent=new Intent(holder.itemView.getContext(), ProdutDetailActivity.class);
                            intent.putExtra("pid",cart.getPid());
                            holder.itemView.getContext().startActivity(intent);
                        }
                        if(i==1)
                        {
                            DatabaseReference databaseReference= FirebaseDatabase.
                                    getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").
                                    getReference().child("CartList").child("UserView").child(Prevalent.currentOnlineUser.getPhone()).child("Product").child(cart.getPid());
                            databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(holder.itemView.getContext(),"Successfully removed item from cart",Toast.LENGTH_SHORT).show();
                                        cartArrayList.remove(holder.getAdapterPosition());
                                        holder.getBindingAdapter().notifyDataSetChanged();
                                    }
                                }
                            });
                        }
                    }
                });
                aBuilder.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        if(cartArrayList!=null)
        {
            return cartArrayList.size();
        }
        return 0;
    }

    public class CartViewHolder1 extends RecyclerView.ViewHolder {
        TextView txtten,texgia,txtsoluong;
        public CartViewHolder1(@NonNull View itemView) {
            super(itemView);
            txtten=(TextView) itemView.findViewById(R.id.cartitemname);
            txtsoluong=(TextView) itemView.findViewById(R.id.cartitemquan);
            texgia=(TextView) itemView.findViewById(R.id.cartitemprice);
        }
    }
}
