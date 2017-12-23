/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Vitor
 */
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class SubConjunto {

    private LinkedHashSet<Object> lista; // nao é ordenado
    private TreeSet<Object> lista_ordenada;
    public double hash_subconjunto = 0; // resultado do mod
    public double soma_hash = 0; //soma das hash dos Objects
    public boolean ordenado;

    public SubConjunto(boolean ordenado) {
        this.ordenado = ordenado;

        if (ordenado) {
            lista_ordenada = new TreeSet<>();
        } else {
            lista = new LinkedHashSet<>();
        }

    }

    public LinkedHashSet<Object> getLista() {
        return lista;
    }

    public void setLista(LinkedHashSet<Object> lista) {
        this.lista = lista;
    }

    public TreeSet<Object> getLista_ordenada() {
        return lista_ordenada;
    }

    public void setLista_ordenada(TreeSet<Object> lista_ordenada) {
        this.lista_ordenada = lista_ordenada;
    }

    public double getHash_subconjunto() {
        return hash_subconjunto;
    }

    public void setHash_subconjunto(double hash_subconjunto) {
        this.hash_subconjunto = hash_subconjunto;
    }

    public double getSoma_hash() {
        return soma_hash;
    }

    public void setSoma_hash(double soma_hash) {
        this.soma_hash = soma_hash;
    }

    public boolean isOrdenado() {
        return ordenado;
    }

    public void setOrdenado(boolean ordenado) {
        this.ordenado = ordenado;
    }

    @Override
    public String toString() {
        if (ordenado) {
            return "SubConjunto [lista_ordenada=" + lista_ordenada.toString() + ", hash_subconjunto="
                    + hash_subconjunto + ", soma_hash=" + soma_hash + ", ordenado=" + ordenado + "]";

        } else {
            return "SubConjunto [lista=" + lista + ", hash_subconjunto="
                    + hash_subconjunto + ", soma_hash=" + soma_hash + ", ordenado=" + ordenado + "]";
        }
    }
}
