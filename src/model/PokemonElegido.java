
package model;

import java.util.List;

public class PokemonElegido {
    private String nombre;
    private List<String> tipo;
    private String habilidad;
    private String movimiento;


    public PokemonElegido(String nombre, List<String> tipo, String habilidad, String movimiento) {
        this.nombre = nombre;
        this.tipo = tipo;
        this.habilidad = habilidad;
        this.movimiento = movimiento;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getTipo() {
        return tipo;
    }

    public void setTipo(List<String> tipo) {
        this.tipo = tipo;
    }

    public String getHabilidad() {
        return habilidad;
    }

    public void setHabilidad(String habilidad) {
        this.habilidad = habilidad;
    }

    public String getMovimiento() {
        return movimiento;
    }

    public void setMovimiento(String movimiento) {
        this.movimiento = movimiento;
    }

}
