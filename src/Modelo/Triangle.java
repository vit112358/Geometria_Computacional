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
public class Triangle implements Cloneable {

    /**
     * Vertices *
     */
    protected Ponto a, b, c;

    /**
     * Os ponteiros para triângulos adjacentes *
     */
    protected Triangle adjAB, adjBC, adjAC;

    /**
     * Inicializa um triângulo pelos seus vértices.       * NB: altera a ordem
     * dos vértices       * para fazê-lo no sentido anti-horário.
     *
     * @param A Primeiro vertice
     * @param B Segundo vertice
     * @param C Terceiro vertice
     */
    public Triangle(Ponto A, Ponto B, Ponto C) {
        a = A;
        b = B;
        c = C;
        adjAB = adjAC = adjBC = null;

        // Faça o triângulo no sentido anti-horário
        // essencial para triangulação!
        double sum = (b.getX() - a.getX())
                * (b.getY() + a.getY())
                + (c.getX() - b.getX())
                * (c.getY() + b.getY())
                + (a.getX() - c.getX())
                * (a.getY() + c.getY());

        if (sum > 0) {
            Ponto temp = a;
            a = b;
            b = temp;
        }
    }

    /**
     * Determina se um ponto está dentro do triângulo.
     *
     * @param p Ponto
     * @return true, se o ponto estiver dentro do triângulo, false não está
     */
    public boolean pointInside(Ponto p) {
        return pointInside(p, true);
    }

    /**
     * Determina se um ponto está dentro do triângulo.
     *
     * @param p O Ponto
     * @param strict true, se o ponto não pode estar na fronteira, caso
     * contrário
     * @return true, se o ponto estiver dentro do triângulo, false caso
     * contrário
     */
    public boolean pointInside(Ponto p, boolean strict) {

        if (strict) {

            boolean l1 = Ponto.isLeftTurn(a, b, p);
            boolean l2 = Ponto.isLeftTurn(b, c, p);
            boolean l3 = Ponto.isLeftTurn(c, a, p);

            if (l1 && l2 && l3) {
                return true;
            }

            boolean r1 = Ponto.isRightTurn(a, b, p);
            boolean r2 = Ponto.isRightTurn(b, c, p);
            boolean r3 = Ponto.isRightTurn(c, a, p);

            if (r1 && r2 && r3) {
                return true;
            }

            return false;

        } else {

            boolean l1 = !Ponto.isRightTurn(a, b, p);
            boolean l2 = !Ponto.isRightTurn(b, c, p);
            boolean l3 = !Ponto.isRightTurn(c, a, p);

            if (l1 && l2 && l3) {
                return true;
            }

            boolean r1 = !Ponto.isLeftTurn(a, b, p);
            boolean r2 = !Ponto.isLeftTurn(b, c, p);
            boolean r3 = !Ponto.isLeftTurn(c, a, p);

            if (r1 && r2 && r3) {
                return true;
            }

            return false;
        }
    }

    /**
     * Retorna o vértice 'A' do triângulo ABC.
     *
     * @return The vertex
     */
    public Ponto getA() {
        return a;
    }

    /**
     * Retorna o vértice 'B' do triângulo ABC.
     *
     * @return The vertex
     */
    public Ponto getB() {
        return b;
    }

    /**
     * Retorna o vértice 'C' do triângulo ABC.
     *
     * @return The vertex
     */
    public Ponto getC() {
        return c;
    }

    @Override
    public Object clone() {

        Triangle res = new Triangle(a, b, c);
        res.adjAB = this.adjAB;
        res.adjBC = this.adjBC;
        res.adjAC = this.adjAC;
        return res;
    }

    @Override
    public boolean equals(Object o2) {

        if (!(o2 instanceof Triangle)) {
            return false;
        }

        Triangle t2 = (Triangle) o2;

        return this.getA().equals(t2.getA())
                && this.getB().equals(t2.getB())
                && this.getC().equals(t2.getC())
                || this.getA().equals(t2.getB())
                && this.getB().equals(t2.getC())
                && this.getC().equals(t2.getA())
                || this.getA().equals(t2.getC())
                && this.getB().equals(t2.getA())
                && this.getC().equals(t2.getB())
                || this.getA().equals(t2.getC())
                && this.getB().equals(t2.getB())
                && this.getC().equals(t2.getA())
                || this.getA().equals(t2.getB())
                && this.getB().equals(t2.getA())
                && this.getC().equals(t2.getC())
                || this.getA().equals(t2.getA())
                && this.getB().equals(t2.getC())
                && this.getC().equals(t2.getB());
    }
}
