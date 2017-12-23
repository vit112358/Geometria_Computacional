/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Problemas;

/**
 *
 * @author Vitor
 */
import java.awt.Point;
import java.util.ArrayList;

import Controle.TAD_Reta;
import Controle.TAD_SegmentoReta;
import Modelo.Circulo;
import Modelo.Poligono;
import Modelo.Ponto;
import Modelo.Reta;
import Modelo.SegmentoDeReta;

public class Problemas_Classicos {

    public static double PI = 3.141516;
    //TAD_SegmentoReta tad = new TAD_SegmentoReta();

    public Problemas_Classicos() {
        // TODO Auto-generated constructor stub

    }

    /**
     * Esta função calcula a distância entre 2 pontos
     *
     * @param A: Primeiro ponto a ser calculado a distância;
     * @param B: Segundo ponto ser calculado a distância;
     * @return o valor da distância entre dois Pontos retornado pela fórmula
     * ((x_final-x_inicial)^2+(y_final-y_inicial)^2)^(1/2)
     *
     * Custo: O(1)
     */
    public double distanciaPontos(Ponto A, Ponto B) {
        return Math.sqrt(Math.pow((B.x - A.x), 2) + Math.pow((B.y - A.y), 2));
    }

    /**
     * Esta função calcula a área do círculo
     *
     * @param c: Círculo em questão;
     * @return o valor da área dada pela fórmula Pi*(Raio)^2 Custo O(1)
     */
    public double areaCriculo(Circulo c) {
        return PI * c.raio * c.raio;
    }

    /**
     * Esta função calcula a área do polígono, é calculado por: A(P)=1/2
     * somatório (com i=0 até n-1)(x_i*y(i+1)-x_(i+1)*y)
     *
     * @param p: Poligono em questão, na realidade o polígono é representado por
     * um vector de pontos
     * @return retorna a área do poligono
     *
     * Custo O(n)
     */
    public double areaPoligono(Poligono p) {

        double soma = 0;

        ArrayList<Ponto> aux = new ArrayList<>();

        aux.addAll(p.getPontos());

        for (int i = 0; i < aux.size() - 1; i++) {
            soma += (aux.get(i).x * aux.get(i + 1).y) - (aux.get(i + 1).x * aux.get(i).y);
        }
        soma /= 2;

        return Math.abs(soma);
    }

    /**
     * Esta função verifica a Convexidade de dado poligono 
     *
     * @param p :Poligono para ser verificado
     *
     * Custo O(1)
     */
    public boolean PredicadoPoligonoConvexo(Poligono p) {
        // Referencia:
        // http://csharphelper.com/blog/2014/07/determine-whether-a-polygon-is-convex-in-c/
        // Referencia:
        // https://pt.stackoverflow.com/questions/125730/como-verificar-se-um-pol%C3%ADgono-%C3%A9-convexo

        // Para cada conjunto de três pontos adjacentes A, B, C, encontrar o produto
        // cruzado AB · BC.
        // Se o sinal de todos os produtos cruzados é o mesmo, os ângulos são todos
        // positivos ou negativos
        // (dependendo da ordem em que os visitamos), de modo que o polígono é convexo.
        boolean got_negative = false;
        boolean got_positive = false;

        int num_points = p.tamanho();
        int B, C;

        for (int A = 0; A < num_points; A++) {
            B = (A + 1) % num_points;
            C = (B + 1) % num_points;

            double cross_product = ProdutoCruzadoPontos(p.getPontos().get(A).x, p.getPontos().get(A).y,
                    p.getPontos().get(B).x, p.getPontos().get(B).y, p.getPontos().get(C).x, p.getPontos().get(C).y);

            if (cross_product < 0) {
                got_negative = true;
            } else if (cross_product > 0) {
                got_positive = true;
            }
            if (got_negative && got_positive) {
                return false;
            }
        }

        // Se chegarmos até aqui, o polígono é convexo.
        return true;
    }

    public double ProdutoCruzadoPontos(double Ax, double Ay, double Bx, double By, double Cx, double Cy) {

        //// Retorne o produto cruzado AB x BC. O produto cruzado é um vetor
        //// perpendicular a AB
        // e BC com comprimento | AB | * | BC | * Sin (theta) e com direção dada pela
        //// regra da direita.
        // Para dois vetores no plano XY, o resultado é um vetor com os componentes X e
        //// Y 0, de modo que o componente Z
        // dá o comprimento e a direção do vetor.
        double BAx = Ax - Bx;
        double BAy = Ay - By;
        double BCx = Cx - Bx;
        double BCy = Cy - By;

        // Calculate the Z coordinate of the cross product.
        return (BAx * BCy - BAy * BCx);
    }

