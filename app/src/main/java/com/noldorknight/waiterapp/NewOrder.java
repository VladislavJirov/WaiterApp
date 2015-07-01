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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class NewOrder extends Activity {
    TextView tableid;
    TextView user;
    String dishid;
    String[] dishname;
    AsyncOrder AO;
    CheckBox modifier;
    ListView orders;
    int itemid;
    ListView categories;
    ListView dishes;
    String[] cmd;
    Context context;
    SServer ss;
    OrderAdapter OAdapter;
    CategoriesAdapter CAdapter;
    DishesAdapter DAdapter;
    List<ArrayList<String>> listOfOrders;
    List<ArrayList<String>> listOfDishes;
    List<ArrayList<String>> listOfCategories;
    String newOrderCmd;
    String username;
    String table;
    String userid;
    Button x1;
    Button x2;
    Button x3;
    Button x4;
    Button Delall;
    Button Delone;
    Button Done;
    TextView choosen;
    TextView totalPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        Intent intent = getIntent();
        username =(intent.getStringExtra("username"));
        table=intent.getStringExtra("tableid");
        userid=intent.getStringExtra("userid");
        tableid = (TextView) findViewById(R.id.OrdTable);
        user = (TextView) findViewById(R.id.OrdUsername);
        choosen = (TextView) findViewById(R.id.textView2);
        tableid.setText("Заказ для столика № "+ table);
        user.setText(username);
        ss = new SServer();
        modifier = (CheckBox)findViewById(R.id.OrdModif);
        x1 = (Button) findViewById(R.id.OrdAddx1);
        x2 = (Button) findViewById(R.id.OrdAddx2);
        x3 = (Button) findViewById(R.id.OrdAddx3);
        x4 = (Button) findViewById(R.id.OrdAddx4);
        Done = (Button) findViewById(R.id.OrdDone);
        totalPrice = (TextView)findViewById(R.id.totalPrice);
        Delall= (Button) findViewById(R.id.OrdRemoveAll);
        Delone= (Button) findViewById(R.id.OrdRemoveOne);
        x1.setOnClickListener(onclick);
        x2.setOnClickListener(onclick);
        x3.setOnClickListener(onclick);
        x4.setOnClickListener(onclick);
        Delall.setOnClickListener(onclick);
        Delone.setOnClickListener(onclick);
        Done.setOnClickListener(onclick);
        cmd = new String[]{"DishesList"};
        AO = new AsyncOrder();
        AO.execute(cmd);
        dishes = (ListView) findViewById(R.id.OrdDishes);
        categories = (ListView)findViewById(R.id.OrdCategories);
        orders = (ListView)findViewById(R.id.OrdOrder);
        dishes.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {public void onItemClick(AdapterView<?> parent, View v,int position, long id)
            {
                System.out.println("POSITION >>>>>"  + position);
                System.out.println("ID >>>>>"  + id);
                dishid = String.valueOf(id);
                dishname = (String[])OAdapter.getItem(position);
                System.out.println("OBJECTIS >>>>>"  + dishname[0]+ dishname[1]);
                choosen.setText(dishname[1]);


            }});

        categories.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {public void onItemClick(AdapterView<?> parent, View v,int position, long id)
            {
                System.out.println("POSITION >>>>>"  + position);
                System.out.println("ID >>>>>"  + id);
                OAdapter.getFilter().filter(String.valueOf(id));
            }});

        orders.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {public void onItemClick(AdapterView<?> parent, View v,int position, long id)
            {
                System.out.println("POSITION >>>>>"  + position);
                itemid = position;
                }});

    }

    private void datahandler()
    {
        if (AO == null) return;
        String[] result = null;
        try{result = AO.get();}
        catch (Exception ex){System.out.println(ex);}
        if (result[0].equals("false"))
        {   Toast toast = Toast.makeText(context, "DON'T TRY TO MESS WITH ME, YOU FILTHY MOTHERFUCKER!!!", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        cmd = new String[result.length];
        System.arraycopy(result,0,cmd,0,result.length);
        showdata();
    }

    OnClickListener onclick = new OnClickListener() {
        @Override
        public void onClick(View view) {
            int sum=0;
            switch (view.getId()) {
                case R.id.OrdAddx1:
                    if (dishname[1].isEmpty())
                    {return;}
                    if (listOfOrders.get(0).contains(dishname[1]))  //сравниваем названия
                {
                    int indexofitem = listOfOrders.get(0).indexOf(dishname[1]);
                    listOfOrders.get(1).set(indexofitem, String.valueOf(Integer.parseInt(listOfOrders.get(1).get(indexofitem))+1));
                    System.out.println(Integer.parseInt(dishname[2]));
                    System.out.println(Integer.parseInt(dishname[2]));
                    listOfOrders.get(3).set(indexofitem, String.valueOf(Integer.parseInt(listOfOrders.get(3).get(indexofitem))+(Integer.parseInt(dishname[2]))));
                    DAdapter.notifyDataSetChanged();
                }
                    else {
                        listOfOrders.get(0).add(dishname[1]);
                        listOfOrders.get(1).add("1");
                        if (modifier.isChecked()) {             }
                        listOfOrders.get(2).add("");
                        listOfOrders.get(3).add(String.valueOf(Integer.parseInt(dishname[2])*1));
                        DAdapter.notifyDataSetChanged();
                    }

                    for (int i=0; i<listOfOrders.get(3).size(); i++) {
                        sum = sum + (Integer.parseInt(listOfOrders.get(3).get(i)));
                    }
                    totalPrice.setText("Итого: "+String.valueOf(sum));
                    break;
                case R.id.OrdAddx2:
                    if (dishname[1].isEmpty())
                    {return;}
                    if (listOfOrders.get(0).contains(dishname[1]))  //сравниваем названия
                    {
                        int indexofitem = listOfOrders.get(0).indexOf(dishname[1]);
                        listOfOrders.get(1).set(indexofitem, String.valueOf(Integer.parseInt(listOfOrders.get(1).get(indexofitem))+2));
                        listOfOrders.get(3).set(indexofitem, String.valueOf(Integer.parseInt(listOfOrders.get(3).get(indexofitem))+(Integer.parseInt(dishname[2])*2)));
                        DAdapter.notifyDataSetChanged();
                    }
                    else {
                        listOfOrders.get(0).add(dishname[1]);
                        listOfOrders.get(1).add("2");
                        if (modifier.isChecked()) {               }
                        listOfOrders.get(2).add("");
                        listOfOrders.get(3).add(String.valueOf(Integer.parseInt(dishname[2])*2));
                        DAdapter.notifyDataSetChanged();
                    }

                    for (int i=0; i<listOfOrders.get(3).size(); i++) {
                        sum = sum + (Integer.parseInt(listOfOrders.get(3).get(i)));
                    }
                    totalPrice.setText("Итого: "+String.valueOf(sum));
                    break;
                case R.id.OrdAddx3:
                    if (dishname[1].isEmpty())
                    {return;}
                    if (listOfOrders.get(0).contains(dishname[1]))  //сравниваем названия
                    {
                        int indexofitem = listOfOrders.get(0).indexOf(dishname[1]);
                        listOfOrders.get(1).set(indexofitem, String.valueOf(Integer.parseInt(listOfOrders.get(1).get(indexofitem))+3));
                        listOfOrders.get(3).set(indexofitem, String.valueOf(Integer.parseInt(listOfOrders.get(3).get(indexofitem))+(Integer.parseInt(dishname[2])*3)));
                        DAdapter.notifyDataSetChanged();
                    }
                    else {
                        listOfOrders.get(0).add(dishname[1]);
                        listOfOrders.get(1).add("3");
                        if (modifier.isChecked()) {               }
                        listOfOrders.get(2).add("");
                        listOfOrders.get(3).add(String.valueOf(Integer.parseInt(dishname[2])*3));
                        DAdapter.notifyDataSetChanged();}

                        for (int i=0; i<listOfOrders.get(3).size(); i++) {
                            sum = sum + (Integer.parseInt(listOfOrders.get(3).get(i)));
                    }
                    totalPrice.setText("Итого: "+String.valueOf(sum));
                    break;
                case R.id.OrdAddx4:
                    if (dishname[1].isEmpty())
                    {return;}
                    if (listOfOrders.get(0).contains(dishname[1]))  //сравниваем названия
                    {
                        int indexofitem = listOfOrders.get(0).indexOf(dishname[1]);
                        listOfOrders.get(1).set(indexofitem, String.valueOf(Integer.parseInt(listOfOrders.get(1).get(indexofitem))+4));
                        listOfOrders.get(3).set(indexofitem, String.valueOf(Integer.parseInt(listOfOrders.get(3).get(indexofitem))+(Integer.parseInt(dishname[2])*4)));
                        DAdapter.notifyDataSetChanged();
                    }
                    else {
                        listOfOrders.get(0).add(dishname[1]);
                        listOfOrders.get(1).add("4");
                        listOfOrders.get(2).add("");
                        listOfOrders.get(3).add(String.valueOf(Integer.parseInt(dishname[2])*4));
                        DAdapter.notifyDataSetChanged();
                        }
                    for (int i=0; i<listOfOrders.get(3).size(); i++) {
                        sum = sum + (Integer.parseInt(listOfOrders.get(3).get(i)));
                        }
                    totalPrice.setText("Итого: "+String.valueOf(sum));

                    break;

                case R.id.OrdDone:
                    System.out.println("We're here");
                    newOrderCmd = new String();
                    for (int i=0; i<listOfOrders.get(0).size();i++)
                    {
                        String dish = listOfOrders.get(0).get(i);
                        if (i==0)
                        {newOrderCmd=listOfOrders.get(1).get(i) +"!"+ listOfDishes.get(0).get((listOfDishes.get(1).indexOf(dish)))+"!";
                        }
                        else{
                        newOrderCmd=newOrderCmd + listOfOrders.get(1).get(i) +"!"+ listOfDishes.get(0).get((listOfDishes.get(1).indexOf(dish)))+"!";
                    }}
                    cmd = new String[]{"OrderInserting","TableID",table,"Waiter",userid,"OrderArray",newOrderCmd};
                    AO = new AsyncOrder();
                    AO.execute(cmd);
                    mainmenu();
                    break;

                case R.id.OrdRemoveAll:
                    if (listOfOrders.get(0).isEmpty())
                    {return;}
                    for (int j=0; j<4; j++)
                    {listOfOrders.get(j).clear();}
                    DAdapter.notifyDataSetChanged();
                    break;
                case R.id.OrdRemoveOne:
                    if (listOfOrders.get(0).isEmpty())
                    {return;}
                    if (Integer.parseInt(listOfOrders.get(1).get(itemid))==1)
                    {
                        for (int j=0; j<4; j++)
                        {listOfOrders.get(j).remove(itemid);}
                    DAdapter.notifyDataSetChanged();
                    }
                    else
                    {
                        listOfOrders.get(3).set(itemid, String.valueOf(Integer.parseInt(listOfOrders.get(3).get(itemid))-(Integer.parseInt(listOfOrders.get(3).get(itemid))/Integer.parseInt(listOfOrders.get(1).get(itemid)))));
                        listOfOrders.get(1).set(itemid, String.valueOf(Integer.parseInt(listOfOrders.get(1).get(itemid))-1));
                        DAdapter.notifyDataSetChanged();
                    }

                    break;
                case R.id.OrdChangeTable:
                    cmd = new String[]{"UnblockTable","TableID",table};
                    AO = new AsyncOrder();
                    AO.execute(cmd);
                    tableslist();
                    break;
            }
        }
    };

    private void mainmenu()
    {
       Intent intent = new Intent(this,MainMenu.class);

        startActivity(intent);
    }

    private void tableslist()
    {
        Intent intent = new Intent(this,GetEmptyTables.class);
        intent.putExtra("user", username);
        intent.putExtra("id", userid);
        startActivity(intent);
    }

     public void showdata()
    {

        listOfDishes = new ArrayList<ArrayList<String>>();
        listOfCategories = new ArrayList<ArrayList<String>>();
        listOfOrders = new ArrayList<ArrayList<String>>();
        ArrayList<String>DishID = new ArrayList<String>();
        ArrayList<String>DishName = new ArrayList<String>();
        ArrayList<String>DishCategory = new ArrayList<String>();
        ArrayList<String>DishPrice = new ArrayList<String>();
        ArrayList<String>CategoryID = new ArrayList<String>();
        ArrayList<String>CategoryName = new ArrayList<String>();
        for (int i = 0; i<cmd.length;i++)
        {
            DishID.add(cmd[i]);
            DishName.add(cmd[i+1]);
            DishCategory.add(cmd[i+2]);
            DishPrice.add(cmd[i+5]);
            if (CategoryName.contains(cmd[i+3])){}
            else{ CategoryName.add(cmd[i+3]);
                CategoryID.add(cmd[i+4]);}
            i=i+5;
        }
        listOfDishes.add(DishID);
        listOfDishes.add(DishName);
        listOfDishes.add(DishCategory);
        listOfDishes.add(DishPrice);
        listOfCategories.add(CategoryName);
        listOfCategories.add(CategoryID);
        listOfOrders.add(new ArrayList<String>());
        listOfOrders.add(new ArrayList<String>());
        listOfOrders.add(new ArrayList<String>());
        listOfOrders.add(new ArrayList<String>());
        DAdapter = new DishesAdapter(this, listOfOrders);
        OAdapter = new OrderAdapter(this, listOfDishes);
        CAdapter = new CategoriesAdapter(this, listOfCategories);
        dishes.setAdapter(OAdapter);
        categories.setAdapter(CAdapter);
        orders.setAdapter(DAdapter);

   }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.new_order, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    class AsyncOrder extends AsyncTask<String[], String, String[]> {

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
            datahandler();
        }
    }

}


