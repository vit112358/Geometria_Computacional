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
import java.util.HashMap;

public class Conjunto {

    public HashMap<Double, SubConjunto> conjunto;

    public Conjunto() {
        this.conjunto = new HashMap<>();
    }

    public HashMap<Double, SubConjunto> getConjunto() {
        return conjunto;
    }

    public void setConjunto(HashMap<Double, SubConjunto> conjunto) {
        this.conjunto = conjunto;
    }
}
