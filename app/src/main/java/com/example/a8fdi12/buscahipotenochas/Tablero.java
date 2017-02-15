package com.example.a8fdi12.buscahipotenochas;



import static java.lang.Math.max;
import static java.lang.Math.min;

/**
 * Created by 8fdi12 on 26/1/17.
 */

public class Tablero {

   private Casilla casillas [][];
   private Settings settings;
   private int estado; //0:jugando, 1:game over, 2:victoria
   private int contadorMinas;

   public Tablero(Settings settings) {
      this.settings = settings;
      this.estado = 0;
      this.contadorMinas = 0;
      this.casillas = new Casilla[settings.getColumns()][settings.getRows()];
   }

   public Settings getSettings() {
      return settings;
   }

   public void crearTablero(){
      //Inicializar el tablero
      for (int x = 0; x < settings.getColumns(); x++){
         for (int y = 0; y < settings.getRows(); y++){
            casillas[x][y] = new Casilla(x,y);

         }
      }

      //Colocar las minas
      for (int m = 0; m < settings.getMines(); m++){
         //Buscar una posicion aleatoria
         int rdm_x, rdm_y;

         do{
            rdm_x = (int) (Math.random()* settings.getColumns());
            rdm_y = (int) (Math.random()* settings.getRows());
         }while (casillas[rdm_x][rdm_y].getMina() == 9 );

         //Poner bomba
         casillas[rdm_x][rdm_y].setMina(9);

         //Recorrer el contorno de la mina
         for (int rdm_x2 = max(0,rdm_x-1); rdm_x2 < min(settings.getColumns(),rdm_x+2); rdm_x2++){
            for (int rmd_y2 = max(0,rdm_y-1); rmd_y2 < min(settings.getRows(), rdm_y+2); rmd_y2++){
               if(casillas[rdm_x2][rmd_y2].getMina()!=9){
                  //Incrementar mina
                  casillas[rdm_x2][rmd_y2].incrMina();
               }
            }
         }

      }

   }

   public Casilla getCasilla(int x, int y){
      return casillas[x][y];
   }

   public void setCasilla(Casilla casilla, int x, int y){
      casillas[x][y] = casilla;
   }

   public boolean minaEncontrada(){
      contadorMinas ++;
      return comprobarMinas();
   }

   public void setEstado(int estado) {
      this.estado = estado;
   }

   private boolean comprobarMinas() {
      if(contadorMinas == settings.getMines()){
         return true;
      }else{
         return false;
      }
   }
}
