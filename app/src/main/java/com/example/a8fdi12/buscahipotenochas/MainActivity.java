package com.example.a8fdi12.buscahipotenochas;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
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

        //Obtenemos el gridLayout
        l_tablero = (GridLayout) findViewById(R.id.l_tablero);

        //Creamos las opcion de principiante
        settings = new Settings(8,8,10);
        settings.setSettings(l_tablero);

        dibujarTablero();
    }

    private void dibujarTablero(){
        Button b;

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = calculateHeigth(size.y);

        for (int x = 0;x<settings.getCells();x++){
            b = new Button(this);
            b.setLayoutParams(new LinearLayout.LayoutParams(width/settings.getColumns(),height/settings.getRows()));
            //b.setLayoutParams(new LinearLayout.LayoutParams(100,100));
            b.setBackgroundResource(R.drawable.custom_button);
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
                CreateAlertDialogWithRadioButtonGroup();
                //System.out.println(getText(R.string.action_settings));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int calculateHeigth(int displayHeigth){
        // status bar height
        /*int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }*/

        // action bar height
        int actionBarHeight = 0;
        final TypedArray styledAttributes = getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize }
        );
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        // navigation bar height
        int navigationBarHeight = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        return displayHeigth - (actionBarHeight + navigationBarHeight) + 75;
    }

    private void CreateAlertDialogWithRadioButtonGroup(){
        AlertDialog alertDialog1;
        CharSequence[] values = {getString(R.string.s_level_1),getString(R.string.s_level_2),getString(R.string.s_level_3)};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.settings_title));
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                switch(item)
                {
                    case 0:
                        settings.changeSettings(8,8,10);
                        //Toast.makeText(MainActivity.this, "First Item Clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 1:
                        settings.changeSettings(12,12,30);
                        //Toast.makeText(MainActivity.this, "Second Item Clicked", Toast.LENGTH_LONG).show();
                        break;
                    case 2:
                        settings.changeSettings(16,16,60);
                        //Toast.makeText(MainActivity.this, "Third Item Clicked", Toast.LENGTH_LONG).show();
                        break;
                }

                try {
                    synchronized(this){
                        wait(200);
                    }
                }
                catch(InterruptedException ex){
                }

                settings.setSettings(l_tablero);
                dibujarTablero();
                dialog.dismiss();
            }
        });
        alertDialog1 = builder.create();
        alertDialog1.show();

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
