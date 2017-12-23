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
import java.util.Iterator;

import Modelo.Circulo;
import Modelo.Conjunto;
import Modelo.SubConjunto;

public class TAD_Circulo implements TAD_Conjuntos {

    public TAD_Circulo() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public boolean memberS(SubConjunto subconjunto, Object elemento) {
        Circulo u = (Circulo) elemento;

        if (subconjunto.ordenado) {
            return subconjunto.getLista_ordenada().contains(u); // lista ordenada
        } else {
            return subconjunto.getLista().contains(u); // lista nao ordenada
        }
    }

    @Override
    public boolean insertS(SubConjunto subconjunto, Object elemento) {
        Circulo u = (Circulo) elemento;

        subconjunto.soma_hash += u.hash_reta;

        subconjunto.hash_subconjunto = this.geraHash(subconjunto.soma_hash);

        if (subconjunto.ordenado) {
            return subconjunto.getLista_ordenada().add(u); // lista ordenada
        } else {
            return subconjunto.getLista().add(u); // lista nao ordenada
        }
    }

    @Override
    public boolean deleteS(SubConjunto subconjunto, Object elemento) {
        Circulo u = (Circulo) elemento;

        if (subconjunto.ordenado) {

            if (subconjunto.getLista_ordenada().remove(u)) {

                subconjunto.soma_hash -= u.hash_reta;

                subconjunto.hash_subconjunto = this.geraHash(subconjunto.soma_hash);

                return true;
            } else {
                return false;
            }
        } else {

            if (subconjunto.getLista().remove(u)) {

                subconjunto.soma_hash -= u.hash_reta;

                subconjunto.hash_subconjunto = this.geraHash(subconjunto.soma_hash);

                return true;
            } else {
                return false;
            }

        }

    }

    @Override
    public boolean memberC(Conjunto conjunto, SubConjunto a) {
        return conjunto.getConjunto().containsKey(a.hash_subconjunto);
    }

    @Override
    public boolean insertC(Conjunto conjunto, SubConjunto a) {
        if ((conjunto != null) && (a != null)) {
            conjunto.getConjunto().put(a.hash_subconjunto, a);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteC(Conjunto conjunto, SubConjunto a) {
        return conjunto.getConjunto().remove(a.hash_subconjunto, a);
    }

    @Override
    public SubConjunto findC(Conjunto conjunto, Object elemento) {
        // retorno o primeiro subconjunto
        Circulo u = (Circulo) elemento;

        for (SubConjunto subconjunto : conjunto.getConjunto().values()) {

            if (subconjunto.ordenado) {
                if (subconjunto.getLista_ordenada().contains(u)) {
                    return subconjunto;
                }
            } else {
                if (subconjunto.getLista().contains(u)) {
                    return subconjunto;
                }
            }
        }
        return null;
    }

    @Override
    public Object minS(SubConjunto subconjunto) {
        if (subconjunto.ordenado) {
            return subconjunto.getLista_ordenada().first();
        } else {
            return null;
        }
    }

    @Override
    public Conjunto splitS(SubConjunto subconjunto, Object elemento) {

        Circulo u = (Circulo) elemento;
        if (this.memberS(subconjunto, u)) {

            SubConjunto s1;
            SubConjunto s2;
            Iterator<Object> iterator;

            if (subconjunto.ordenado) {

                s1 = new SubConjunto(true);
                s2 = new SubConjunto(true);

                iterator = subconjunto.getLista_ordenada().iterator();

            } else {

                s1 = new SubConjunto(false);
                s2 = new SubConjunto(false);

                iterator = subconjunto.getLista().iterator();
            }

            Circulo aux = new Circulo();
            while (iterator.hasNext()) {

                aux = (Circulo) iterator.next();

                int res = aux.compareTo(u);

                if (res == 1) {
                    this.insertS(s2, aux);
                } else {
                    this.insertS(s1, aux);
                }
            }

            Conjunto conjunto = new Conjunto();
            conjunto.getConjunto().put(s1.hash_subconjunto, s1);
            conjunto.getConjunto().put(s2.hash_subconjunto, s2);
            return conjunto;

        } else {
            return null;
        }
    }

    @Override
    public double geraHash(double numero) {
        return numero % 13;
    }

    @Override
    public SubConjunto union(SubConjunto a, SubConjunto b) {
        if (a.hash_subconjunto == b.hash_subconjunto) {
            return a;
        } else {

            SubConjunto k;
            if (a.ordenado) {
                k = new SubConjunto(true);
                Iterator<Object> iterator_aux = a.getLista_ordenada().iterator();
                while (iterator_aux.hasNext()) {
                    Circulo aux = (Circulo) iterator_aux.next();
                    this.insertS(k, aux);
                }

            } else {
                k = new SubConjunto(false);
                Iterator<Object> iterator_aux = a.getLista().iterator();
                while (iterator_aux.hasNext()) {
                    Circulo aux = (Circulo) iterator_aux.next();
                    this.insertS(k, aux);
                }
            }

            k.hash_subconjunto = a.hash_subconjunto;
            k.soma_hash = a.soma_hash;

            Iterator<Object> iterator;
            if (b.ordenado) {
                iterator = b.getLista_ordenada().iterator();
            } else {
                iterator = b.getLista().iterator();
            }

            while (iterator.hasNext()) {

                Circulo aux = (Circulo) iterator.next();
                if (k.ordenado) {
                    if (!k.getLista_ordenada().contains(aux)) {
                        this.insertS(k, aux); // ja calcula a hash
                    }
                } else {
                    if (!k.getLista().contains(aux)) {
                        this.insertS(k, aux); // ja calcula a hash
                    }
                }

            }

            return k;
        }
    }

    @Override
    public SubConjunto intersection(SubConjunto a, SubConjunto b) {
        if (a.hash_subconjunto == b.hash_subconjunto) {
            return a;
        } else {

            SubConjunto k;

            Iterator<Object> iterator;
            if (a.ordenado) {
                iterator = a.getLista_ordenada().iterator();
                k = new SubConjunto(true);
            } else {
                iterator = a.getLista().iterator();
                k = new SubConjunto(false);
            }

            k.ordenado = a.ordenado;

            while (iterator.hasNext()) {
                Circulo aux = (Circulo) iterator.next();

                if (b.ordenado) {
                    if (b.getLista_ordenada().contains(aux)) {
                        this.insertS(k, aux); // ja calcula a hash
                    }
                } else {
                    if (b.getLista().contains(aux)) {
                        this.insertS(k, aux); // ja calcula a hash
                    }
                }

            }
            return k;

        }
    }

    @Override
    public SubConjunto difference(SubConjunto a, SubConjunto b) {
        if (a.hash_subconjunto == b.hash_subconjunto) {
            return null;
        } else {

            SubConjunto k;

            Iterator<Object> iterator;
            if (a.ordenado) {
                iterator = a.getLista_ordenada().iterator();
                k = new SubConjunto(true);
            } else {
                iterator = a.getLista().iterator();
                k = new SubConjunto(false);
            }

            k.ordenado = a.ordenado;

            while (iterator.hasNext()) {
                Circulo aux = (Circulo) iterator.next();

                if (b.ordenado) {
                    if (!b.getLista_ordenada().contains(aux)) {
                        this.insertS(k, aux); // ja calcula a hash
                    }
                } else {
                    if (!b.getLista().contains(aux)) {
                        this.insertS(k, aux); // ja calcula a hash
                    }
                }

            }
            return k;
        }
    }

}
