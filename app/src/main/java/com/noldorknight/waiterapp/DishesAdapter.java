package com.noldorknight.waiterapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DishesAdapter extends BaseAdapter{
    Context ctx;
    LayoutInflater lInflater;
    List<String> DishID;
    List<String> Amount;
    List<String> Modifier;
    List<String> Price;
    List<ArrayList<String>> CategoriesList;

    DishesAdapter(Context context, List<ArrayList<String>> categories) {
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        CategoriesList=categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DishID = CategoriesList.get(0);
        Amount = CategoriesList.get(1);
        Modifier  = CategoriesList.get(2);
        Price  = CategoriesList.get(3);
        View view = convertView;
        if (view == null) {view = lInflater.inflate(R.layout.neworderadapter, parent, false);}
        ((TextView) view.findViewById(R.id.dishadaptertextview)).setText(DishID.get(position));
        ((TextView) view.findViewById(R.id.textView)).setText(Amount.get(position));
        ((TextView) view.findViewById(R.id.textView5)).setText(Integer.parseInt(Price.get(position))*(Integer.parseInt(Amount.get(position))));
        ((TextView) view.findViewById(R.id.textView4)).setText(Modifier.get(position));
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
    return position;
    }
}




