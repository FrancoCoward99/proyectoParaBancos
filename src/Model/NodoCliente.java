/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Franco Coward
 */
public class NodoCliente {
    private Cliente cliente;
    private NodoCliente siguiente;
    private NodoCliente anterior;

    // Constructor
    public NodoCliente(Cliente cliente) {
        this.cliente = cliente;
        this.siguiente = null;
        this.anterior = null;
    }

    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    public NodoCliente getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoCliente siguiente) {
        this.siguiente = siguiente;
    }

    public NodoCliente getAnterior() {
        return anterior;
    }

    public void setAnterior(NodoCliente anterior) {
        this.anterior = anterior;
    }
}
