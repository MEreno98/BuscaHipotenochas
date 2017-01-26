package com.example.a8fdi12.buscahipotenochas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //noinspection SimplifiableIfStatement
        switch (item.getItemId()){
            case R.id.action_changehipo:
                //Falta el metodo
                System.out.println(getText(R.string.action_change));
                return true;
            case R.id.action_instrucciones:
                showIntrucciones();
                System.out.println(getText(R.string.action_instrucciones));
                return true;
            case R.id.action_start:
                //Falta el metodo
                System.out.println(getText(R.string.action_start));
                return true;
            case R.id.action_settings:
                //Falta el metodo
                System.out.println(getText(R.string.action_settings));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showIntrucciones(){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);

        dialogo1.setTitle(getText(R.string.action_instrucciones));
        dialogo1.setMessage(getText(R.string.dialog_instrucciones));
        dialogo1.setNeutralButton(getText(R.string.dialog_instrucciones_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
            }
        });

        dialogo1.show();
    }


}
