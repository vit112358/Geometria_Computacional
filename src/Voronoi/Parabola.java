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

public class Parabola {

    public static int IS_FOCUS = 0;
    public static int IS_VERTEX = 1;

    int type;
    Point point; //se é centro de uma parabola
    Borda edge; // se é vértice
    Evento event; // uma parábola com centro pode desaparecer em um evento de círculo

    Parabola parent;
    Parabola child_left;
    Parabola child_right;

    public Parabola() {
        type = IS_VERTEX;
    }

    public Parabola(Point p) {
        point = p;
        type = IS_FOCUS;
    }

    public void setLeftChild(Parabola p) {
        child_left = p;
        p.parent = this;
    }

    public void setRightChild(Parabola p) {
        child_right = p;
        p.parent = this;
    }

    @Override
    public String toString() {
        if (type == IS_FOCUS) {
            return "Focus at " + point;
        } else {
            return "Vertex/Edge beginning at " + edge.inicio;
        }
    }

    // Retorna o local mais próximo do lado esquerdo (foco da parábola)
    public static Parabola getLeft(Parabola p) {
        return getLeftChild(getLeftParent(p));
    }

    //Retorna o lugar certo mais próximo (foco da parábola)
    public static Parabola getRight(Parabola p) {
        return getRightChild(getRightParent(p));
    }

    // retorna o pai mais próximo à esquerda
    public static Parabola getLeftParent(Parabola p) {
        Parabola parent = p.parent;
        if (parent == null) {
            return null;
        }
        Parabola last = p;
        while (parent.child_left == last) {
            if (parent.parent == null) {
                return null;
            }
            last = parent;
            parent = parent.parent;
        }
        return parent;
    }

    // retorna o pai mais próximo à direita
    public static Parabola getRightParent(Parabola p) {
        Parabola parent = p.parent;
        if (parent == null) {
            return null;
        }
        Parabola last = p;
        while (parent.child_right == last) {
            if (parent.parent == null) {
                return null;
            }
            last = parent;
            parent = parent.parent;
        }
        return parent;
    }

    //Retorna a parabola mais próximo (foco de outra parábola) para a esquerda
    public static Parabola getLeftChild(Parabola p) {
        if (p == null) {
            return null;
        }
        Parabola child = p.child_left;
        while (child.type == IS_VERTEX) {
            child = child.child_right;
        }
        return child;
    }

    // retorna a parabola mais próximo (foco de outra parábola) à direita
    public static Parabola getRightChild(Parabola p) {
        if (p == null) {
            return null;
        }
        Parabola child = p.child_right;
        while (child.type == IS_VERTEX) {
            child = child.child_left;
        }
        return child;
    }

}
