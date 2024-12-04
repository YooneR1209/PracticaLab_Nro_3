package controller.Dao;

import models.Familia;

import java.util.Arrays;
import java.util.Comparator;

import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;
import controller.tda.list.ListEmptyException;

public class FamiliaDao extends AdapterDao<Familia> {
    private Familia familia = new Familia();
    private LinkedList<Familia> listAll;

    public FamiliaDao() {
        super(Familia.class);
    }

    public Familia getFamilia() { // Obtiene la familia
        if (familia == null) {
            familia = new Familia(); // En caso de que la familia sea nula, crea una nueva instancia de Familia
        }
        return this.familia; // Devuelve la familia
    }

    public void setFamilia(Familia familia) { // Establece la familia con un objeto Familia
        this.familia = familia; // Asigna el objeto Familia a la variable familia
    }

    public LinkedList getlistAll() { // Obtiene la lista de objetos
        if (listAll == null) { // Si la lista es nula
            this.listAll = listAll(); // Invoca el método listAll() para obtener la lista de objetos
        }
        return listAll; // Devuelve la lista de objetos de la variable listAll
    }

    public Boolean save() throws Exception { // Guarda la variable familia en la lista de objetos
        Integer id = getlistAll().getSize() + 1; // Obtiene el tamaño de la lista y le suma 1 para asignar un nuevo id
        familia.setId(id); // Asigna el id a familia
        this.persist(this.familia); // Guarda la familia en la lista de objetos LinkedList y en el archivo JSON
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se guardó correctamente
    }

    public Boolean update() throws Exception { // Actualiza el nodo Familia en la lista de objetos
        this.merge(getFamilia(), getFamilia().getId() - 1); // Envia la familia a actualizar con su index
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true;
    }

    public Boolean delete(int index) throws Exception { // Elimina un objeto Familia por su índice
        this.supreme(index);
        this.listAll = listAll(); // Actualiza la lista de objetos
        return true; // Retorna verdadero si se eliminó correctamente
    }

    public int contarFamiliasConGenerador() {
        int contador = 0;
        LinkedList<Familia> familias = listAll(); // lista todas las familias
        Familia[] familiasArray = familias.toArray(); // Convierte la lista enlazada en un arreglo

        for (Familia familia : familiasArray) { // Usa el bucle for-each en el arreglo
            if (familia.getTieneGenerador()) { // Verifica si la familia tiene generador
                contador++;
            }
        }
        return contador;
    }

    // public LinkedList order(Integer type_order, String atributo) {
    // LinkedList listita = listAll();

    // if (!listAll().isEmpty()) {
    // Familia[] lista = (Familia[]) listita.toArray();
    // listita.reset();

    // for (int i = 1; i < lista.length; i++) {
    // Familia aux = lista[i];
    // int j = i - 1;
    // while (j >= 0 && (verify(lista[j], aux, type_order, atributo))) {
    // lista[j + 1] = lista[j--];
    // }
    // lista[j + 1] = aux;
    // }

    // listita.toList(lista);
    // }
    // return listita;
    // }

    public LinkedList<Familia> order(String attribute, Integer type, Integer metodo) {
        try {
            getlistAll();
            System.out.println("Lista antes de ordenar " + listAll.toString());
            switch (metodo) {
                case 1:
                    System.out.println("Método de ordenación Lineal ");
                    return this.listAll.order(attribute, type);

                case 2:
                    System.out.println("Método de ordenación shellSort ");
                    return this.listAll.shellSort(attribute, type);

                case 3:
                    System.out.println("Método de ordenación quickSort " );
                    return this.listAll.quickSort(attribute, type);

                default:
                    System.out.println("Método de ordenación mergeSort ");
                    return this.listAll.mergeSort(attribute, type);
            }

        } catch (Exception e) {
            return null;
        }
    }

    // private Boolean verify(Familia a, Familia b, Integer type_order, String
    // atributo) {
    // if (type_order == 1) {
    // if (atributo.equalsIgnoreCase("apellidoPaterno")) {
    // return a.getApellidoPaterno().toLowerCase().compareTo(b.getApellidoPaterno())
    // > 0;
    // } else if (atributo.equalsIgnoreCase("apellidoMaterno")) {
    // return a.getApellidoPaterno().toLowerCase().compareTo(b.getApellidoPaterno())
    // > 0;
    // }

