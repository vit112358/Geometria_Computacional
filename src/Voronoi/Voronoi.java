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
import java.util.List;
import java.util.PriorityQueue;
import java.util.ArrayList;
import java.util.Random;
import Modelo.Point;

public class Voronoi {

    public List<Point> pontos;
    public List<Borda> bordas; // bordas no diagrama de Voronoi
    public PriorityQueue<Evento> events; // a fila de prioridade representa a linha de varredura
    public Parabola arvoreLinha; // árvore de pesquisa binária representa linha de praia

    // tamanho da janelo do Windows
    public double width = 1;
    public double height = 1;

    public double coordYatualLinha; // atual y-coord da linha de varredura

    public Voronoi(List<Point> pontos) {
        // atualiza a lista de pontos da classe
        this.pontos = pontos;

        // cria uma lista de bordas (linha que irá dividir os pontos)
        bordas = new ArrayList<>();

        // função inicial
        generateVoronoi();
    }

    private void generateVoronoi() {
        // PriorityQueue utiliza ou ordenamento natural ou um ordenamento definido
        // cria uma fila de evento com prioridade - compara pela coordenada y = arvore
        events = new PriorityQueue<>();
        pontos.forEach((p) -> {
            events.add(new Evento(p, Evento.SITE_EVENT));
        });

        // eventos de processo (linha de varredura), enquanto n for vazia
        int count = 0;
        while (!events.isEmpty()) {
            // remove a de maior prioridade, menor y
            Evento e = events.remove();
            coordYatualLinha = e.p.y; // atualiza a coord atual y
            count++; // atualiza contador
            if (Evento.SITE_EVENT == e.type) { // verifica se é um evento do ponto
                handleSite(e.p); // evento do ponto - cria a parabola
            } else {
                handleCircle(e); // evento do circulo
            }
        }
        // atualiza a coord y da linha
        coordYatualLinha = width + height;

        // fecha todas as arestas penduradas
        endEdges(arvoreLinha);

        // livrar-se das linhas que passam da tela
        // aquo contem todas as bordar, ponto inicial e ponto final
        bordas.stream().filter((e) -> (e.vizinho != null)).map((e) -> {
            e.inicio = e.vizinho.fim;
            return e;
        }).forEachOrdered((e) -> {
            e.vizinho = null;
        });
    }

    // terminar todas as bordas inacabadas
    private void endEdges(Parabola p) {
        if (p.type == Parabola.IS_FOCUS) {
            p = null;
            return;
        }
        // retorna a coordenada x atual de uma borda inacabada
        double x = getXofEdge(p);
        p.edge.fim = new Point(x, p.edge.slope * x + p.edge.yint);
        bordas.add(p.edge);

        endEdges(p.child_left);
        endEdges(p.child_right);

        p = null;
    }

    // processo de evento do ponto
    private void handleSite(Point p) {
        // caso base - 1 vez
        if (arvoreLinha == null) { // cria uma parabola, este ponto é centro de uma parabola
            arvoreLinha = new Parabola(p);
            return;
        }

        // encontre a parábola na linha da praia logo acima p (ponto ou vertice)
        Parabola par = getParabolaByX(p.x);
        if (par.event != null) { //
            events.remove(par.event);
            par.event = null;
        }

        // criar uma nova borda pendurada; divide o foco da parábola é p, e falo que é um vertice, linha
        Point start = new Point(p.x, getY(par.point, p.x));
        Borda el = new Borda(start, par.point, p);
        Borda er = new Borda(start, p, par.point);
        el.vizinho = er;
        er.vizinho = el;
        par.edge = el;
        par.type = Parabola.IS_VERTEX;

        // substitua parabola par original com p0, p1, p2
        Parabola p0 = new Parabola(par.point);
        Parabola p1 = new Parabola(p);
        Parabola p2 = new Parabola(par.point);

        par.setLeftChild(p0);
        par.setRightChild(new Parabola());
        par.child_right.edge = er;
        par.child_right.setLeftChild(p1);
        par.child_right.setRightChild(p2);

        // adiciona evento de círculo se os centros a, b, c se encontram no mesmo
        // círculo
        checkCircleEvent(p0);
        checkCircleEvent(p2);
    }

