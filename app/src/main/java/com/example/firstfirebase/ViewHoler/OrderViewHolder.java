package com.example.firstfirebase.ViewHoler;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstfirebase.Activity.Admin.AdminCheckOrderActivity;
import com.example.firstfirebase.Activity.Admin.OrderProductsActivity;
import com.example.firstfirebase.ClassUse.Order;
import com.example.firstfirebase.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderViewHolder extends RecyclerView.Adapter<OrderViewHolder.OrderViewHoler1> {
    ArrayList<Order> orderArrayList;

    public OrderViewHolder(ArrayList<Order> orderArrayList) {
        this.orderArrayList = orderArrayList;
    }

    @NonNull
    @Override
    public OrderViewHoler1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_order_item,parent,false);
        return new OrderViewHoler1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHoler1 holder,int position) {
        Order order=orderArrayList.get(position);
        if(order==null)
        {
            return;
        }
        holder.txtorderdate.setText("Order at:Date: "+order.getOdate()+" Time: "+order.getOtime());
        holder.txtordertotal.setText("Total Amount: "+order.getOtotal()+"$");
        holder.txtorderaddress.setText("Shipping address: "+order.getOaddress()+" "+order.getOcity());
        holder.txtordername.setText("Name: "+order.getOname());
        holder.txtorderphone.setText("Tel: "+order.getOphone());
        String id="";
        holder.btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid=order.getOdate()+order.getOtime();
                Intent intent=new Intent(holder.itemView.getContext(), OrderProductsActivity.class);
                intent.putExtra("phone",order.getOphone()+order.getOtime());
                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.itemView.getContext());
                builder.setTitle("Delivery status");
                builder.setMessage("Have you shipped this product ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DatabaseReference database=FirebaseDatabase.getInstance("https://my-demo-project-dfedb-default-rtdb.asia-southeast1.firebasedatabase.app").getReference();
                        database.child("Orders").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot data:snapshot.getChildren())
                                {
                                    for(DataSnapshot dataSnapshot:data.getChildren())
                                    {
                                        if(dataSnapshot.getKey().equals(order.getOphone()+order.getOtime()))
                                        {
                                            dataSnapshot.getRef().removeValue();
                                            holder.itemView.getContext().startActivity(new Intent(holder.itemView.getContext(), AdminCheckOrderActivity.class));

                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {

        if(orderArrayList!=null)
        {
            return orderArrayList.size();
        }
        return 0;
    }

    public class OrderViewHoler1 extends RecyclerView.ViewHolder {
        TextView txtordername,txtorderphone,txtorderaddress,txtordertotal,txtorderdate;
        Button btnshow;
        public OrderViewHoler1(@NonNull View itemView) {
            super(itemView);
            txtordername=(TextView) itemView.findViewById(R.id.txtorderusername);
            txtorderphone=(TextView) itemView.findViewById(R.id.txtorderphonenumber);
            txtorderaddress=(TextView) itemView.findViewById(R.id.txtorderaddress);
            txtordertotal=(TextView) itemView.findViewById(R.id.txtordertotalprice);
            txtorderdate=(TextView) itemView.findViewById(R.id.txtorderdate);
            btnshow=(Button) itemView.findViewById(R.id.btnshoworder);
        }
    }
}
