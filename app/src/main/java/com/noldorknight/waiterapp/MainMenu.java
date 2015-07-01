package com.noldorknight.waiterapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu extends Activity {

    TextView user;
    String username;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        getActionBar().setTitle("e-Waiter. Главное меню");
        user = (TextView) findViewById(R.id.username);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        userid = intent.getStringExtra("userID");
        user.setText(username);

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

    public void exitaccount(View view){
        Intent intent = new Intent(this, Login.class);
        AsyncOrder AO = new AsyncOrder();
        AO.execute();
        startActivity(intent);
   }

    public void getactiveorders (View view)
    {
        Intent intent = new Intent(this, OrdersList.class);
        intent.putExtra("id", userid);
        startActivity(intent);
    }

    public void placevisitors(View view) {
        Intent intent = new Intent(this, GetEmptyTables.class);
        intent.putExtra("name", username);
        intent.putExtra("id", userid);
        startActivity(intent);
    }
    class AsyncOrder extends AsyncTask<Void,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params) {
            SServer ss = new SServer();
            ss.setserver();
            String[] answer = new String[]{"UnblockAccount","Username",username};
            System.out.println(username);
            ss.senddata(answer);
            return null;
        }
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
        }
    }
}


