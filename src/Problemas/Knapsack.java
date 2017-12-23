/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Problemas;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Problema da Mochila com programação dinâmica
 *
 * @author Vitor
 */
public class Knapsack {

    public Knapsack() {
    }

    /**
     * Utilidade que retorna o maior de dois valores
     * @param a: primeiro número a ser comparado
     * @param b: segundo npumero a ser comparado
     * @return o maior entre os valores de a e b
     */
    public static int max(int a, int b) {
        if (a > b) {
            return a;
        } else {
            return b;
        }
    }

    /**
     * retorna o maior custo que pode ser colocado em uma mochila de peso @
     * @param W: capacidade da mochila
     * @param wt: peso do item
     * @param val: valor
     * @param n: numero de itens
     * @return int com o valor maximo que pode ser alocado
     */
    public static int knapSack(int W, int wt[], int val[], int n, JTable minha_tabela) {
        int i, w;
        int K[][] = new int[n + 1][W + 1];

        // constroi uma tabela de baixo pra cima    
        for (i = 0; i <= n; i++) {
            for (w = 0; w <= W; w++) {
                if (i == 0 || w == 0) {
                    K[i][w] = 0;
                } else if (wt[i - 1] <= w) {
                    K[i][w] = max(val[i - 1] + K[i - 1][w - wt[i - 1]], K[i - 1][w]);
                } else {
                    K[i][w] = K[i - 1][w];
                }
            }
        }
        
        Object[][] data = new Object[n+1][W+1];
        Object[] names = new Object[W+1];
        
        for (Object name : names) {
            names[i]=i;
        }

        for (int j = 0; j < data.length; j++) {
            for (int k = 0; k < data[0].length; k++) {
                data[j][k]=K[j][k];
            }
        }
        
        DefaultTableModel dtm = new DefaultTableModel(data, names);
        minha_tabela.setModel(dtm);
        
        return K[n][W];
    }
}
