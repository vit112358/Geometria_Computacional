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
public class Ponto implements Comparable<Ponto> {

    public double x;
    public double y;
    public double hash_ponto;

    public Ponto() {

    }

    public Ponto(double x, double y) {
        this.x = x;
        this.y = y;
        this.hash_ponto = this.geraHashPonto(x, y);
    }

    /**
     * Calcula o produto cruzado      * de dois vetores (p0, p1) e (p0, p2)
     *      * definido por três pontos p0, p1 e p2.
     *
     * @param p0 The first point
     * @param p1 The second point
     * @param p2 The third point
     * @return O produto cruzado
     */
    private static double crossProduct(Ponto p0, Ponto p1, Ponto p2) {
        return (p1.getX() - p0.getX()) * (p2.getY() - p0.getY())
                - (p2.getX() - p0.getX()) * (p1.getY() - p0.getY());
    }

    /**
     * Determina se dois vetores (p0, p1) e (p1, p2)      * definido por três
     * pontos p0, p1 e p2 fazem uma curva esquerda.
     *
     *
     * @param p0 The first point
     * @param p1 The second point
     * @param p2 The third point
     * @return verdade, se o turno for deixado, falso caso contrário
     */
    public static boolean isLeftTurn(Ponto p0, Ponto p1, Ponto p2) {
        return (crossProduct(p0, p1, p2) > 0);
    }

    /**
     * Determina se dois vetores (p0, p1) e (p1, p2)      * definido por três
     * pontos p0, p1 e p2 fazem uma curva à direita.
     *
     * @param p0 The first point
     * @param p1 The second point
     * @param p2 The third point
     * @return true, se o turno estiver certo, falso caso contrário
     */
    public static boolean isRightTurn(Ponto p0, Ponto p1, Ponto p2) {
        return (crossProduct(p0, p1, p2) < 0);
    }

    public double getX() {
        return x;
    }

    public void setX(double d) {
        this.x = d;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getHash_ponto() {
        return hash_ponto;
    }

    public void setHash_ponto(double hash_ponto) {
        this.hash_ponto = hash_ponto;
    }

    public double distance(Ponto p) {
        return Math.hypot(x - p.x, y - p.y);
    }

    // Signed area / determinant thing
    public double cross(Ponto p) {
        return x * p.y - y * p.x;
    }

    public Ponto subtract(Ponto p) {
        return new Ponto(x - p.x, y - p.y);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(hash_ponto);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(x);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
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
        Ponto other = (Ponto) obj;
        if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x)) {
            return false;
        }
        if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y)) {
            return false;
        }
        return true;
    }

    public double geraHashPonto(double x, double y) {

        double resultado = x * 7 + y * 3;

        return resultado % 10;

    }

    @Override
    public int compareTo(Ponto o) {

        if (x < o.x) {
            return -1;
        } else if (x == o.x) {
            if (y < o.y) {
                return -1;
            } else if (y == o.y) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }

    }

    @Override
    public String toString() {
        return "Ponto [x=" + x + ", y=" + y + "]";
    }
}
