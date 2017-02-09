package com.example.a8fdi12.buscahipotenochas;

/**
 * Created by 8fdi12 on 2/2/17.
 */

public class Casilla {

    private int x,y;
    private int mina; //[0-8] cuantas minas hay alrededor y 9 hay mina en la casilla
    private int estado;// 0:tapado, 1:descubierto, 2:bandera

    public Casilla(int x, int y) {
        this.x = x;
        this.y = y;
        this.mina = 0;
        this.estado = 0;
    }

    

}
