/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Problemas;

import java.text.DecimalFormat;

/**
 *
 * @author Vitor
 */
public class Troco {

    public String calculoTroco(double conta, double pago) {
        DecimalFormat dm = new DecimalFormat("###,##0.00");

        if (pago < conta) {
            return ("Pagamento insuficiente! Faltam R$" + (dm.format(conta - pago)) + "\n");
        } else {

            String resultado;
            double troco;

            troco = pago - conta;

            resultado = "\nTroco = R$" + dm.format(troco) + "\n\n";

            resultado = this.calculaNotas(troco, resultado); // calcula as notas O(n)
            resultado = this.calculaMoedas(troco, resultado);// calcula as moedas O(n)

            resultado = resultado + "\n";

            return (resultado);

        }
    }

    private String calculaNotas(final double troco, String resultado) {

        // notas disponiveis
        int nota[] = {100, 50, 20, 10, 5, 2, 1};

        int valor;
        int ct;

        int contadorNota = 0;
        //pega o valor inteiro
        valor = (int) troco;
        // verifica se ja conseguiu o troco
        while (valor != 0) {
            // verifica se é possivel dar o troco com  valor da nota no indice do vetor atual
            // se sim, devolve a quantidade de notas
            ct = valor / nota[contadorNota]; // calculando a qtde de notas
            if (ct != 0) {
                //pega o resultado de quantas notas é possivel devolver
                resultado = resultado + (ct + " nota(s) de R$" + nota[contadorNota] + "\n");
                // calcula o resto do troco
                valor = valor % nota[contadorNota];
            }
            // próxima nota
            contadorNota++;
        }
        return resultado;
    }

    private String calculaMoedas(final double troco, String resultado) {

        int centavos[] = {50, 25, 10, 5, 1};

        int contadorMoeda = 0;
        int valor;
        int ct;
        //pega o valor dos centavos
        valor = (int) Math.round((troco - (int) troco) * 100);
        // verifica se ja conseguiu o troco
        while (valor != 0) {
            // verifica se é possivel dar o troco com  valor de moedas no indice do vetor atual
            // se sim, devolve a quantidade de moedas
            ct = valor / centavos[contadorMoeda];
            if (ct != 0) {
                //pega o resultado de quantas moedas é possivel devolver
                resultado = resultado + (ct + " moeda(s) de " + centavos[contadorMoeda] + " centavo(s)\n");
                // calcula o resto do troco
                valor = valor % centavos[contadorMoeda];
            }
            // próximo centavo
            contadorMoeda++;
        }
        return resultado;
    }
}
