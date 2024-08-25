/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.time.LocalDateTime;

/**
 *
 * @author Franco Coward
 */
public class Cliente {
    private String nombre;
    private int id;
    private int edad;
    private String tramite;
    private String tipo;
    private LocalDateTime horaCreacion;
    private LocalDateTime horaAtencion;
     private int tiquete;

    // Constructor
    public Cliente(String nombre, int id, int edad, String tramite, String tipo, int tiquete) {
        this.nombre = nombre;
        this.id = id;
        this.edad = edad;
        this.tramite = tramite;
        this.tipo = tipo;
        this.horaCreacion = LocalDateTime.now();
        this.horaAtencion = null;
        this.tiquete = tiquete;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getTramite() {
        return tramite;
    }

    public void setTramite(String tramite) {
        this.tramite = tramite;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public LocalDateTime getHoraCreacion() {
        return horaCreacion;
    }

    public void setHoraCreacion(LocalDateTime horaCreacion) {
        this.horaCreacion = horaCreacion;
    }

    public LocalDateTime getHoraAtencion() {
        return horaAtencion;
    }

    public void setHoraAtencion(LocalDateTime horaAtencion) {
        this.horaAtencion = horaAtencion;
    }

 
    
    public void setHoraAtencion() {
        this.horaAtencion = LocalDateTime.now();
    }
    
       public int getTiquete() {
        return tiquete;
    }

    public void setTiquete(int tiquete) {
        this.tiquete = tiquete;
    }
    
    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", id=" + id +
                ", edad=" + edad +
                ", tramite='" + tramite + '\'' +
                ", tipo='" + tipo + '\'' +
                ", horaCreacion=" + horaCreacion +
                ", horaAtencion=" + (horaAtencion != null ? horaAtencion: "No atendido") +
                '}';
    }
}
