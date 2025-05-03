
package model;

import java.util.Date;

public class Batalla {
    private String pokemonUsuario;
    private String pokemonOponente;
    private String resultado;
    private Date fecha;

    public Batalla(String pokemonUsuario, String pokemonOponente, String resultado, Date fecha) {
        this.pokemonUsuario = pokemonUsuario;
        this.pokemonOponente = pokemonOponente;
        this.resultado = resultado;
        this.fecha = fecha;
    }

    public String getPokemonUsuario() {
        return pokemonUsuario;
    }

    public void setPokemonUsuario(String pokemonUsuario) {
        this.pokemonUsuario = pokemonUsuario;
    }

    public String getPokemonOponente() {
        return pokemonOponente;
    }

    public void setPokemonOponente(String pokemonOponente) {
        this.pokemonOponente = pokemonOponente;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
