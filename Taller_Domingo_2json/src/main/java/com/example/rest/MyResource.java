package com.example.rest;

import java.util.Random;

import javax.ws.rs.Path;

import controller.Dao.servicies.FamiliaServicies;
import controller.tda.list.LinkedList; 
import models.Familia; 

/**
 * Root resource (exposed at "myresource" path)
 */
@Path("myresource")
public class MyResource {
    LinkedList<Familia> lista = new LinkedList<>();

    public Familia[] arregloAleatorio() {
        Familia[] familiaIntegrantes = new Familia[25000];
        Random random = new Random();

        for (int i = 0; i < familiaIntegrantes.length; i++) {
            Familia f = new Familia();
            familiaIntegrantes[i] = f;
            familiaIntegrantes[i].setIntegrantes(random.nextInt(25000));

        }
        return familiaIntegrantes;
    }

    public LinkedList<Familia> hacerLista() {

        Familia[] familiaAleatoria = this.arregloAleatorio();
        this.lista.toList(familiaAleatoria);
        return this.lista;
    }

    public void inicializarCopias () {
        FamiliaServicies fs = new FamiliaServicies();
        hacerLista();

        LinkedList<Familia> copia1 = fs.duplicar_LinkedList(this.lista);
        LinkedList<Familia> copia2 = fs.duplicar_LinkedList(this.lista);
        LinkedList<Familia> copia3 = fs.duplicar_LinkedList(this.lista);

        // ORDENACION 

        try {


            //ShellSort

            long inicio = System.nanoTime(); //Medimos el tiempo desde un punto fijo

            copia1.shellSort("integrantes", 0);

            long fin = System.nanoTime(); // Marca de finalización
            long duracion = fin - inicio; // Tiempo transcurrido en nanosegundos
            System.out.println("Tiempo en ShellSort: " + (duracion / 1_000_000) + " milisegundos.");

            //QuickSort

            long inicio2 = System.nanoTime();

            copia2.quickSort("integrantes", 0);

            long fin2 = System.nanoTime(); 
            long duracion2 = fin2 - inicio2; 
            System.out.println("Tiempo en QuickSort: " + (duracion2 / 1000000) + " milisegundos.");

            //MergeSort

            long inicio3 = System.nanoTime();

            copia3.mergeSort("integrantes", 0);
            long fin3 = System.nanoTime(); 
            long duracion3 = fin3 - inicio3;

            System.out.println("Tiempo en MergeSort: " + (duracion3 / 1000000) + " milisegundos.");


        } catch (Exception e) {
        System.out.println("Error" +e);}


        // BUSQUEDA

        // Secuencial
        try {
            System.out.println(copia1.get(0).getIntegrantes() +  " / " + copia2.get(0).getIntegrantes());
            
            System.out.println("Tienen la misma referencia? " + (copia1.get(0) == copia2.get(0)));

            long inicio = System.nanoTime();

            LinkedList<Familia> resultado = fs.buscar_Integrantes_Prueba_Lineal(copia1, 5000);
            System.out.println(resultado.toString());

            long fin = System.nanoTime(); 
            long duracion = fin - inicio; 
            System.out.println("Tiempo en Lineal: " + (duracion / 1_000_000) + " milisegundos.");
            
        } catch (Exception e) {
        System.out.println("Error" +e);}

        // Lineal-binaria
        try {

            // copia2.mergeSort("integrantes", 0);

            long inicio = System.nanoTime();

            LinkedList<Familia> resultado = fs.buscar_Integrantes_Prueba(copia2, 5000);
            System.out.println(resultado.toString());

            long fin = System.nanoTime(); 
            long duracion = fin - inicio; 
            System.out.println("Tiempo en Lineal-Binario: " + (duracion / 1000000) + " milisegundos.");
            
        } catch (Exception e) {
        System.out.println("Error" +e);}
   
    }

    public static void main(String[] args) {
        MyResource mr = new MyResource();
        mr.inicializarCopias();
    }

    

}
