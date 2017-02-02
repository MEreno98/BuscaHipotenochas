package com.example.a8fdi12.buscahipotenochas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    private Settings settings;
    private GridLayout l_tablero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        l_tablero = (GridLayout) findViewById(R.id.l_tablero);

        settings = new Settings(16,16,60);
        settings.setSettings(l_tablero);

        dibujarTablero();
    }

    private void dibujarTablero(){
        Button b;
        System.out.println(l_tablero.getLayoutParams().width);



        for (int x = 0;x<settings.getCells();x++){
            b = new Button(this);
            b.setLayoutParams(new LinearLayout.LayoutParams(100,100));
            b.setText(Integer.toString(x+1));
            b.setId(x);
            l_tablero.addView(b,x);
        }
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
                //Mostrar instrucciones
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
