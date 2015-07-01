package com.noldorknight.waiterapp;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.Filter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filterable;
import android.widget.TextView;

public class OrderAdapter extends BaseAdapter implements Filterable {
    Context ctx;
    String[] nameandid;
    LayoutInflater lInflater;
    List<String> DishID;
    List<String> DishName;
    List<String> DishCategory;
    List<String> DishPrice;
    ValueFilter valueFilter;
    List<ArrayList<String>> DishesList;
    List<ArrayList<String>> filteredlist;

    OrderAdapter(Context context,  List<ArrayList<String>> dishes) {
        ctx = context;
        lInflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DishesList=dishes;
        filteredlist=dishes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DishID = filteredlist.get(0);
        DishName = filteredlist.get(1);
        DishCategory = filteredlist.get(2);
        DishPrice = filteredlist.get(3);
        View view = convertView;
        if (view == null) {view = lInflater.inflate(R.layout.orderadapter, parent, false);}
        ((TextView) view.findViewById(R.id.dishadaptertextview)).setText(DishName.get(position));
        return view;
    }

    @Override
    public int getCount() {
        return filteredlist.get(0).size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        nameandid = new String[3];
        nameandid[0]=filteredlist.get(0).get(position);
        nameandid[1]=filteredlist.get(1).get(position);
        nameandid[2]=filteredlist.get(3).get(position);
        return nameandid;
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
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
            String categoryfilter = constraint.toString();

             if(constraint.length() == 0){
                Result.values = DishesList;
                Result.count = DishesList.size();
                return Result;
            }
            final List<ArrayList<String>> list = DishesList;
            filteredlist=DishesList;
            List<ArrayList<String>> filteredTables = new ArrayList<ArrayList<String>>();
            filteredTables.add(new ArrayList<String>());
            filteredTables.add(new ArrayList<String>());
            filteredTables.add(new ArrayList<String>());
            filteredTables.add(new ArrayList<String>());
            String filterableCategory;
            System.out.println("ARRAY SIZE IS == " + list.get(0).size());
            for(int i = 0; i<list.get(0).size(); i++)
            {   filterableCategory = list.get(2).get(i);
                if(filterableCategory.equals(categoryfilter))
                {
                    filteredTables.get(0).add(list.get(0).get(i));
                    filteredTables.get(1).add(list.get(1).get(i));
                    filteredTables.get(2).add(list.get(2).get(i));
                    filteredTables.get(3).add(list.get(3).get(i));

                }
            }

            System.out.println("Filtered tables size inside == " + filteredTables.size());
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