    /**
     * Esta função verifica se o ponto está dentro do de um dado polígono, se um
     * ponto estiver dentro do poligono, a semireta que parte dele em qualquer
     * direção corta o polígono numero impar, caso contrário cortará o polígono
     * numero par de vezes
     *
     * @param p: Poligono em questão, ele é definido como um arrayList de pontos
     * @param c: Ponto em que irá ser feito a verificação
     * @return ele retorna verdadeiro se o ponto estiver dentro do polígono, e
     * falso caso contrário;
     *
     * Custo O(n)
     */
    public boolean pontoDentroPoligono(Poligono p, Ponto c) {

        int i, j = p.getPontos().size() - 1;
        boolean oddNodes = false;

        for (i = 0; i < p.getPontos().size(); i++) {

            if ((p.getPontos().get(i).y < c.y) && (p.getPontos().get(j).y >= c.y)
                    || (p.getPontos().get(j).y < c.y) && (p.getPontos().get(i).y >= c.y)) {

                if (p.getPontos().get(i).x
                        + (c.y - p.getPontos().get(i).y) / (p.getPontos().get(j).y - p.getPontos().get(i).y)
                        * (p.getPontos().get(j).x - p.getPontos().get(i).x) < c.x) {
                    oddNodes = !oddNodes;
                }
            }
            j = i;

        }
        return oddNodes;
    }

    /**
     * Esta função verifica se um ponto está dentro ou não de uma dada
     * circunferência, se a distância de p o centro for menor que o raio ele é
     * ponto interno, caso a distancia entre o ponto for maior que o raio ele é
     * ponto externo, caso for igual ao raio ele está em cima da circunferência
     *
     * @param circulo: círculo dado;
     * @param p: Ponto dado;
     * @return a função retorna um inteiro, -1 para ponto externo, 1 para ponto
     * interno, 0 para ponto em cima da circunferência
     *
     * Custo O(1)
     */
    public int ladoCirculo(Circulo circulo, Ponto p) {

        double calculo = Math
                .sqrt((Math.pow((p.x - circulo.getCentro().x), 2) + Math.pow((p.y - circulo.getCentro().y), 2)));

        if (calculo > circulo.raio) {
            return -1; // ponto externo
        } else if (calculo < circulo.raio) {
            return 1; // ponto interno
        } else {
            return 0; // ponto esta na circunferencia
        }
    }

    /**
     * Calcula a distancia de um ponto à uma dada reta pela fórmula:
     * d=((a*x+b*y+c)^1/2)^2/(a^2+b^2)^1/2, onde a,b e c são coeficientes da
     * equação da reta, e x e y são as coordenadas de um dado ponto;
     *
     * @param p: Ponto p em questão;
     * @param r: Reta r que irá ser calculada a distância em relação à p;
     * @return distancia entre à reta r e o ponto p;
     *
     * Custo O(1)
     */
    public double distanciaPontoReta(Ponto p, Reta r) {

        double aux = (r.a * p.x) + (r.b * p.y) + r.c;

        if (aux < 0) {
            aux = aux * -1;
        }

        aux = aux / Math.sqrt(r.a * r.a + r.b * r.b);

        return aux;
    }

    /**
     * Esta função retorna o ponto mais próximo em um segmento de reta de um
     * dado ponto
     *
     * @param C Ponto P: o ponto dado;
     * @param r Reta r: reta dada;
     * @return Ponto mais próximo;
     *
     * referencia:
     * http://alunosonline.uol.com.br/matematica/equacao-geral-da-reta.html
     * https://www.geogebra.org/m/MH5e3JY4 calcula a equação reduzida do ponto
     * em relação a reta depois procura a interseção
     *
     * Custo O(1)
     */
    public Ponto pontoMaisProximoSegmentoReta1(Ponto C, Reta r) {

        double AB = this.distanciaPontos(r.p2, r.p1);
        double AC = this.distanciaPontos(C, r.p1);
        double numerador = AB * AC;

        double t = numerador / Math.pow(Math.abs(AB), 2);

        Ponto resultado = new Ponto();

        if (t > 1) { // o segundo ponto é o mais proximo
            resultado.x = r.p2.x;
            resultado.y = r.p2.y;
        } else { // traça uma reta e calcula a interseção entre elas
            // perpendicular
            double as = 0.0;
            if (r.coefAng(r) != 0) {
                as = (-1) / (r.coefAng(r));
            }

            double coefAng = as;
            double coefLinear = ((as * C.x) - C.y) * (-1);

            // r = s
            // m x + b = coefAng x + coefLinear
            // achando o x
            double x = (r.coefLin(r) - coefLinear) / (coefAng - r.coefAng(r));

            // substitui para achar o y = coefAng x + coefLinear
            double y = (coefAng * x) + coefLinear;

            Ponto aux = new Ponto();

            // direção da reta p1 -> p2
            if (r.p1.x > r.p2.x) {

                if (x > r.p1.x) {
                    aux = r.p1;
                } else {
                    aux = new Ponto(x, y);
                }

            } else // direção da reta p2 <- p1
            if (x < r.p1.x) {
                if (r.p1.x < r.p2.x) {
                    aux = r.p1;
                } else if (x > r.p1.x) {
                    aux = r.p1;
                } else {
                    aux = new Ponto(x, y);
                }
            } else {
                aux = new Ponto(x, y);
            }

            resultado = aux;
        }

        return resultado;
    }

