/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao;

import Modelo.Ponto;
import Problemas.Problemas_Classicos;
import java.awt.Color;

/**
 *
 * @author Vitor
 */
public class Interface_Voronoi_2 implements DrawListener {

    private static int SIZE_X = 500;
    private static int SIZE_Y = 500;
    private Ponto[][] vetorPontos_tela = new Ponto[SIZE_X][SIZE_Y]; // matriz de pontos vazio para pegar os selecionados
    private Problemas_Classicos classicos = new Problemas_Classicos();
    private Draw draw = new Draw();

    public Interface_Voronoi_2() {
        draw.setCanvasSize(SIZE_X, SIZE_Y);
        
        draw.setXscale(0, SIZE_X);
        draw.setYscale(0, SIZE_Y);
        draw.addListener(this);
        draw.show(20);
    }

    @Override
    public void mousePressed(double x, double y) {
        // cria ponto pressionado pelo mouse
        Ponto pontoSelecionado = new Ponto((int) x, (int) y);

        // compara cada ponto da tela (coordX, coordY) e procura o ponto selecionado no
        // vetorPontos_Tela
        draw.setPenColor(Color.getHSBColor((float) Math.random(), .7f, .7f));

        // procura o ponto mais proximo do ponto da tela ( o meio dos 2)
        for (int coordX = 0; coordX < SIZE_X; coordX++) {
            for (int coordY = 0; coordY < SIZE_Y; coordY++) {
                Ponto ponto_tela = new Ponto(coordX, coordY);
                // verifica quais os pontos estão bem no meio dos dois
                if ((vetorPontos_tela[coordX][coordY] == null)
                        || // null quando for o primeiro ponto selecionado
                        ((classicos.distanciaPontos(ponto_tela, pontoSelecionado)) < (classicos.distanciaPontos(ponto_tela, vetorPontos_tela[coordX][coordY])))) {
                    vetorPontos_tela[coordX][coordY] = pontoSelecionado;
                    draw.filledSquare(coordX + 0.5, coordY + 0.5, 0.5);
                }
            }
        }

        // desenha o ponto
        draw.setPenColor(Color.BLACK);
        draw.filledCircle(x, y, 4);
        draw.show(20);

    }

    // inicia a interface
    public static void main(String[] args) {
        Interface_Voronoi_2 interface_Voronoi_2 = new Interface_Voronoi_2();
    }

    @Override
    public void mouseDragged(double x, double y) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(double x, double y) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyTyped(char c) {
        // TODO Auto-generated method stub

    }

    @Override
    public void keyPressed(int keycode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(int keycode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
