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
public class Point implements Comparable<Point> {

    public double x;
    public double y;

    public Point(double x0, double y0) {
        x = x0;
        y = y0;
    }

    @Override
    public int compareTo(Point other) {
        if (this.y == other.y) {
            if (this.x == other.x) {
                return 0;
            } else if (this.x > other.x) {
                return 1;
            } else {
                return -1;
            }
        } else if (this.y > other.y) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

}
