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
import java.util.Vector;

public class Poligono {

    public Vector<Ponto> pontos;
    public double hash_poligono;
    private boolean eSentidoHorario;

    public boolean iseSentidoHorario() {
        return eSentidoHorario;
    }

    public void seteSentidoHorario(boolean eSentidoHorario) {
        this.eSentidoHorario = eSentidoHorario;
    }

    public Poligono() {
        pontos = new Vector<>();
    }

    public Poligono(Ponto pts[]) {
        this();
        for (int i = 0; i < pts.length; i++) {
            pontos.addElement(new Ponto(pts[i].x, pts[i].y));
        }
        this.hash_poligono = this.geraHashPoligono(pontos);
    }

    /**
     * Constroi um poligono a partir de um vetor (Vector) de pontos.
     */
    public Poligono(Vector<Ponto> v) {
        this();
        for (int i = 0; i < v.size(); i++) {
            pontos.addElement(new Ponto(v.get(i).x, v.get(i).y));
        }
        this.hash_poligono = this.geraHashPoligono(v);
    }

    /**
     * Insere um ponto como ultimo vertice do poligono.
     */
    public void insereVertice(Ponto a) {
        pontos.addElement(new Ponto(a.x, a.y));
    }

    /**
     * Insere um ponto a partir de suas coordenadas.
     */
    public void insereVertice(int x, int y) {
        insereVertice(new Ponto(x, y));
    }

    /**
     * Insere um ponto em uma dada posicao do poligono.
     */
    public void insereVerticeEm(Ponto p, int ind) {
        pontos.insertElementAt(p, ind);
    }

    /**
     * Remove vertice de uma dada posicao do poligono, devolvendo-o.
     */
    public Ponto removeVerticeDe(int ind) {
        Ponto x = new Ponto(pontos.elementAt(ind).x, pontos.elementAt(ind).y);
        pontos.removeElementAt(ind);
        return x;
    }

    /**
     * Remove o ultimo vertice do poligono, devolvendo-o.
     */
    public Ponto removeVertice() {
        return pontos.remove(pontos.size());
    }

    /**
     * Retorna um vertice de uma dada posicao do poligono.
     */
    public Ponto verticeDe(int ind) {
        return pontos.get(ind);
    }

    /**
     * Retorna o ultimo vertice do poligono.
     */
    public Ponto ultimoVertice() {
        return (verticeDe(tamanho() - 1));
    }

    /**
     * Retorna a quantidade de vertices do poligono.
     */
    public int tamanho() {
        return pontos.size();
    }

    public Vector<Ponto> getPontos() {
        return pontos;
    }

    public Ponto get(int i) {
        if (i >= 0 && i < tamanho()) {
            return pontos.get(i);
        } else {
            return null;
        }
    }

    /**
     * Constroi um poligono a partir de um vetor (array) de pontos.
     */
    public void setPontos(Vector<Ponto> pontos) {
        this.pontos = pontos;
    }

    public double getHash_poligono() {
        return hash_poligono;
    }

    public void setHash_poligono(double hash_poligono) {
        this.hash_poligono = hash_poligono;
    }

    public double geraHashPoligono(Vector<Ponto> pontos) {
        double resultado = 0;
        for (int i = 0; i < pontos.size(); i++) {
            resultado += pontos.get(i).x * 7 + pontos.get(i).y * 3;
        }
        resultado = resultado % 13;
        return 1;
    }

    @Override
    public String toString() {
        return "Poligono [pontos=" + pontos.toString() + ", hash_poligono=" + hash_poligono + "]";
    }

    /**
     * Determina se a ordem dos vértices na lista é no sentido horário.
     *
     * @return true, if a ordem é no sentido horário, false se no sentido
     * anti-horário
     */
    public boolean eSentidoHorario() {
        double sum = 0;

        for (int i = 0; i < this.tamanho(); i++) {
            sum += (pontos.get((i + 1) % tamanho()).getX() - pontos.get(i).getX())
                    * (pontos.get((i + 1) % tamanho()).getY() + pontos.get(i).getY());
        }

        eSentidoHorario = (sum > 0);
        return eSentidoHorario;
    }
}
