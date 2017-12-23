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
public class Reta implements Comparable<Reta> {

    /*
	 * Linhas Retas • Matematicamente, uma reta pode ser descrita como:
	 * y = mx + b • O parâmetro m é o coeficiente angular, relacionado ao ângulo que
	 * a reta faz com o eixo x. – Para m <=1, a reta faz um ângulo entre 0º e 45º
	 * com o eixo x. – Para m >1, a reta faz um ângulo entre 45º e 90º com o eixo x.
	 * • O coeficiente linear b dá o valor no eixo y que é cruzado pela reta. •
	 * Dados os pontos no plano P1 e P2, pode-se obter m e b, ou seja, a equação da
	 * reta que passa pelos pontos.
     */
    public Ponto p1;
    public Ponto p2;
    public double a;
    public double b;
    public double c;
    public double hash_reta;

    // y = mx + b
    public double m;
    public double coeficienteB;

    public Reta() {
        // TODO Auto-generated constructor stub
    }

    public Reta(Ponto p1, Ponto p2) {
        this.p1 = p1;
        this.p2 = p2;

        this.a = p1.y - p2.y;
	this.b = p2.x - p1.x;
	this.c = (p1.x * p2.y) - (p2.x * p1.y);
        
        this.m = -(a / b);
        // b = y1 - mx1
        this.coeficienteB = ((m * p1.x) - p1.y) * (-1);
        this.hash_reta = this.geraHashReta(a, b, c);

    }

    public Reta(double a, double b, double c) {

        this.a = a;
        this.b = b;
        this.c = c;

        // http://blogmatematicarlos.blogspot.com.br/2014/07/como-transformar-uma-equacao-geral-da.html
        this.m = -(a / b);
        this.coeficienteB = -(c / b);

        this.hash_reta = this.geraHashReta(a, b, c);
    }

    public double coefAng(Reta r) {
        // y = x . & onde & = (y2-y1) / (x2-x1)
        // na forma: 1.y + CoefAng.x + CoefLin = 0
        return ((r.getP2().getY() - r.getP1().getY()) / (r.getP2().getX() - r.getP1().getX()));
    }

    public double f(Reta r, double x) {
        // retorno e´ y em funcao fr de x
        // (Y - y1) / (y2-y1) = (X - x1)/(x2 - x1)
        return (r.getP1().getY() + (((r.getP2().getY() - r.getP1().getY()) * (x - r.getP1().getX()))
                / (r.getP2().getX() - r.getP1().getX())));
    }

    public double coefLin(Reta r) {
        // coeficiente linear: onde esta y quando x e´0
        // na forma: y + CoefAng x + CoefLin = 0
        return f(r, 0);
    }

    public Ponto getP1() {
        return p1;
    }

    public void setP1(Ponto p1) {
        this.p1 = p1;
    }

    public Ponto getP2() {
        return p2;
    }

    public void setP2(Ponto p2) {
        this.p2 = p2;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public double getM() {
        return m;
    }

    public void setM(double m) {
        this.m = m;
    }

    public double getCoeficienteB() {
        return coeficienteB;
    }

    public void setCoeficienteB(double coeficienteB) {
        this.coeficienteB = coeficienteB;
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
        Reta other = (Reta) obj;
        if (Double.doubleToLongBits(a) != Double.doubleToLongBits(other.a)) {
            return false;
        }
        if (Double.doubleToLongBits(b) != Double.doubleToLongBits(other.b)) {
            return false;
        }
        return true;
    }

    public double geraHashReta(double x, double y, double z) {

        double resultado = x * 7 + y * 3 + z * 13;

        return resultado % 13;

    }

    @Override
    public int compareTo(Reta r) {

        if (a < r.a) {
            return -1;
        } else if (a == r.a) {
            if (b < r.b) {
                return -1;
            } else if (b == r.b) {
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
        return "Reta{" + "p1=" + p1 + ", p2=" + p2 + ", a=" + a + ", b=" + b + ", c=" + c + ", hash_reta=" + hash_reta + ", m=" + m + ", coeficienteB=" + coeficienteB + '}';
    }
}
