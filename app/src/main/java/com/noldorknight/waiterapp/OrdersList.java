package com.noldorknight.waiterapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class OrdersList extends Activity {

    SServer ss;
    String[] cmd;
    exordlist exord;
    Intent intent;
    Context context;
    String user;
    ListView list;
    ListView contains;
    ArrayList<ArrayList<String>> dishes;
    ArrayList<ArrayList<String>> orders;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = intent.getStringExtra("id");
        setContentView(R.layout.activity_orders_list);
        cmd = new String[]{"ReturnActiveOrders", user};
        exord = new exordlist();
        exord.execute(cmd);
        list = (ListView) findViewById(R.id.AOlist);
        contains = (ListView) findViewById(R.id.AOcontains);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                }});

        contains.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

            }});
    }

    private void datahandler()
    {
        if (exord == null) return;
        String[] result = null;
        try{result = exord.get();}
        catch (Exception ex){System.out.println(ex);}
        if (result[0].equals("false"))
        {   Toast toast = Toast.makeText(context, "Result is empty", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
    }


    public void showdata()
    {
        dishes = new ArrayList<ArrayList<String>>();
        orders = new ArrayList<ArrayList<String>>();
        ArrayList<String>OrderID = new ArrayList<String>();
        ArrayList<String>TableID = new ArrayList<String>();
        ArrayList<String>Dish = new ArrayList<String>();
        ArrayList<String>Amount = new ArrayList<String>();
        ArrayList<String>Price = new ArrayList<String>();
         for (int i = 0; i<cmd.length;i++)
        {
            OrderID.add(cmd[i]);
            TableID.add(cmd[i+1]);
            Dish.add(cmd[i+2]);
            Amount.add(cmd[i+3]);
            Price.add(cmd[i+4]);
            i=i+4;
        }
        orders.add(OrderID);
        orders.add(TableID);
        dishes.add(OrderID);
        dishes.add(Dish);
        dishes.add(Amount);
        dishes.add(Price);

  //      DAdapter = new DishesAdapter(this, listOfOrders);
 //       OAdapter = new OrderAdapter(this, listOfDishes);
 //       CAdapter = new CategoriesAdapter(this, listOfCategories);
 //       dishes.setAdapter(OAdapter);
  //      categories.setAdapter(CAdapter);
  //      orders.setAdapter(DAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.orders_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class exordlist extends AsyncTask<String[], String, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String[] doInBackground(String[]... cmd) {

            ss.setserver();
            String[] answer;
            answer = ss.senddata(cmd[0]);
            return answer;
        }


        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
            System.out.println(result);
            datahandler();
        }
    }
}
