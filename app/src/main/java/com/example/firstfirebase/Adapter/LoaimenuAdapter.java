package com.example.firstfirebase.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.firstfirebase.ClassUse.LoaiMenu;
import com.example.firstfirebase.R;

import java.util.ArrayList;

public class LoaimenuAdapter extends BaseAdapter {
    ArrayList<LoaiMenu> arrayList;
    Context context;

    public LoaimenuAdapter(ArrayList<LoaiMenu> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    private class Viewholder{
        ImageView imghinhmenu;
        TextView txttenmenu;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Viewholder viewHolder=null;
        if(view==null)
        {
            viewHolder=new Viewholder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.dongmenu,null);
            viewHolder.imghinhmenu=view.findViewById(R.id.imghinhmenu);
            viewHolder.txttenmenu=view.findViewById(R.id.txttenmenu);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder= (Viewholder) view.getTag();
        }
        LoaiMenu loaiMenu= (LoaiMenu) getItem(i);
        viewHolder.imghinhmenu.setImageResource(loaiMenu.getHinhanhmenu());
        viewHolder.txttenmenu.setText(loaiMenu.getTenmenu().toString());
        return view;
    }
}
