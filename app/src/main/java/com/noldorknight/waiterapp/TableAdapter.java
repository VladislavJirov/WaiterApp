package com.noldorknight.waiterapp;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Filter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

public class TableAdapter extends BaseAdapter implements Filterable {
    Context ctx;
    LayoutInflater lInflater;
    List<String> HallID;
    List<String> TableID;
    List<String> SCount;
    List<String> HallNo;
    List<String> HallName;
    ValueFilter valueFilter;
    List<ArrayList<String>> TablesList;
    List<ArrayList<String>> filteredlist;

    TableAdapter(Context context,  List<ArrayList<String>> tables) {
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        TablesList=tables;
        filteredlist=tables;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        HallID = filteredlist.get(0);
        SCount = filteredlist.get(1);
        TableID = filteredlist.get(2);
        HallNo = filteredlist.get(3);
        HallName = filteredlist.get(4);
        View view = convertView;
        if (view == null) {view = lInflater.inflate(R.layout.adapter, parent, false);}
        if (Integer.parseInt(HallID.get(position)) >=4)
        {((TextView) view.findViewById(R.id.TABLEID)).setText(HallName.get(position));}
        else
        {((TextView) view.findViewById(R.id.TABLEID)).setText("Столик №"+TableID.get(position));}
        ((TextView) view.findViewById(R.id.SCOUNT)).setText("Кол-во мест = " + SCount.get(position));
        ((ImageView) view.findViewById(R.id.imageView)).setImageResource(R.drawable.tables);
        return view;
    }

    @Override
    public int getCount() {
        return filteredlist.get(2).size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return filteredlist.get(2).get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return Long.parseLong(filteredlist.get(2).get(position));
    }

   @Override
    public Filter getFilter() {
       if (valueFilter == null) {
          valueFilter = new ValueFilter();
          }
       return valueFilter;
    }

 private class ValueFilter extends Filter {
     @Override
     protected FilterResults performFiltering(CharSequence constraint) {
         FilterResults Result = new FilterResults();
         String filterString = constraint.toString();
         String scount = filterString.substring(0,filterString.indexOf("*"));
         String hallid = filterString.substring(filterString.indexOf("*")+1,filterString.length());

         if(constraint.length() == 0){
             Result.values = TablesList;
             Result.count = TablesList.size();
             return Result;
         }
         final List<ArrayList<String>> list = TablesList;
         filteredlist=TablesList;
         List<ArrayList<String>> filteredTables = new ArrayList<ArrayList<String>>();
         for (int j=0; j<5; j++){
         filteredTables.add(new ArrayList<String>());}
         String filterableHalls;
         String filterableSeats;
         System.out.println("ARRAY SIZE IS == " + list.get(0).size());
         for(int i = 0; i<list.get(0).size(); i++)
         {   filterableHalls = list.get(0).get(i);
             filterableSeats = list.get(1).get(i);

             if(filterableHalls.equals(hallid)&&Integer.parseInt(filterableSeats) >= Integer.parseInt(scount))
             {
                 for (int j=0; j<5; j++)
                     filteredTables.get(j).add(list.get(j).get(i));
             }

             else{if (Integer.parseInt(hallid) >=4)
             if(Integer.parseInt(filterableHalls)>= 5 && Integer.parseInt(filterableSeats) >= Integer.parseInt(scount))
             {
                 for (int j=0; j<5; j++)
                     filteredTables.get(j).add(list.get(j).get(i));
             }
             }

         }
         System.out.println("Filtered tables size inside == " + filteredTables.get(0).size());
         Result.values = filteredTables;
         Result.count = filteredTables.size();
         return Result;
     }

     @SuppressWarnings("unchecked")
     @Override

     protected void publishResults(CharSequence constraint, FilterResults results) {
         if (results.count == 0)
         {notifyDataSetInvalidated();}
         else{
         filteredlist = (ArrayList<ArrayList<String>>) results.values;
         notifyDataSetChanged();}
     }
}
}

