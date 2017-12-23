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
import java.util.Collection;

public class Circulo implements Comparable<Circulo> {

    private static final double MULTIPLICATIVE_EPSILON = 1 + 1e-14;
    public Ponto centro;
    public double raio;
    public double hash_reta;

    public Circulo() {

    }

    public Circulo(Ponto centro, double raio) {
        this.centro = centro;
        this.raio = raio;
    }

    // ver classe Quadrado
    public String toString() {
        return "C:" + centro + " R:" + raio;
    }

    public Ponto getCentro() {
        return centro;
    }

    public void setCentro(Ponto centro) {
        this.centro = centro;
    }

    public double getRaio() {
        return raio;
    }

    public void setRaio(int raio) {
        this.raio = raio;
    }

    @Override
    public int compareTo(Circulo c) {
        double A1 = this.raio * this.raio * 3.14;
        double A2 = c.raio * c.raio * 3.14;
        if (A1 < A2) {
            return -1;
        } else if (A1 == A2) {
            return 0;
        } else {
            return 1;
        }

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((centro == null) ? 0 : centro.hashCode());
        long temp;
        temp = Double.doubleToLongBits(raio);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    public boolean contains(Ponto p) {
        return centro.distance(p) <= raio * MULTIPLICATIVE_EPSILON;
    }

    public boolean contains(Collection<Ponto> ps) {
        for (Ponto p : ps) {
            if (!contains(p)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Circulo other = (Circulo) obj;

        if (centro == null) {
            if (other.centro != null) {
                return false;
            }
        } else if (raio == other.raio) {
            if (centro.equals(other)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
        return false;
    }

}