    // evento de círculo de processo
    private void handleCircle(Evento e) {

        // encontre p0, p1, p2 que geram esse evento da esquerda para a direita
        Parabola p1 = e.arc;
        Parabola xl = Parabola.getLeftParent(p1);
        Parabola xr = Parabola.getRightParent(p1);
        Parabola p0 = Parabola.getLeftChild(xl);
        Parabola p2 = Parabola.getRightChild(xr);

        // remover eventos associados, uma vez que os pontos serão alterados
        if (p0.event != null) {
            events.remove(p0.event);
            p0.event = null;
        }
        if (p2.event != null) {
            events.remove(p2.event);
            p2.event = null;
        }

        Point p = new Point(e.p.x, getY(p1.point, e.p.x)); // novo vértice

        // bordas de extremidade!
        xl.edge.fim = p;
        xr.edge.fim = p;
        bordas.add(xl.edge);
        bordas.add(xr.edge);

        // comece uma nova bisectriz (borda) desse vértice no qual a borda original é
        // mais alto na árvore
        Parabola higher = new Parabola();
        Parabola par = p1;
        while (par != arvoreLinha) {
            par = par.parent;
            if (par == xl) {
                higher = xl;
            }
            if (par == xr) {
                higher = xr;
            }
        }
        higher.edge = new Borda(p, p0.point, p2.point);

        // apague p1 e pai (limite) da linha da praia
        Parabola gparent = p1.parent.parent;
        if (p1.parent.child_left == p1) {
            if (gparent.child_left == p1.parent) {
                gparent.setLeftChild(p1.parent.child_right);
            }
            if (gparent.child_right == p1.parent) {
                gparent.setRightChild(p1.parent.child_right);
            }
        } else {
            if (gparent.child_left == p1.parent) {
                gparent.setLeftChild(p1.parent.child_left);
            }
            if (gparent.child_right == p1.parent) {
                gparent.setRightChild(p1.parent.child_left);
            }
        }

        Point op = p1.point;
        p1.parent = null;
        p1 = null;

        checkCircleEvent(p0);
        checkCircleEvent(p2);
    }

    // adiciona evento de círculo se os focos a, b, c se encontram no mesmo círculo
    private void checkCircleEvent(Parabola b) {
        // retorna o pai mais próximo à esquerda
        Parabola lp = Parabola.getLeftParent(b);
        // retorna o pai mais próximo à direita
        Parabola rp = Parabola.getRightParent(b);

        if (lp == null || rp == null) {
            return;
        }

        // Retorna a parabola mais próximo (foco de outra parábola) para a esquerda
        Parabola a = Parabola.getLeftChild(lp);
        // Retorna a parabola mais próximo (foco de outra parábola) para a direita
        Parabola c = Parabola.getRightChild(rp);

        if (a == null || c == null || a.point == c.point) {
            return;
        }
        //area
        if (ccw(a.point, b.point, c.point) != 1) {
            return;
        }

        // As bordas se cruzarão para formar um vértice para um evento de círculo
        // Retorna a intersecção das linhas de com os 2 vetores
        Point start = getEdgeIntersection(lp.edge, rp.edge);
        if (start == null) {
            return;
        }

        // raio de cálculo
        double dx = b.point.x - start.x;
        double dy = b.point.y - start.y;
        double d = Math.sqrt((dx * dx) + (dy * dy));
        if (start.y + d < coordYatualLinha) {
            return; // deve ser após a linha de varredura
        }
        Point ep = new Point(start.x, start.y + d);

        // adicionar evento de círculo
        Evento e = new Evento(ep, Evento.CIRCLE_EVENT);
        e.arc = b;
        b.event = e;
        events.add(e);
    }

    // adicione a primeira coisa do círculo que aprendemos nesta classe: P
    public int ccw(Point a, Point b, Point c) {
        double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (area2 < 0) {
            return -1;
        } else if (area2 > 0) {
            return 1;
        } else {
            return 0;
        }
    }

    // Retorna a intersecção das linhas de com os vetores a e b
    private Point getEdgeIntersection(Borda a, Borda b) {

        if (b.slope == a.slope && b.yint != a.yint) {
            return null;
        }

        double x = (b.yint - a.yint) / (a.slope - b.slope);
        double y = a.slope * x + a.yint;

        return new Point(x, y);
    }

