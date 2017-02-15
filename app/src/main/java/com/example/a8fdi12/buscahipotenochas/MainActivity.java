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
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener{

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
        Settings settings = new Settings(8,8,10,R.drawable.bomb1);
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

        //Establecer
        gl_tablero.setColumnCount(tablero.getSettings().getColumns());
        gl_tablero.setRowCount(tablero.getSettings().getRows());

        int z = 0;

        for(int x = 0; x < settings.getColumns(); x++){
            for(int y = 0; y < settings.getRows(); y++){

                Casilla casilla = tablero.getCasilla(x,y);

                if (casilla.getMina() == 9){
                    //Crear image button
                    img_btn = new ImageButton(this);

                    //Establecer el tamaño y estilo del boton
                    img_btn.setLayoutParams(new LinearLayout.LayoutParams(width/settings.getColumns(),height/settings.getRows()));
                    img_btn.setBackgroundResource(R.drawable.custom_button);

                    //img_btn.setImageResource(settings.getIcon());
                    //Establecer el id
                    casilla.setId(z);
                    img_btn.setId(z);

                    //Añadir el los eventos
                    img_btn.setOnClickListener(this);
                    img_btn.setOnLongClickListener(this);

                    //Añadir el boton
                    gl_tablero.addView(img_btn,z);
                }else{
                    //Crear button
                    btn = new Button(this);

                    //Establecer el tamaño y estilo del boton
                    btn.setLayoutParams(new LinearLayout.LayoutParams(width/settings.getColumns(),height/settings.getRows()));
                    btn.setBackgroundResource(R.drawable.custom_button);

                    //btn.setText(Integer.toString(casilla.getMina()));
                    //Establecer el id
                    casilla.setId(z);
                    btn.setId(z);


                    //Añadir evento
                    btn.setOnClickListener(this);
                    btn.setOnLongClickListener(this);

                    gl_tablero.addView(btn,z);
                }

                tablero.setCasilla(casilla,x,y);
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

    @Override
    public void onClick(View view) {
        for(int x = 0; x < tablero.getSettings().getColumns(); x++){
            for(int y = 0; y < tablero.getSettings().getRows(); y++){
                click(x,y,view.getId());
            }
        }
    }

    @Override
    public boolean onLongClick(View view) {
        for(int x = 0; x < tablero.getSettings().getColumns(); x++){
            for(int y = 0; y < tablero.getSettings().getRows(); y++){
                //Obtener la casilla
                Casilla casilla = tablero.getCasilla(x,y);

                //Comprobar si la casilla coincide con la del evento
                if(view.getId() == casilla.getId()){
                    if(casilla.getMina() == 9){
                        //Comprobar el estado de casilla
                        if(casilla.getEstado() == 0){
                            //Cambiar estado marcado
                            casilla.setEstado(2);

                            //Mostrar imagen de explosion en el boton y deshablitar el boton
                            ImageButton img_b = (ImageButton) view;
                            img_b.setImageResource(tablero.getSettings().getIcon());
                            img_b.setEnabled(false);
                            img_b.setBackgroundResource(R.drawable.custom_button_disabled);

                            //Guardar la casilla
                            tablero.setCasilla(casilla,x,y);

                            //Aumentar contador minas
                            if(tablero.minaEncontrada()){
                                terminar(true);
                            }
                        }
                    }else{
                        //Comprobar el estado de casilla
                        if(casilla.getEstado() == 0){
                            //Cambiar estado destapado
                            casilla.setEstado(1);

                            //Mostrar el texto en el boton y deshablitar el boton
                            Button b = (Button) view;
                            b.setText(Integer.toString(casilla.getMina()));
                            b.setEnabled(false);
                            b.setBackgroundResource(R.drawable.custom_button_disabled);

                            //Guardar la casilla
                            tablero.setCasilla(casilla,x,y);

                            terminar(false);
                        }
                    }
                }
            }
        }

        return false;
    }

    private void click(int x, int y, int id){
        //Obtener la casilla
        Casilla casilla = tablero.getCasilla(x,y);

        //Comprobar si la casilla coincide con la del evento
        if(id == casilla.getId()){
            //Comprobar si hay mina
            if(casilla.getMina() != 9){
                //Comprobar el estado de casilla
                if(casilla.getEstado() == 0){
                    //Cambiar estado destapado
                    casilla.setEstado(1);

                    //Mostrar el texto en el boton y deshablitar el boton
                    Button b = (Button) findViewById(casilla.getId());
                    b.setEnabled(false);
                    b.setBackgroundResource(R.drawable.custom_button_disabled);

                    if(casilla.getMina() != 0){
                        b.setText(Integer.toString(casilla.getMina()));
                    }else{
                        //Recorrer el contorno de la casilla
                        for (int x2 = max(0,x-1); x2 < min(tablero.getSettings().getColumns(),x+2); x2++){
                            for (int y2 = max(0,y-1); y2 < min(tablero.getSettings().getRows(), y+2); y2++){
                                click(x2,y2,tablero.getCasilla(x2,y2).getId());
                            }
                        }
                    }

                    //Guardar la casilla
                    tablero.setCasilla(casilla,x,y);
                }
            }else{
                //Comprobar el estado de casilla
                if(casilla.getEstado() == 0){
                    //Cambiar estado destapado
                    casilla.setEstado(1);

                    //Mostrar imagen de explosion en el boton y deshablitar el boton
                    ImageButton img_b = (ImageButton) findViewById(casilla.getId());
                    img_b.setImageResource(R.drawable.bomb_click);
                    img_b.setEnabled(false);
                    img_b.setBackgroundResource(R.drawable.custom_button_disabled);

                    //Guardar la casilla
                    tablero.setCasilla(casilla,x,y);

                    //Terminar juego
                    terminar(false);
                }
            }
        }

    }

    private void terminar(boolean estado){
        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);

        if (estado){
            //Victoria
            tablero.setEstado(2);

            //Dialog de victoria
            dialogo1.setTitle(getText(R.string.victoria_title));
            dialogo1.setMessage(getText(R.string.victoria_text));
        }else{
            //Derrota
            tablero.setEstado(1);

            //Dialog de derrota
            dialogo1.setTitle(getText(R.string.derrota_title));
            dialogo1.setMessage(getText(R.string.derrota_text));
        }

        dialogo1.setNeutralButton(getText(R.string.dialog_instrucciones_btn), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                dialogo1.dismiss();
            }
        });
        dialogo1.show();


        for (int x = 0; x < tablero.getSettings().getColumns(); x++){
            for(int y = 0; y < tablero.getSettings().getRows(); y++){
                Casilla casilla = tablero.getCasilla(x,y);

                //Comprobar si la casilla esta tapa
                if(casilla.getEstado() == 0){
                    //Comprobar si hay mina
                    if(casilla.getMina() != 9){
                        //Obtener el boton
                        Button b = (Button) findViewById(casilla.getId());

                        //Mostrar el texto en el boton y deshablitar el boton
                        b.setText(Integer.toString(casilla.getMina()));
                        b.setEnabled(false);
                        b.setBackgroundResource(R.drawable.custom_button_disabled);
                    }else{
                        //Obtener el image button
                        ImageButton img_b = (ImageButton) findViewById(casilla.getId());

                        //Mostrar imagen de explosion en el boton y deshablitar el boton
                        img_b.setImageResource(R.drawable.bomb_click);
                        img_b.setEnabled(false);
                        img_b.setBackgroundResource(R.drawable.custom_button_disabled);
                    }
                }
            }
        }
    }
}
