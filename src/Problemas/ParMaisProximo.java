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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import Modelo.Ponto;

public class ParMaisProximo {

    /**
     * Encontra o par de pontos mais próximos usando divisao e cnquista em
     * O(n*log(n)).
     *
     * @return retorna um vetor com com o par maix proximo
     */
    public static ArrayList<Ponto> Fast(ArrayList<Ponto> points) {
        // faz copias da lista de pontos
        ArrayList<Ponto> X = (ArrayList<Ponto>) points.clone();
        ArrayList<Ponto> Y = (ArrayList<Ponto>) points.clone();

        // ordena as lista menor para maior
        Collections.sort(X, new PointComparatorX());
        // ordena as lista maior para menor
        Collections.sort(Y, new PointComparatorY());

        // algoritmo que calcula de modo recursivo
        return parMaisProximo(X, Y);
    }

    /**
     * O procedimento recursivo para o algoritmo "Fast".
     */
    private static ArrayList<Ponto> parMaisProximo(ArrayList<Ponto> X, ArrayList<Ponto> Y) {
        // array que irá conter o resultado
        ArrayList<Ponto> resultPair = new ArrayList<Ponto>();

        // Caso de base de recursão: |P| <= 3
        if (X.size() <= 1) {
            // somente um ponto
            resultPair = null;
        } else if (X.size() == 2) {
            // dois pontos
            resultPair = X;
        } else if (X.size() == 3) {
            // |P| == 3 => Força Bruta, calcula a distancia de todos 
            // e verifica qual esta mais perto
            double dist1 = dist(X.get(0), X.get(1));
            double dist2 = dist(X.get(0), X.get(2));
            double dist3 = dist(X.get(1), X.get(2));

            if (dist2 < dist3) {
                if (dist1 < dist2) {
                    resultPair.add(X.get(0));
                    resultPair.add(X.get(1));
                } else {
                    resultPair.add(X.get(0));
                    resultPair.add(X.get(2));
                }
            } else {
                if (dist1 < dist3) {
                    resultPair.add(X.get(0));
                    resultPair.add(X.get(1));
                } else {
                    resultPair.add(X.get(1));
                    resultPair.add(X.get(2));
                }
            }
        } // Etapa de recursão: dividir e conquistar
        else {
            //pega o ponto x da metade da lista
            double lX = (X.get(X.size() / 2)).getX();

            // Dividir
            ArrayList<Ponto> XL = new ArrayList<Ponto>();
            ArrayList<Ponto> XR = new ArrayList<Ponto>();
            // Dividi os pontos pelo x pego
            for (int i = 0; i < X.size(); i++) {
                if ((X.get(i)).getX() <= lX) {
                    XL.add(X.get(i));
                } else {
                    XR.add(X.get(i));
                }
            }
            // Dividi os pontos pelo x pego
            ArrayList<Ponto> YL = new ArrayList<Ponto>();
            ArrayList<Ponto> YR = new ArrayList<Ponto>();

            for (int i = 0; i < Y.size(); i++) {
                if ((Y.get(i)).getX() <= lX) {
                    YL.add(Y.get(i));
                } else {
                    YR.add(Y.get(i));
                }
            }

            // O caso degenerado quando todos os pontos vão para a esquerda deve ser
            // tratado com cuidado. Isso acontece, e. quando todos os pontos tiveram
            // a mesma coordenada X.
            // O desempenho pode degradar para O (n^2) aqui se
            // todos os pontos estão no eixo Y.
            if (XR.size() == 0) {
                // Solução temporária: basta mover o ponto "mais à direita" de XL para XR				
                Ponto repl = XL.get(XL.size() - 1);

                XL.remove(repl);
                XR.add(repl);

                YL.remove(repl);
                YR.add(repl);
            }

            // Conquistar
            // chama a função recursiva com os array's divididos
            ArrayList<Ponto> ClosestLeft = parMaisProximo(XL, YL);
            ArrayList<Ponto> ClosestRight = parMaisProximo(XR, YR);
            // verifica qual o mais proximo entre os subconjuntos
            ArrayList<Ponto> ClosestBetween = maisProximoEntreSubconjunotos(X, Y,
                    Math.min(dist(ClosestLeft), dist(ClosestRight)));

            double distLeft = dist(ClosestLeft);
            double distRight = dist(ClosestRight);
            double distBetween = dist(ClosestBetween);

            // Verifica 
            if (distLeft < distRight) {
                if (distBetween < distLeft) {
                    resultPair = ClosestBetween;
                } else {
                    resultPair = ClosestLeft;
                }
            } else {
                if (distBetween < distRight) {
                    resultPair = ClosestBetween;
                } else {
                    resultPair = ClosestRight;
                }
            }
        }

        return resultPair;
    }

    // calcula o mais proximo entre subconjuntos passados pela divisao
    private static ArrayList<Ponto> maisProximoEntreSubconjunotos(ArrayList<Ponto> X, ArrayList<Ponto> Y, double d) {
        double lX = (X.get(X.size() / 2)).getX();

        // Preencha a lista Y2
        ArrayList<Ponto> Y2 = new ArrayList<Ponto>();
        for (int i = 0; i < Y.size(); i++) {
            if (Math.abs((Y.get(i)).getX() - lX) <= d) {
                Y2.add(Y.get(i));
            }
        }

        // Iterator sobre Y2 em busca dos pontos mais próximos
        if (Y2.size() < 2) {
            return null;
        }

        Ponto p1 = Y2.get(0);
        Ponto p2 = Y2.get(1);

        // Basta verificar apenas 7 pontos
        for (int i = 0; i < Y2.size() - 1; i++) {
            for (int j = i + 1; j <= Math.min(i + 8, Y2.size() - 1); j++) {
                if (dist(Y2.get(i), Y2.get(j)) < dist(p1, p2)) {
                    p1 = Y2.get(i);
                    p2 = Y2.get(j);
                }
            }
        }

        ArrayList<Ponto> resultPair = new ArrayList<Ponto>();
        resultPair.add(p1);
        resultPair.add(p2);

        return resultPair;
    }

    //calcula a distacia entre 2 pontos
    public static double dist(Ponto p0, Ponto p1) {
        return Math.sqrt(
                (p1.getX() - p0.getX()) * (p1.getX() - p0.getX()) + (p1.getY() - p0.getY()) * (p1.getY() - p0.getY()));
    }

    // calcula a distancia entre varios pontos
    private static double dist(ArrayList<Ponto> pair) {
        if (pair == null || pair.size() != 2) {
            return Double.POSITIVE_INFINITY;
        } else {
            return Math.sqrt(((pair.get(1)).getX() - (pair.get(0)).getX())
                    * ((pair.get(1)).getX() - (pair.get(0)).getX())
                    + ((pair.get(1)).getY() - (pair.get(0)).getY()) * ((pair.get(1)).getY() - (pair.get(0)).getY()));
        }
    }

    // compara a coordenada x do ponto
    static class PointComparatorX implements Comparator<Ponto> {

        public int compare(Ponto p1, Ponto p2) {

            if (p1.getX() < p2.getX()) {
                return -1;
            }
            if (p1.getX() > p2.getX()) {
                return 1;
            }
            return 0;
        }
    }

    // compara a coordenada y do ponto
    static class PointComparatorY implements Comparator<Ponto> {

        @Override
        public int compare(Ponto p1, Ponto p2) {

            if (p1.getY() < p2.getY()) {
                return -1;
            }
            if (p1.getY() > p2.getY()) {
                return 1;
            }
            return 0;
        }
    }
}
