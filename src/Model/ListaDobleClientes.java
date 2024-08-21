/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Franco Coward
 */
public class ListaDobleClientes {
    private NodoCliente cabeza;
    private NodoCliente cola;

    public ListaDobleClientes() {
        this.cabeza = null;
        this.cola = null;
    }

    public NodoCliente getCabeza() {
        return cabeza;
    }

    public void setCabeza(NodoCliente cabeza) {
        this.cabeza = cabeza;
    }

    public NodoCliente getCola() {
        return cola;
    }

    public void setCola(NodoCliente cola) {
        this.cola = cola;
    }

    // Método para agregar un cliente al final de la lista
    public void agregarCliente(Cliente cliente) {
        NodoCliente nuevoNodo = new NodoCliente(cliente);
        if (cabeza == null) {
            cabeza = cola = nuevoNodo;
        } else {
            cola.setSiguiente(nuevoNodo);
            nuevoNodo.setAnterior(cola);
            cola = nuevoNodo;
        }
    }

    // Método para atender (extraer) al primer cliente de la lista
public Cliente atenderCliente() {
    if (cabeza == null) {
        return null;
    }
    Cliente clienteAtendido = cabeza.getCliente();
    cabeza = cabeza.getSiguiente();
    if (cabeza != null) {
        cabeza.setAnterior(null);
    } else {
        cola = null; // Si la lista queda vacía, también se actualiza la cola.
    }
    return clienteAtendido;
}

    public int getSize() {
        int size = 0;
        NodoCliente actual = cabeza;
        while (actual != null) {
            size++;
            actual = actual.getSiguiente();
        }
        return size;
    }

    // Método para imprimir la lista de clientes (para depuración)
    public void imprimirLista() {
        NodoCliente actual = cabeza;
        while (actual != null) {
            System.out.println(actual.getCliente());
            actual = actual.getSiguiente();
        }
    }
}