    // retorna a coordenada x atual de uma borda inacabada
    private double getXofEdge(Parabola par) {
        // encontrar uma interseção de duas parábolas

        // Retorna a parabola mais próximo (foco de outra parábola) para a esquerda
        Parabola left = Parabola.getLeftChild(par);
        // Retorna a parabola mais próximo (foco de outra parábola) para a direita
        Parabola right = Parabola.getRightChild(par);

        Point p = left.point;
        Point r = right.point;

        double dp = 2 * (p.y - coordYatualLinha);
        double a1 = 1 / dp;
        double b1 = -2 * p.x / dp;
        double c1 = (p.x * p.x + p.y * p.y - coordYatualLinha * coordYatualLinha) / dp;

        double dp2 = 2 * (r.y - coordYatualLinha);
        double a2 = 1 / dp2;
        double b2 = -2 * r.x / dp2;
        double c2 = (r.x * r.x + r.y * r.y - coordYatualLinha * coordYatualLinha) / dp2;

        double a = a1 - a2;
        double b = b1 - b2;
        double c = c1 - c2;

        double disc = b * b - 4 * a * c;
        double x1 = (-b + Math.sqrt(disc)) / (2 * a);
        double x2 = (-b - Math.sqrt(disc)) / (2 * a);

        double ry;
        if (p.y > r.y) {
            ry = Math.max(x1, x2);
        } else {
            ry = Math.min(x1, x2);
        }

        return ry;
    }

    // Retorna a parábola acima desta coordenada x na linha da praia
    private Parabola getParabolaByX(double xx) {
        Parabola par = arvoreLinha; // recebe a parabola atual
        double x = 0;
        while (par.type == Parabola.IS_VERTEX) {
            x = getXofEdge(par);
            if (x > xx) {
                par = par.child_left;
            } else {
                par = par.child_right;
            }
        }
        return par;
    }

    // encontre a coordenada y correspondente para x na parábola com foco p
    private double getY(Point p, double x) {
        // determinar equação para a parábola em torno do foco p
        double dp = 2 * (p.y - coordYatualLinha);
        double a1 = 1 / dp;
        double b1 = -2 * p.x / dp;
        double c1 = (p.x * p.x + p.y * p.y - coordYatualLinha * coordYatualLinha) / dp;
        return (a1 * x * x + b1 * x + c1);
    }

    public static void main(int args) {

        // numero de pontos que serão gerados aleatoriamente
        int N = args;
        // lista que irá conter os pontos
        ArrayList<Point> points = new ArrayList<>();

        Random gen = new Random();
        // add os pontos a lista
        for (int i = 0; i < N; i++) {
            double x = gen.nextDouble();
            double y = gen.nextDouble();
            points.add(new Point(x, y));
        }
        // gera o diagrama
        Voronoi diagrama = new Voronoi(points);

        // desenha resultados - pontos
        StdDraw.setPenRadius(.005);
        points.forEach((p) -> {
            StdDraw.point(p.x, p.y);
        });
        // desenha resultados - linhas
        StdDraw.setPenRadius(.002);
        diagrama.bordas.forEach((e) -> {
            StdDraw.line(e.inicio.x, e.inicio.y, e.fim.x, e.fim.y);
        });

    }

    public static void main(String[] args) {

        // lista que irá conter os pontos
        ArrayList<Point> points = new ArrayList<>();

        points.add(new Point(0.10, 0.10));
        points.add(new Point(0.10, 0.70));
        points.add(new Point(0.60, 0.40));
        //points.add(new Point(0.40, 0.60));
        //points.add(new Point(0.60, 0.90));

        // gera o diagrama
        Voronoi diagrama = new Voronoi(points);

        // desenha resultados - pontos
        StdDraw.setPenRadius(.005);
        points.forEach((p) -> {
            StdDraw.point(p.x, p.y);
        });
        // desenha resultados - linhas
        StdDraw.setPenRadius(.002);
        diagrama.bordas.forEach((e) -> {
            StdDraw.line(e.inicio.x, e.inicio.y, e.fim.x, e.fim.y);
        });
    }
}
