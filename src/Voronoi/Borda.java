/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Voronoi;

import Modelo.Point;

/**
 *
 * @author Vitor
 */
public class Borda {

    public Point inicio;
    public Point fim;
    public Point site_esq;
    public Point site_dir;
    public Point direcao; // borda é realmente um vetor normal para os pontos esquerdo e direito

    Borda vizinho; //refazendo a borda porém na direção contrária
    double slope;
    double yint;

    public Borda(Point first, Point left, Point right) {
        inicio = first;
        site_esq = left;
        site_dir = right;
        direcao = new Point(right.y - left.y, -(right.x - left.x));
        fim = null;
        slope = (right.x - left.x) / (left.y - right.y);
        Point mid = new Point((right.x + left.x) / 2, (left.y + right.y) / 2);
        yint = mid.y - slope * mid.x;
    }

    @Override
    public String toString() {
        return inicio + ", " + fim;
    }
}
