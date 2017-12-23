/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controle;

/**
 *
 * @author Vitor
 */

import Modelo.Conjunto;
import Modelo.SubConjunto;

public interface TAD_Conjuntos {

    public boolean memberS(SubConjunto subconjunto, Object elemento);

    public boolean insertS(SubConjunto subconjunto, Object elemento);

    public boolean deleteS(SubConjunto subconjunto, Object elemento);

    public boolean memberC(Conjunto conjunto, SubConjunto a);

    public boolean insertC(Conjunto conjunto, SubConjunto a);

    public boolean deleteC(Conjunto conjunto, SubConjunto a);

    public SubConjunto findC(Conjunto conjunto, Object elemento);

    // conjunto universal totalmente ordenado
    public Object minS(SubConjunto subconjunto);

    public Conjunto splitS(SubConjunto subconjunto, Object elemento);

    double geraHash(double numero);

    SubConjunto union(SubConjunto a, SubConjunto b);

    SubConjunto intersection(SubConjunto a, SubConjunto b);

    SubConjunto difference(SubConjunto a, SubConjunto b);

}
