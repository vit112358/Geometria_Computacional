/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Voronoi;

/**
 *
 * @author Vitor
 */

import Modelo.Point;

// um evento é um evento de ponto ou círculo para a linha de varredura para processar
public class Evento implements Comparable<Evento> {

    // um evento do ponto é quando o ponto é um site
    public static int SITE_EVENT = 0;

    // um evento em círculo é quando o ponto é um vértice do diagrama / parábola voronoi
    public static int CIRCLE_EVENT = 1;

    Point p;
    int type;
    Parabola arc; //apenas se evento de círculo

    public Evento(Point p, int type) {
        this.p = p;
        this.type = type;
        arc = null;
    }

    @Override
    public int compareTo(Evento other) {
        return this.p.compareTo(other.p);
    }

}
