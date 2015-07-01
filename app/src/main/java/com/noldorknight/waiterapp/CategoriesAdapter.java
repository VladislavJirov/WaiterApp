package com.noldorknight.waiterapp;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CategoriesAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    List<String> CategoryID;
    List<String> CategoryName;
    List<ArrayList<String>> CategoriesList;

    CategoriesAdapter(Context context,  List<ArrayList<String>> categories) {
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CategoriesList=categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryID = CategoriesList.get(1);
        CategoryName = CategoriesList.get(0);
        View view = convertView;
        if (view == null) {view = lInflater.inflate(R.layout.orderadapter, parent, false);}
        ((TextView) view.findViewById(R.id.dishadaptertextview)).setText(CategoryName.get(position));
        return view;
    }

    @Override
    public int getCount() {
        return CategoriesList.get(0).size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return CategoriesList.get(0).get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return Long.parseLong(CategoriesList.get(1).get(position));
    }
}




