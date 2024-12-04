package controller.tda.list;

import java.lang.reflect.Method;

import javax.ws.rs.core.Link;

import controller.tda.list.LinkedList;
import models.Familia;

public class LinkedList<E> {
    private Node<E> header; // Nodo cabecera (el primer nodo de la lista)
    private Node<E> last; // Nodo último (el último nodo de la lista)
    private Integer size; // Tamaño de la lista (cuenta el número de nodos en la lista)

    // Constructor de la clase LinkedList
    public LinkedList() {
        this.header = null; // Inicialmente, la cabecera es nula (no hay nodos en la lista)
        this.last = null; // Inicialmente, el último nodo es nulo
        this.size = 0; // Inicialmente, el tamaño de la lista es 0
    }

    // Método para verificar si la lista está vacía
    public Boolean isEmpty() {
        // Retorna verdadero si la cabecera es nula o el tamaño es 0, es decir, si la
        // lista está vacía

        return (this.header == null || this.size == 0);
    }

    // Método privado para agregar un elemento al principio de la lista
    protected void addHeader(E dato) {
        Node<E> help; // Nodo de ayuda para insertar el nuevo dato

        // Si la lista está vacía
        if (isEmpty()) {
            help = new Node<>(dato); // Crea un nuevo nodo con el dato
            this.header = help; // El nuevo nodo se convierte en el nodo cabecera
            this.last = help; // Al ser el primero tambien es el ultimo
        } else {
            // Si la lista no está vacía
            Node<E> healpHeader = this.header; // Guarda el nodo cabecera actual en una variable auxiliar
            help = new Node<>(dato, healpHeader); // Crea un nuevo nodo que apunta al nodo cabecera actual
            this.header = help; // El nuevo nodo se convierte en la nueva cabecera
        }
        this.size++; // Incrementa el tamaño de la lista
    }

    private void addLast(E info) {
        Node<E> help; // Nodo para ayudar a agregar el nuevo elemento
        if (isEmpty()) { // Verificar si la lista está vacía
            help = new Node<>(info); // Crear un nuevo nodo
            this.header = help; // Establecer el nuevo nodo como cabecera
            this.last = help; // Establecer el nuevo nodo como último
        } else {
            help = new Node<>(info, null); // Crear un nuevo nodo
            last.setNext(help); // Conectar el último nodo al nuevo nodo
            last = help; // Actualizar 'last' al nuevo nodo
        }
        this.size++; // Incrementar el tamaño de la lista
    }

    public void add(E info) {
        addLast(info);
    }

    public void addF(E info) {
        addHeader(info);
    }

