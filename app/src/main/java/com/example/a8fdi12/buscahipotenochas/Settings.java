package com.example.a8fdi12.buscahipotenochas;

import android.widget.GridLayout;

/**
 * Created by 8fdi12 on 2/2/17.
 */

public class Settings {

    private int columns,rows,mines;

    public Settings(int columns, int rows, int mines) {
        this.columns = columns;
        this.rows = rows;
        this.mines = mines;
    }

    public void setSettings(GridLayout tablero){
        tablero.removeAllViews();
        tablero.setColumnCount(this.columns);
        tablero.setRowCount(this.rows);


    }

    public void changeSettings(int columns,int rows, int mines){
        this.columns = columns;
        this.rows = rows;
        this.mines = mines;
    }

    public int getCells(){
        return this.columns * this.rows;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getMines() {return mines; }
}
