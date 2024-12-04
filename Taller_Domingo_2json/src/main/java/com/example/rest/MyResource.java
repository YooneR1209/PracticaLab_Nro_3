package com.example.rest;

import java.util.Random;

// import java.util.HashMap;

// import javax.ws.rs.GET;
import javax.ws.rs.Path;
// import javax.ws.rs.Produces;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;

import controller.Dao.servicies.FamiliaServicies;
// import controller.Dao.servicies.FamiliaServicies;
import controller.tda.list.LinkedList; // Import correcto para LinkedList
import models.Familia; // Importar tu clase Familia

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
            familiaIntegrantes[i] = new Familia();
            familiaIntegrantes[i].setIntegrantes(random.nextInt(25000));

        }
        return familiaIntegrantes;
    }

    public LinkedList<Familia> hacerLista() {
        Familia[] familiaAleatoria = this.arregloAleatorio();
        for (int i = 0; i < familiaAleatoria.length; i++) {
            Familia f = new Familia(familiaAleatoria[i]);
            this.lista.add(f);
        }
        return this.lista;
    }

    public void inicializarCopias () {
        // ORDENACION 
        
        // try {
            hacerLista();

            LinkedList<Familia> copia1 = lista.duplicateLinkedList(lista);
            LinkedList<Familia> copia2 = lista.duplicateLinkedList(lista);
        //     LinkedList<Familia> copia3 = lista.duplicateLinkedList(lista);

        //     //ShellSort

        //     long startTime = System.nanoTime();

        //     copia1.shellSort("integrantes", 0);

        //     long endTime = System.nanoTime(); // Marca de finalización
        //     long duration = endTime - startTime; // Tiempo transcurrido en nanosegundos
        //     System.out.println("Tiempo en ShellSort: " + (duration / 1_000_000) + " milisegundos.");

        //     //QuickSort

        //     long startTime2 = System.nanoTime();

        //     copia2.quickSort("integrantes", 0);

        //     long endTime2 = System.nanoTime(); // Marca de finalización
        //     long duration2 = endTime2 - startTime2; // Tiempo transcurrido en nanosegundos
        //     System.out.println("Tiempo en QuickSort: " + (duration2 / 1_000_000) + " milisegundos.");

        //     //MergeSort

        //     long startTime3 = System.nanoTime();

        //     copia3.mergeSort("integrantes", 0);
        //     long endTime3 = System.nanoTime(); // Marca de finalización
        //     long duration3 = endTime3 - startTime3; // Tiempo transcurrido en nanosegundos

        //     System.out.println("Tiempo en MergeSort: " + (duration3 / 1_000_000) + " milisegundos.");


        // } catch (Exception e) {
        // System.out.println("Error" +e);}


        // hacerLista();
        // BUSQUEDA

        // Secuencial
        try {

            FamiliaServicies fs = new FamiliaServicies();

            long startTime = System.nanoTime();

            LinkedList<Familia> resultado = fs.buscar_Integrantes_Prueba_Lineal(copia1, 5000);
            System.out.println(resultado.toString());

            long endTime = System.nanoTime(); 
            long duration = endTime - startTime; 
            System.out.println("Tiempo en Lineal: " + (duration / 1_000_000) + " milisegundos.");
            
        } catch (Exception e) {
        System.out.println("Error" +e);}

        // Lineal-binaria
        try {

            FamiliaServicies fs = new FamiliaServicies();
            copia2.mergeSort("integrantes", 0);

            long startTime = System.nanoTime();

            LinkedList<Familia> resultado = fs.buscar_Integrantes_Prueba(copia2, 5000);
            System.out.println(resultado.toString());

            long endTime = System.nanoTime(); 
            long duration = endTime - startTime; 
            System.out.println("Tiempo en Lineal-Binario: " + (duration / 1_000_000) + " milisegundos.");
            
        } catch (Exception e) {
        System.out.println("Error" +e);}

        
    
    }

    public static void main(String[] args) {
        MyResource mr = new MyResource();
        mr.inicializarCopias();
    }

    

}