    // } else {
    // if (atributo.equalsIgnoreCase("apellidoPaterno")) {
    // return a.getApellidoPaterno().compareTo(b.getApellidoPaterno()) < 0;
    // } else if (atributo.equalsIgnoreCase("apellidoMaterno")) {
    // return a.getApellidoPaterno().compareTo(b.getApellidoPaterno()) < 0;
    // }
    // }
    // return false;

    // }

    public LinkedList<Familia> buscar_Apellido_Paterno(String texto) {
        System.out.println("Estamos buscando por Metodo Lineal");
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();
        if (!listita.isEmpty()) {
            Familia[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {
                if (aux[i].getApellidoPaterno().toLowerCase().startsWith(texto.toLowerCase())) {
                    lista.add(aux[i]);

                }
            }
        }
        return lista;
    }

    public LinkedList<Familia> buscar_Apellido_Materno(String texto) {
        System.out.println("Estamos buscando por Metodo Lineal");
        LinkedList<Familia> lista = new LinkedList<>();
        LinkedList<Familia> listita = listAll();
        if (!listita.isEmpty()) {
            Familia[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {
                if (aux[i].getApellidoMaterno().toLowerCase().startsWith(texto.toLowerCase())) {
                    lista.add(aux[i]);

                }
            }
        }
        return lista;
    }

    // BUSQUEDA BINARIA STRINGS

    public LinkedList<Familia> buscar_Apellido_Materno_Binaria(String texto) {

        System.out.println("Estamos buscando por Metodo Lineal-Binario AM");
        LinkedList<Familia> resultados = new LinkedList<>();

        try {
            getlistAll();
            listAll.quickSort("apellidoMaterno", 0);

            int low = 0;
            int high = listAll.getSize() - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                Familia midFamilia = listAll.get(mid);
                String apellidoMaterno = midFamilia.getApellidoMaterno().toLowerCase();

                if (apellidoMaterno.startsWith(texto.toLowerCase())) {
                    // Añade elementos que coincidan hacia ambos lados
                    int left = mid;
                    int right = mid;
                    while (left >= 0
                            && listAll.get(left).getApellidoMaterno().toLowerCase().startsWith(texto.toLowerCase())) {
                        resultados.addF(listAll.get(left--));
                    }
                    while (right < listAll.getSize()
                            && listAll.get(right).getApellidoMaterno().toLowerCase().startsWith(texto.toLowerCase())) {
                        if (right != mid)
                            resultados.add(listAll.get(right));
                        right++;
                    }
                    break;
                } else if (apellidoMaterno.compareTo(texto.toLowerCase()) < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
            
            return resultados.quickSort("id", 0);

        } catch (Exception e) {
            System.out.println(e);
        }
        return resultados;

    }

    public LinkedList<Familia> buscar_Canton_LB(String texto) {

        System.out.println("Estamos buscando por Metodo Lineal-Binario C");
        LinkedList<Familia> resultados = new LinkedList<>();

        try {
            getlistAll();
            listAll.mergeSort("canton", 0);
            int low = 0;
            int high = listAll.getSize() - 1;
            while (low <= high) {
                int mid = (low + high) / 2;
                Familia midFamilia = listAll.get(mid);
                String canton = midFamilia.getCanton().toLowerCase();

                if (canton.startsWith(texto.toLowerCase())) {
                    // Añade elementos que coincidan hacia ambos lados
                    int left = mid;
                    int right = mid;
                    while (left >= 0
                            && listAll.get(left).getCanton().toLowerCase().startsWith(texto.toLowerCase())) {
                        resultados.addF(listAll.get(left--));
                    }
                    while (right < listAll.getSize()
                            && listAll.get(right).getCanton().toLowerCase().startsWith(texto.toLowerCase())) {
                        if (right != mid)
                            resultados.add(listAll.get(right));
                        right++;
                    }
                    break;
                } else if (canton.compareTo(texto.toLowerCase()) < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            return resultados.quickSort("id", 0);

        } catch (Exception e) {
            System.out.println("Error durante la búsqueda: " + e.getMessage());
        }
        return resultados;
    }

    public Familia buscar_Canton_Binaria(String texto) {
        System.out.println("Estamos buscando por Método Binario puro");
    
        try {
            getlistAll(); 
            listAll.mergeSort("canton", 0); 
    
            int low = 0;
            int high = listAll.getSize() - 1;
    
            while (low <= high) {
                int mid = (low + high) / 2;
                Familia midFamilia = listAll.get(mid);
                String canton = midFamilia.getCanton().toLowerCase();
    
                if (canton.startsWith(texto.toLowerCase())) {
                    return midFamilia; 
                } else if (canton.compareTo(texto.toLowerCase()) < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        } catch (Exception e) {
            System.out.println("Error durante la búsqueda: " + e.getMessage());
        }
    
        return null; // No se encontró coincidencia
    }
    

    public LinkedList<Familia> buscar_Apellido_Paterno_Binaria(String texto) {

        System.out.println("Estamos buscando por Metodo Binario AP");
        LinkedList<Familia> resultados = new LinkedList<>();

        try {
            this.getlistAll();
            System.err.println("Hacemos quickSort FamiliaDao");
            listAll.quickSort("apellidoPaterno", 0);
            System.err.println("SALIMOS quickSort FamiliaDao");

            int low = 0;
            int high = listAll.getSize() - 1;

            while (low <= high) {
                int mid = (low + high) / 2;
                Familia midFamilia = listAll.get(mid);
                String apellidoPaterno = midFamilia.getApellidoPaterno().toLowerCase();

                if (apellidoPaterno.startsWith(texto.toLowerCase())) {
                    int left = mid;
                    int right = mid;
                    while (left >= 0
                            && listAll.get(left).getApellidoPaterno().toLowerCase().startsWith(texto.toLowerCase())) {
                        resultados.addF(listAll.get(left--));
                    }
                    while (right < listAll.getSize()
                            && listAll.get(right).getApellidoPaterno().toLowerCase().startsWith(texto.toLowerCase())) {
                        if (right != mid)
                            resultados.add(listAll.get(right));
                        right++;
                    }
                    break;
                } else if (apellidoPaterno.compareTo(texto.toLowerCase()) < 0) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }

            return resultados.quickSort("id", 0);

        } catch (Exception e) {
            // TODO: handle exception
        }
        
        return resultados;

    }

    // BUSQUEDA ID BINARIA

    public Familia buscar_Id_Binaria(Integer id) {
        System.out.println("Estamos buscando por Metodo Binario Id");

        try {
            this.getlistAll();
            listAll.quickSort("id", 0);
            Familia[] listaOrdenada = listAll.toArray();
            if (listaOrdenada == null || listaOrdenada.length == 0) {
                return null;
            }

            int izquierda = 0;
            int derecha = listaOrdenada.length - 1;

            while (izquierda <= derecha) {
                int medio = izquierda + (derecha - izquierda) / 2;
                Integer idMedio = listaOrdenada[medio].getId();

                if (idMedio.equals(id)) {
                    return listaOrdenada[medio];
                }

                if (idMedio < id) {
                    izquierda = medio + 1;
                } else {
                    derecha = medio - 1;
                }
            }

        } catch (Exception e) {
            // TODO: handle exception
        }

        return null;

    }
    // FIN BUSQUEDA NUMERICA BINARIA


    //Buscar Integrantes:
    //Busqueda Lineal Binaria

    public LinkedList<Familia> buscar_Integrantes_LinealBinaria(int integrantes) {
        System.out.println("Estamos buscando por Método Binario para integrantes");
        LinkedList<Familia> resultados = new LinkedList<>();
        this.getlistAll();
        try {
            listAll.quickSort("integrantes", 0);
            Familia[] listaOrdenada = listAll.toArray();

            if (listaOrdenada == null || listaOrdenada.length == 0) {
                return resultados;
            }

            int izquierda = 0;
            int derecha = listaOrdenada.length - 1;

            while (izquierda <= derecha) {
                int medio = izquierda + (derecha - izquierda) / 2;
                int integrantesMedio = listaOrdenada[medio].getIntegrantes();

                if (integrantesMedio == integrantes) {
                    resultados.add(listaOrdenada[medio]);

                    int left = medio - 1;
                    while (left >= 0 && listaOrdenada[left].getIntegrantes() == integrantes) {
                        resultados.addF(listaOrdenada[left]);
                        left--;
                    }

                    int right = medio + 1;
                    while (right < listaOrdenada.length && listaOrdenada[right].getIntegrantes() == integrantes) {
                        resultados.add(listaOrdenada[right]);
                        right++;
                    }
                    break;
                }

                if (integrantesMedio < integrantes) {
                    izquierda = medio + 1;
                } else {
                    derecha = medio - 1;
                }
            }

            return resultados.quickSort("id", 0);

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return resultados;
    }

    //Busqueda Binaria

    public Familia buscar_Integrantes_Binaria(Integer integrantes) {
        System.out.println("Estamos buscando por Metodo Binario integrantes");

        try {
            this.getlistAll();
            listAll.quickSort("integrantes", 0);
            Familia[] listaOrdenada = listAll.toArray();
            if (listaOrdenada == null || listaOrdenada.length == 0) {
                return null; // Si la lista está vacía, retorna null
            }

            int izquierda = 0;
            int derecha = listaOrdenada.length - 1;

            // Búsqueda binaria
            while (izquierda <= derecha) {
                int medio = izquierda + (derecha - izquierda) / 2;
                Integer integrantesMedio = listaOrdenada[medio].getIntegrantes();

                if (integrantesMedio.equals(integrantes)) {
                    return listaOrdenada[medio]; // Se encontró la coincidencia
                }

                if (integrantesMedio < integrantes) {
                    izquierda = medio + 1; // Buscar en la mitad derecha
                } else {
                    derecha = medio - 1; // Buscar en la mitad izquierda
                }
            }

        } catch (Exception e) {
            System.out.println("Error " + e);
        }

        return null; // No se encontró la coincidencia

    }
    //FIN busqueda integrantes

    public Familia buscar_Id(Integer id) {
        Familia familia = new Familia();
        LinkedList<Familia> listita = listAll();
        if (!listita.isEmpty()) {
            Familia[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {

                if (aux[i].getId().equals(id)) {
                    familia = aux[i];
                    System.out.println(familia.getApellidoPaterno());
                    break;
                }
            }
        }
        return familia;
    }

    //PRUEBA BUSQUEDA

    public LinkedList<Familia> buscar_Integrantes_LinealBinaria_P(LinkedList<Familia> lista, int integrantes) {
        LinkedList<Familia> resultados = new LinkedList<>();
        try {
            // lista.mergeSort("integrantes", 0);
            Familia[] listaOrdenada = lista.toArray();

            if (listaOrdenada == null || listaOrdenada.length == 0) {
                return resultados;
            }

            int izquierda = 0;
            int derecha = listaOrdenada.length - 1;

            while (izquierda <= derecha) {
                int medio = izquierda + (derecha - izquierda) / 2;
                int integrantesMedio = listaOrdenada[medio].getIntegrantes();

                if (integrantesMedio == integrantes) {
                    resultados.add(listaOrdenada[medio]);

                    int left = medio - 1;
                    while (left >= 0 && listaOrdenada[left].getIntegrantes() == integrantes) {
                        resultados.addF(listaOrdenada[left]);
                        left--;
                    }

                    int right = medio + 1;
                    while (right < listaOrdenada.length && listaOrdenada[right].getIntegrantes() == integrantes) {
                        resultados.add(listaOrdenada[right]);
                        right++;
                    }
                    break;
                }

                if (integrantesMedio < integrantes) {
                    izquierda = medio + 1;
                } else {
                    derecha = medio - 1;
                }
            }

            return resultados.quickSort("id", 0);

        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return resultados;
    }

    public LinkedList<Familia> buscar_Integrantes_LinealBinaria_PL(LinkedList <Familia> listita, Integer valor) {
        System.out.println("Estamos buscando por Metodo Lineal");
        LinkedList<Familia> lista = new LinkedList<>();
        if (!listita.isEmpty()) {
            Familia[] aux = listita.toArray();
            for (int i = 0; i < aux.length; i++) {
                if (aux[i].getIntegrantes() == valor) {
                    lista.add(aux[i]);

                }
            }
        }
        return lista;
    }

}
