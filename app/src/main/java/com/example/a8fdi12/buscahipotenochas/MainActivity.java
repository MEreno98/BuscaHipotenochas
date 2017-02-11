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
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity{

    private Tablero tablero;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Obtenemos el gridLayout
        GridLayout gl_tabelo = (GridLayout) findViewById(R.id.l_tablero);

        //Creamos las opcion de principiante y establecemos las opciones
        Settings settings = new Settings(8,8,10);
        settings.setSettings(gl_tabelo);

        //Creamos el tablero
        tablero = new Tablero(settings);
        tablero.crearTablero();
        dibujar(gl_tabelo);
    }

    public void dibujar(GridLayout gl_tablero){
        Button btn;
        ImageButton img_btn;

        //Obtener el tamaño de la pantalla
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = this.calcularHeight(size.y);

        //Obtener opciones
        Settings settings = tablero.getSettings();

        int z = 0;

        for(int x = 0; x < settings.getColumns(); x++){
            for(int y = 0; y < settings.getRows(); y++){

                Casilla casilla = tablero.getCasilla(x,y);

                if (casilla.getMina() == 9){
                    img_btn = new ImageButton(this);

                    img_btn.setLayoutParams(new LinearLayout.LayoutParams(width/settings.getColumns(),height/settings.getRows()));
                    img_btn.setBackgroundResource(R.drawable.custom_button);

                    img_btn.setImageResource(R.mipmap.bomb);
                    img_btn.setId(x);

                    gl_tablero.addView(img_btn,z);
                }else{
                    btn = new Button(this);

                    btn.setLayoutParams(new LinearLayout.LayoutParams(width/settings.getColumns(),height/settings.getRows()));
                    btn.setBackgroundResource(R.drawable.custom_button);

                    btn.setText(Integer.toString(casilla.getMina()));
                    btn.setId(x);

                    gl_tablero.addView(btn,z);
                }

                z++;
            }

        }

    }

    private int calcularHeight(int displayHeight){
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

        return displayHeight - (actionBarHeight + navigationBarHeight) + 75;
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
                //System.out.println(getText(R.string.action_instrucciones));
                return true;
            case R.id.action_start:
                //Juego nuevo
                newGame();
                //System.out.println(getText(R.string.action_start));
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

    private void CreateAlertDialogWithRadioButtonGroup(){
        AlertDialog alertDialog1;
        CharSequence[] values = {getString(R.string.s_level_1),getString(R.string.s_level_2),getString(R.string.s_level_3)};
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setTitle(getString(R.string.settings_title));
        builder.setSingleChoiceItems(values, -1, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {

                Settings settings = tablero.getSettings();

                switch(item)
                {
                    case 0:
                        settings.changeSettings(8,8,10);
                        break;
                    case 1:
                        settings.changeSettings(12,12,30);
                        break;
                    case 2:
                        settings.changeSettings(16,16,60);
                        break;
                }

                try {
                    synchronized(this){
                        wait(200);
                    }
                }
                catch(InterruptedException ex){}

                tablero = new Tablero(settings);
                newGame();

                dialog.dismiss();
            }
        });

        alertDialog1 = builder.create();
        alertDialog1.show();
    }

    private void newGame(){
        GridLayout gl_tablero = (GridLayout) findViewById(R.id.l_tablero);
        gl_tablero.removeAllViews();

        gl_tablero.setColumnCount(tablero.getSettings().getColumns());
        gl_tablero.setRowCount(tablero.getSettings().getRows());

        tablero.crearTablero();
        dibujar(gl_tablero);
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