    public Ponto intersecaoSegmentosDeReta(Reta A, Reta B) {

        Ponto k = new Ponto(A.p1.x, A.p1.y);
        Ponto l = new Ponto(A.p2.x, A.p2.y);
        Ponto m = new Ponto(B.p1.x, B.p1.y);
        Ponto n = new Ponto(B.p2.x, B.p2.y);

        double det;

        det = (n.x - m.x) * (l.y - k.y) - (n.y - m.y) * (l.x - k.x);

        if (det == 0.0) {
            return null; // não há intersecção
        }
        double s = ((n.x - m.x) * (m.y - k.y) - (n.y - m.y) * (m.x - k.x)) / det;
        double t = ((l.x - k.x) * (m.y - k.y) - (l.y - k.y) * (m.x - k.x)) / det;

        Ponto Pi = new Ponto();

        Pi.x = k.x + (l.x - k.x) * s;
        Pi.y = k.y + (l.y - k.y) * s;

        return Pi; // há intersecção
    }

    public Ponto intersecao(Reta self, Reta other) {

        double resultado = (other.p2.x - other.p1.x) * (self.p2.y - self.p1.y)
                - (other.p2.y - other.p1.y) * (self.p2.x - self.p1.x);

        if (resultado == 0.0) {
            return null;
        }

        double s = ((other.p2.x - other.p1.x) * (other.p1.y - self.p1.y)
                - (other.p2.y - other.p1.y) * (other.p1.x - self.p1.x)) / resultado;

        Ponto Pi = new Ponto(0, 0);
        Pi.x = self.p1.x + (self.p2.x - self.p1.x) * s;
        Pi.y = self.p1.y + (self.p2.y - self.p1.y) * s;

        return Pi;
    }

    /**
     * Esta função calcula a intersecção entre dois segmentos de reta
     *
     * @param A: reta A, primeira reta que será analisada
     * @param B: reta B, segunda reta que será analisada
     * @return retorna null ou o ponto de intersecção
     *
     * http://www.inf.pucrs.br/~pinho/CG/Aulas/OpenGL/Interseccao/CalcIntersec.html
     *
     * Custo O(1)
     *
     */
    public boolean intersecaoSegmentosDeReta1(Reta A, Reta B) {

        Ponto p1 = A.p1;
        Ponto p2 = A.p2;
        Ponto p3 = B.p1;
        Ponto p4 = B.p2;

        double d1 = direction(p3, p4, p1);
        double d2 = direction(p3, p4, p2);
        double d3 = direction(p1, p2, p3);
        double d4 = direction(p1, p2, p4);

        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) && ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
            return true;
        } else if (d1 == 0 && onSegment(p3, p4, p1)) {
            return true;
        } else if (d2 == 0 && onSegment(p3, p4, p2)) {
            return true;
        } else if (d3 == 0 && onSegment(p1, p2, p3)) {
            return true;
        } else if (d4 == 0 && onSegment(p1, p2, p4)) {
            return true;
        } else {
            return false;
        }
    }

    // retorna a direção O(1)
    private static double direction(Ponto p0, Ponto p1, Ponto p2) {
        return ((p2.getX() - p0.getX()) * (p1.getY() - p0.getY()) - (p2.getY() - p0.getY()) * (p1.getX() - p0.getX()));
    }

    // segmento dos pontos O(1)
    private static boolean onSegment(Ponto pi, Ponto pj, Ponto pk) {
        return (Math.min(pi.getX(), pj.getX()) <= pk.getX() && pk.getX() <= Math.max(pi.getX(), pj.getX())
                && Math.min(pi.getY(), pj.getY()) <= pk.getY() && pk.getY() <= Math.max(pi.getY(), pj.getY()));
    }

    /**
     * Esta função realiza a verificação
     *
     * @param r: Reta r em questão
     * @param C: Ponto C em questão;
     * @return Retorna a Orientação
     *
     * Custo O(1)
     */
    public double predicadoOrientacao(Reta r, Ponto C) {
        
        System.out.println("R:"+"X1="+r.p1.x+";"
        +"Y1="+r.p1.y+";X2="+r.p2.x+";"
        +"Y2="+r.p2.y);
        double soma = (r.p1.x * r.p2.y * 1) + (r.p2.x * C.y * 1) + (C.x * r.p1.y * 1);
        System.out.println("soma="+soma);
        double sub = -(C.x * r.p2.y * 1) - (r.p1.x * C.y * 1) - (r.p2.x * r.p1.y * 1);
        System.out.println("Sub="+sub);
        double aux=soma+sub;
        if(r.p1.y>r.p2.y){
            return aux;
        }else if(r.p1.y<=r.p2.y){
            return aux*(-1);
        }else{
            return 0;
        }
        
        

    }

}