    private Node<E> getNode(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, List empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == (this.size - 1)) {
            return last;
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search;
        }
    }

    public E getFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, lista vacia");
        }
        return header.getInfo();
    }

    public E getLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, Lista Vacia");
        }
        return last.getInfo();
    }

    public E get(Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, list empty");
        } else if (index.intValue() < 0 || index.intValue() >= this.size.intValue()) {
            throw new IndexOutOfBoundsException("Error, fuera de rango");
        } else if (index.intValue() == 0) {
            return header.getInfo();
        } else if (index.intValue() == (this.size - 1)) {
            return last.getInfo();
        } else {
            Node<E> search = header;
            int cont = 0;
            while (cont < index.intValue()) {
                cont++;
                search = search.getNext();
            }
            return search.getInfo();
        }
    }

    public void add(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty() || index.intValue() == 0) {
            addHeader(info);
        } else if (index.intValue() == this.size.intValue()) {
            addLast(info);
        } else {
            Node<E> search_preview = getNode(index - 1);
            Node<E> search = getNode(index);
            Node<E> help = new Node<>(info, search);
            search_preview.setNext(help);
            this.size++;
        }
    }

    public void update(E info, Integer index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("La lista está vacía");
        }

        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Índice fuera de límites");
        }

        // Obtener el nodo en la posición dada
        Node<E> help = getNode(index);
        help.setInfo(info); // Suponiendo que tienes un método setInfo en tu clase Node
    }

    /*** END BYPOSITION */
    public void reset() {
        this.header = null;
        this.last = null;
        this.size = 0;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("List data");
        try {
            Node<E> help = header;
            while (help != null) {
                sb.append(help.getInfo()).append(" ->");
                help = help.getNext();
            }
        } catch (Exception e) {
            sb.append(e.getMessage());
        }
        return sb.toString();
    }

    public Integer getSize() {
        return this.size;
    }

    public Node<E> getHeader() {
        return header; // Devuelve el nodo cabecera
    }

    // esta bien
    public E[] toArray() {
        E[] matrix = null;
        if (!isEmpty()) {
            Class clazz = header.getInfo().getClass();
            matrix = (E[]) java.lang.reflect.Array.newInstance(clazz, size);
            Node<E> aux = header;
            for (int i = 0; i < this.size; i++) {
                matrix[i] = aux.getInfo();
                aux = aux.getNext();
            }

        }
        return matrix;
    }

    public LinkedList<E> toList(E[] matrix) { // Recibe un array de objetos
        reset(); // Reinicia la lista
        for (int i = 0; i < matrix.length; i++) { // Recorre el array
            this.add(matrix[i]); // Agrega cada objeto del array a un nodo de la lista
        }
        return this; // Devuelve la instancia LinkedList
    }

    // REMOVE AGREGADO 24/OCT/2024

    public int getLength() {
        return this.size.intValue();
    }

    protected void removeLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, no puede eliminar datos de una lista vacia.");
        } else {
            Node<E> nodo_last = getNode((getLength() - 2));
            nodo_last.setNext(null);
            this.last = nodo_last;
            this.size--;
        }
    }

    public void removeFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, no puede eliminar datos de una lista vacia.");
        } else {
            Node<E> help = this.header;
            Node<E> nextHeader = help.getNext();
            this.header = nextHeader;
            this.size--;
        }
    }

    public void remove(int index) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia, no puede eliminar elementos");
        } else if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Índice fuera de límites: " + index);
        } else if (index == 0) {
            removeFirst();
        } else if (index == (this.size - 1)) {
            removeLast();
        } else {
            Node<E> nodoDeath = getNode(index);
            Node<E> previousNode = getNode(index - 1);
            previousNode.setNext(nodoDeath.getNext());
            this.size--;
        }

    }

    protected E deleteLast() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, no puede eliminar datos de una lista vacia.");
        } else {
            E element = last.getInfo();
            Node<E> aux = getNode(size - 2);
            if (aux == null) {
                last = null;
                if (size == 2) {
                    last = header;
                } else {
                    header = null;
                }
            } else {
                last = null;
                last = aux;
                last.setNext(null);
            }
            size--;
            return element;
        }
    }

    public E deleteFirst() throws ListEmptyException {
        if (isEmpty()) {
            throw new ListEmptyException("Error, no puede eliminar datos de una lista vacia.");
        } else {
            E element = header.getInfo();
            Node<E> aux = header.getNext();
            header = aux;
            if (size.intValue() == 1) {
                last = null;
            }
            size--;
            return element;
        }
    }

    public E delete(Integer post) throws ListEmptyException, IndexOutOfBoundsException {
        if (isEmpty()) {
            throw new ListEmptyException("Lista vacia, no puede eliminar elementos");
        } else if (post < 0 || post >= this.size) {
            throw new IndexOutOfBoundsException("Índice fuera de límites: " + post);
        } else if (post == 0) {
            return deleteFirst();
        } else if (post == (this.size - 1)) {
            return deleteLast();
        } else {
            Node<E> preview = getNode(post - 1);
            Node<E> actually = getNode(post);
            E element = preview.getInfo();
            Node<E> next = actually.getNext();
            actually = null;
            preview.setNext(next);
            size--;
            return element;
        }

    }

    public LinkedList<E> order(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E data = this.header.getInfo();
            if (data instanceof Object) {
                E[] lista = this.toArray();
                reset();
                for (int i = 1; i < lista.length; i++) {
                    E aux = lista[i];
                    int j = i - 1;
                    while (j >= 0 && attribute_compare(attribute, lista[j], aux, type)) {
                        lista[j + 1] = lista[j];
                        j--;
                    }
                    lista[j + 1] = aux;
                }
                this.toList(lista);
            }

        }
        return this;
    }

    public LinkedList<E> shellSort(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            System.out.println("Estamos usando shellSort");
            E[] lista = this.toArray();
            int n = lista.length;

            for (int gap = n / 2; gap > 0; gap /= 2) {
                for (int i = gap; i < n; i++) {
                    E aux = lista[i];
                    int j;

                    for (j = i; j >= gap && attribute_compare(attribute, lista[j - gap], aux, type); j -= gap) {
                        lista[j] = lista[j - gap];
                    }

                    lista[j] = aux;
                }
            }
            this.toList(lista);
        }
        return this;
    }

    // INICIO QUICKSORT

    public LinkedList<E> quickSort(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            // System.out.println("Estamos usando quickSort");
            E[] lista = this.toArray();
            quickSortHelper(lista, 0, lista.length - 1, attribute, type);
            this.toList(lista);
        }
        return this;
    }

    private void quickSortHelper(E[] arr, int low, int high, String attribute, Integer type) throws Exception {
        if (low < high) {
            int pivotIndex = partition(arr, low, high, attribute, type);
            quickSortHelper(arr, low, pivotIndex - 1, attribute, type);
            quickSortHelper(arr, pivotIndex + 1, high, attribute, type);
        }
    }

    private int partition(E[] arr, int low, int high, String attribute, Integer type) throws Exception {
        E pivot = arr[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (attribute_compare(attribute, pivot, arr[j], type)) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private void swap(E[] arr, int i, int j) {
        E temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    // FIN QUICKSORT

    // INICIO MERGESORT

    public LinkedList<E> mergeSort(String attribute, Integer type) throws Exception {
        if (!isEmpty()) {
            E[] lista = this.toArray(); 
            lista = mergeSortHelper(lista, attribute, type); 
            this.toList(lista); 
        }
        return this;
    }

    private E[] mergeSortHelper(E[] array, String attribute, Integer type) throws Exception {
        if (array.length < 2) {
            return array; 
        }
    
        int mid = array.length / 2; 
        E[] left = copiarRangoArray(array, 0, mid); 
        E[] right = copiarRangoArray(array, mid, array.length); 
    
        left = mergeSortHelper(left, attribute, type);
        right = mergeSortHelper(right, attribute, type);
    
        return merge(left, right, attribute, type);
    }

    private E[] copiarRangoArray(E[] array, int start, int end) {
        int length = end - start;
        E[] newArray = (E[]) new Object[length];
        for (int i = 0; i < length; i++) {
            newArray[i] = array[start + i];
        }
        return newArray;
    }
    
    
    private E[] merge(E[] left, E[] right, String attribute, Integer type) throws Exception {
        E[] resultado = (E[]) new Object[left.length + right.length]; 
        int i = 0, j = 0, k = 0;

        while (i < left.length && j < right.length) {
            if (attribute_compare(attribute, left[i], right[j], type)) {
            resultado[k++] = right[j++];                
            } else {
            resultado[k++] = left[i++];

            }
        }
        while (i < left.length) {
            resultado[k++] = left[i++];
        }
        while (j < right.length) {
            resultado[k++] = right[j++];
        }

        return resultado;
    }

    //FIN MERGESORT

    private Boolean compare(Object a, Object b, Integer type) {
        switch (type) {
            case 0:
                // System.out.println("Estamos ordenando ascendentemente ");
                if (a instanceof Boolean && b instanceof Boolean) {
                    return (Boolean) a && !(Boolean) b;
                } else if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() > b1.doubleValue();
                } else {
                    return (a.toString().toUpperCase()).compareTo(b.toString().toUpperCase()) > 0;
                }

            default:
                // System.out.println("Estamos ordenando de descendentemente");
                if (a instanceof Boolean && b instanceof Boolean) {
                    return !(Boolean) a && (Boolean) b;
                } else if (a instanceof Number) {
                    Number a1 = (Number) a;
                    Number b1 = (Number) b;
                    return a1.doubleValue() < b1.doubleValue();
                } else {
                    return (a.toString().toUpperCase()).compareTo(b.toString().toUpperCase()) < 0;
                }
        }
    }

    private Boolean attribute_compare(String attribute, E a, E b, Integer type) throws Exception {
        return compare(exist_attribute(a, attribute), exist_attribute(b, attribute), type);
    }

    private Object exist_attribute(E a, String attribute) throws Exception {
        Method method = null;
        attribute = attribute.substring(0, 1).toUpperCase() + attribute.substring(1);
        attribute = "get" + attribute;
        for (Method aux : a.getClass().getMethods()) {
            if (aux.getName().contains(attribute)) {
                method = aux;
                break;
            }

        }
        if (method == null) {
            for (Method aux : a.getClass().getSuperclass().getMethods()) {
                if (aux.getName().contains(attribute)) {
                    method = aux;
                    break;
                }

            }

        }
        if (method != null) {
            return method.invoke(a);
        }

        return null;
    }
    
    public LinkedList<Familia> duplicateLinkedList(LinkedList<Familia> lista) {
        LinkedList<Familia> nueva = new LinkedList<>();
        try {
            for (int i = 0; i < lista.getSize(); i++) {
                Familia original = lista.get(i);
                Familia copia = new Familia(original);  
                nueva.add(copia);
            }
            return nueva;
        } catch (Exception e) {
            System.out.println("Error "+e);        
        }
        return nueva;

    }
    
    
    

}
