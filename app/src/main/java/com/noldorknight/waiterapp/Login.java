package com.noldorknight.waiterapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.os.AsyncTask;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


public class Login extends Activity {

    SServer ss = new SServer();
    public Button btn;
    public ProgressBar progr;
    public EditText login;
    public EditText passw;
    String[] logincmd;
    Context context;
    MyTask mt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        context = getApplicationContext();
        btn = (Button) findViewById(R.id.trytogetin);
        progr = (ProgressBar) findViewById(R.id.progressBar);
        login = (EditText) findViewById(R.id.loginfld);
        passw = (EditText) findViewById(R.id.passfld);
        mt = new MyTask();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
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


    public void onClick(View view)
    {
        logincmd = new String[]{"CheckLogin", "Login" ,login.getText().toString(), "Password", passw.getText().toString()};
        mt = new MyTask();
        mt.execute(logincmd);
    }

    private void showresult()
    {
        if (mt == null) return;
        String[] result = null;
        try{
        result = mt.get();
    }
        catch (Exception ex)
        {System.out.println(ex);}

        if (result.length == 0){
            btn.setVisibility(View.VISIBLE);
            progr.setVisibility(View.INVISIBLE);
            Toast toast = Toast.makeText(context, "Неверный логин или пароль", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        Intent intent = new Intent(this, MainMenu.class);
        intent.putExtra("username",result[0]);
        intent.putExtra("userID",result[1]);
        login.clearComposingText();
        passw.clearComposingText();
        btn.setVisibility(View.VISIBLE);
        progr.setVisibility(View.INVISIBLE);
        startActivity(intent);
    }

    class MyTask extends AsyncTask<String[], String, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            btn.setVisibility(View.INVISIBLE);
            progr.setVisibility(View.VISIBLE);
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
            showresult();
            }
    }
}