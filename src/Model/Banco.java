/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Franco Coward
 */
public class Banco {
    private String nombre;
    private ListaDobleClientes[] cajas; // Arreglo de listas dobles para las cajas
    private int numCajas;

    public Banco(String nombre, int numCajas) {
        this.nombre = nombre;
        this.numCajas = numCajas;
        this.cajas = new ListaDobleClientes[numCajas];
        for (int i = 0; i < numCajas; i++) {
            cajas[i] = new ListaDobleClientes();
        }
    }


    public void atenderCliente(int numCaja) {
        if (numCaja >= 1 && numCaja <= numCajas) {
            Cliente clienteAtendido = cajas[numCaja - 1].atenderCliente();
            if (clienteAtendido != null) {
                clienteAtendido.setHoraAtencion();
                System.out.println("Cliente atendido: " + clienteAtendido);
            } else {
                System.out.println("No hay clientes en la caja " + numCaja);
            }
        } else {
            System.out.println("Número de caja inválido");
        }
    }

    // Método para imprimir el estado de todas las cajas (para depuración)
    public void imprimirEstadoCajas() {
        for (int i = 0; i < numCajas; i++) {
            System.out.println("Caja " + (i + 1) + ":");
            cajas[i].imprimirLista();
        }
    }
}
