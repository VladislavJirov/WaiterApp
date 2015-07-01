package com.noldorknight.waiterapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SeekBar;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class GetEmptyTables extends Activity implements SeekBar.OnSeekBarChangeListener {
    SServer ss;
    emtytables getempty;
    String[] cmd;
    String tid;
    String user;
    String userid;
    SeekBar seekb;
    TextView txtv;
    TextView usertv;
    Integer scount;
    TabHost tab;
    Context context;
    GridView ADGR;
    Integer hall;
    List<ArrayList<String>> listOLists;
    TableAdapter TAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_empty_tables);
        ss = new SServer();
        usertv = (TextView) findViewById(R.id.usernametv);
        context = getApplicationContext();
        Intent intent = getIntent();
        user =intent.getStringExtra("user");
        userid = intent.getStringExtra("id");
        usertv.setText(user);
        seekb = (SeekBar) findViewById(R.id.seekBar);
        seekb.setOnSeekBarChangeListener(this);
        ADGR = (GridView) findViewById(R.id.AdapterGridView);
        ADGR.setOnItemClickListener(new AdapterView.OnItemClickListener()
          {public void onItemClick(AdapterView<?> parent, View v,int position, long id)
               {
                   tid = String.valueOf(id);
                   cmd = new String[]{"CheckTable","TableID",tid};
                   getempty = new emtytables();
                   getempty.execute(cmd);
               }});
        txtv = (TextView) findViewById(R.id.sCount);
        tab = (TabHost) findViewById(R.id.tabHost);
        ////////////////////////////////
        tab.setup();
        TabHost.TabSpec spec = tab.newTabSpec("tag1");
        spec.setContent(R.id.AdapterGridView);
        spec.setIndicator("Восток");
        tab.addTab(spec);
        spec = tab.newTabSpec("tag2");
        spec.setContent(R.id.AdapterGridView);
        spec.setIndicator("Запад");
        tab.addTab(spec);
        spec = tab.newTabSpec("tag3");
        spec.setContent(R.id.AdapterGridView);
        spec.setIndicator("Летник");
        tab.addTab(spec);
        spec = tab.newTabSpec("tag4");
        spec.setContent(R.id.AdapterGridView);
        spec.setIndicator("VIP");
        tab.addTab(spec);


    tab.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
        hall = tab.getCurrentTab();
                for(int i=0;i<tab.getTabWidget().getChildCount();i++)
                {
                    tab.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#FFDBD6D1")); //unselected
                }
                tab.getTabWidget().getChildAt(tab.getCurrentTab()).setBackgroundColor(Color.parseColor("#FFFFFF"));
                updateadapter(scount, hall);}});
        getfreetables();
    }

    private void getfreetables()
    {
        cmd = new String[]{"GetEmptyTables"};
        getempty = new emtytables();
        getempty.execute(cmd);
    }

    private void datahandler()
    {
        if (getempty == null) return;
        String[] result = null;
        try{result = getempty.get();}
        catch (Exception ex){System.out.println(ex);}
        if (result[0].equals("false"))
        {   Toast toast = Toast.makeText(context, "Result is empty", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        if (result.length == 1)
        {
            Intent intent = new Intent(context, NewOrder.class);
            intent.putExtra("tableid",tid);
            intent.putExtra("user",user);
            intent.putExtra("userid",userid);
            startActivity(intent);
        }
        else{
        cmd = new String[result.length];
        System.arraycopy(result,0,cmd,0,result.length);
        hall = 0;
        scount = 1;
        showdata();}
    }

    private void showdata()
    {
        listOLists = new ArrayList<ArrayList<String>>();
        ArrayList<String>HallID = new ArrayList<String>();
        ArrayList<String> TableID = new ArrayList<String>();
        ArrayList<String> SCount= new ArrayList<String>();
        ArrayList<String> HallNo= new ArrayList<String>();
        ArrayList<String> HallName= new ArrayList<String>();
        for (int i = 0; i<cmd.length;i++)
        {
            HallID.add(cmd[i]);
            SCount.add(cmd[i+1]);
            TableID.add(cmd[i+2]);
            HallNo.add(cmd[i+3]);
            HallName.add(cmd[i+4]);
            i= i+4;
        }
        listOLists.add(HallID);
        listOLists.add(SCount);
        listOLists.add(TableID);
        listOLists.add(HallNo);
        listOLists.add(HallName);
        TAdapter = new TableAdapter(this, listOLists);
        ADGR.setAdapter(TAdapter);
        ADGR.setNumColumns(GridView.AUTO_FIT);
        ADGR.setColumnWidth(174);
        ADGR.setVerticalSpacing(10);
        ADGR.setHorizontalSpacing(10);
        tab.setCurrentTab(1);
        tab.setCurrentTab(0);
    }

    private void updateadapter(int scount, int hall)
    {

        TAdapter.getFilter().filter(String.valueOf(scount)+"*"+String.valueOf(hall+1));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.get_empty_tables, menu);
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

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        scount = Integer.valueOf(seekb.getProgress());
        txtv.setText("Количество мест > " + scount);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        updateadapter(scount, hall);
    }

    public void refreshtables(View view) {
        getfreetables();
    }

    class emtytables extends AsyncTask<String[], String, String[]> {

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






