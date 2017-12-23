/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Problemas;

import Modelo.Poligono;
import Modelo.Ponto;
import Modelo.Triangle;
import java.util.ArrayList;

/**
 *
 * @author Vitor
 */
public class Algoritmo_Van_Gogh {

    public static ArrayList<Triangle> slow(Poligono p) {

        ArrayList<Triangle> result = new ArrayList<Triangle>();
        // verifica se o pologono está no sentido horario
        boolean sentidoHorario = p.eSentidoHorario();

        while (p.tamanho() > 3) {

            int li = -1;
            Ponto l, v, r;
            Triangle tr;
            boolean isOrelha;
            int tentativas = 0;

            do {
                tentativas++;

                if (tentativas >= p.tamanho()) {
                    // Loop infinito; entrada ruim
                    return null;
                }

                li++;
                l = p.get(li % p.tamanho());
                v = p.get((li + 1) % p.tamanho());
                r = p.get((li + 2) % p.tamanho());
                tr = new Triangle(l, v, r);
                // Determina se dois vetores definido por três pontos fazem uma curva esquerda. 
                isOrelha = Ponto.isLeftTurn(l, v, r) ^ sentidoHorario;

                if (isOrelha) { // Requisição adicional necessária
                    for (int i = 0; i < p.tamanho(); i++) { // não deve haver pontos dentro
                        // verifica se o ponto esta dentro do triangulo
                        if (tr.pointInside(p.get(i))) {
                            isOrelha = false;
                            break;
                        }
                    }
                }

            } while (!isOrelha); // Até que descobrimos uma orelha

            // Garantiu que recebemos uma orelha aqui
            result.add(tr);
            p.removeVerticeDe((li + 1) % p.tamanho());

        }

        if (p.tamanho() == 3) { // O último triângulo
            result.add(new Triangle(p.get(0), p.get(1), p.get(2)));
        }

        return result;
    }
}
