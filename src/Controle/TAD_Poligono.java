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

import Modelo.Conjunto;
import Modelo.SubConjunto;
import Modelo.Poligono;
import Modelo.Ponto;

public class TAD_Poligono implements TAD_Conjuntos {

    public TAD_Poligono() {
    }

    /**
     * Esta função verifica se alguem está na classe
     *
     * @param subconjunto: este é o subconjunto que eu quero verificar se existe
     * alguém dentro;
     * @param elemento: este é o elemento que quero saber se existe no meu
     * subconjunto;
     *
     * @return: retorna verdadeiro se o elemento existe no meu subconjunto,
     * retorna falso se ele não contém o elemento
     */
    @Override
    public boolean memberS(SubConjunto subconjunto, Object elemento) {
        Poligono u = (Poligono) elemento;

        return subconjunto.getLista().contains(u);
    }

    /**
     * Esta função insere um poligono no meu subconjunto, e atualiza hash
     *
     * @param subconjunto: este é o subconjunto que eu quero inserir o elemento;
     * @param elemento: este é o elemento que eu quero inserir;
     *
     * @return verdadeiro se eu inserir, caso eu não consiga retorna falso
     */
    @Override
    public boolean insertS(SubConjunto subconjunto, Object elemento) {
        // TODO Auto-generated method stub
        Poligono u = (Poligono) elemento;

        subconjunto.soma_hash += u.hash_poligono;
        subconjunto.hash_subconjunto = this.geraHash(subconjunto.soma_hash);

        return subconjunto.getLista().add(u);

    }

    /**
     * Esta função deleta um poligono do meu subconjunto, e também atualiza a
     * hash
     *
     * @param subconjunto: este é o subconjunto que eu quero deletar dado
     * elemento;
     * @param elemento: este é o elemento que eu quero remover;
     *
     * @return verdadeiro se eu conseguir remover, falso caso não consiga
     */
    @Override
    public boolean deleteS(SubConjunto subconjunto, Object elemento) {
        Poligono u = (Poligono) elemento;

        if (subconjunto.getLista().remove(u)) {
            subconjunto.soma_hash -= u.hash_poligono;
            subconjunto.hash_subconjunto = this.geraHash(subconjunto.soma_hash);
            return true;
        }

        return false;
    }

    /**
     * Esta função verifica se um conjunto contém um subconjunto de poligonos
     *
     * @param conjunto: conjunto de subconjunto de poligonos;
     * @param a: subconjunto de poligonos;
     *
     * @return verdadeiro se o subconjunto a está contido no conjunto, caso
     * contrário retorna falso
     */
    @Override
    public boolean memberC(Conjunto conjunto, SubConjunto a) {
        return conjunto.getConjunto().containsKey(a.hash_subconjunto);
    }

    /**
     * Esta função insere um subconjunto de poligonos a em conjunto
     *
     * @param conjunto: conjunto de subconjuntos de poligonos;s
     * @param a: subconjunto de poligonos;
     *
     */
    @Override
    public boolean insertC(Conjunto conjunto, SubConjunto a) {
        if ((conjunto != null) && (a != null)) {
            conjunto.getConjunto().put(a.hash_subconjunto, a);
            return true;
        }
        return false;
    }

    /**
     * Esta função remove um subconjunto de poligonos a que está contido em um
     * conjunto de subconjuntos de poligonos
     *
     * @param conjunto: conjunto de subconjuntos;
     * @param a: subconjunto de poligonos;
     *
     * @return verdadeiro se eu conseguir remover, falso coaso eu nao consiga
     * remover o subconjunto
     */
    @Override
    public boolean deleteC(Conjunto conjunto, SubConjunto a) {
        return conjunto.getConjunto().remove(a.hash_subconjunto, a);
    }

    /**
     * Esta função que retorna um subconjunto que contém dado elemento
     *
     * @param conjunto: conjunto que contém subconjuntos de poligonos
     * @param elemento: elemento que eu quero o subconjunto no qual ele exista
     *
     * @return retorna o subconjunto que o elemento existe ou retorna null
     */
    @Override
    public SubConjunto findC(Conjunto conjunto, Object elemento) {
        Poligono u = (Poligono) elemento;
        for (SubConjunto subconjunto : conjunto.getConjunto().values()) {
            if (subconjunto.getLista().contains(u)) {
                return subconjunto;
            }
        }
        return null;
    }

    /**
     * Esta função não faz sentido dado que não faz sentido ordenar poligonos
     * por alguma coisa
     */
    @Override
    public Object minS(SubConjunto subconjunto) {
        /*
		 * Iterator<Object> iterator =subconjunto.getLista().iterator();
		 * while(iterator.hasNext()){ return iterator.next(); }
         */
        return null;
    }

    /**
     * Da forma que foi pedido o subconjunto precisa estar ordenado, foi
     * implementado pela ordem que o iterador irá percorrer a lista, esta função
     * também não faz sentido pois será desordenado, foi só immplementado para
     * fins de teste
     */
    @Override
    public Conjunto splitS(SubConjunto subconjunto, Object elemento) {
        Conjunto c = new Conjunto();
        SubConjunto a = new SubConjunto(false);
        SubConjunto b = new SubConjunto(false);
        Iterator<Object> iterador = subconjunto.getLista().iterator();

        boolean achou = false;

        while (iterador.hasNext()) {

            if (achou == false) {
                this.insertS(a, elemento);
            } else {
                this.insertS(b, elemento);
            }
            if (elemento.equals(iterador.next())) {
                achou = true;
            }

        }
        this.insertC(c, a);
        this.insertC(c, b);
        return c;
    }

    /**
     * Esta função recebe um número e recebe uma hash
     */
    @Override
    public double geraHash(double numero) {
        return numero % 13;
    }

    /**
     * Esta função faz a união do subconjunto a com o subconjunto b,
     * primeiramente é verificado se os conjuntos são iguais, se sim retorno o
     * subconjunto a, caso contrário construo um novo subconjunto
     *
     * @param a: subconjunto que eu quero fazer uniao
     * @param b: subconjunto que eu quero fazer uniao
     *
     * @return retorna o subconjunto uniao
     */
    @Override
    public SubConjunto union(SubConjunto a, SubConjunto b) {

        if (a.hash_subconjunto == b.hash_subconjunto) {
            return a;
        } else {

            SubConjunto k;
            k = new SubConjunto(false);
            Iterator<Object> iterador = a.getLista().iterator();

            // inserindo os pontos de a no subconjunto k
            while (iterador.hasNext()) {
                Poligono poligono_auxiliar = (Poligono) iterador.next();
                this.insertS(k, poligono_auxiliar);
            }

            // as hash do subconjunto k serão a mesma do
            // subconjunto a, pois contém os mesmos pontos
            // logo serão, em teoria até esta parte do algoritmo
            // o mesmo conjunto
            k.hash_subconjunto = a.hash_subconjunto;
            k.soma_hash = a.soma_hash;

            Iterator<Object> iterator;
            iterator = b.getLista().iterator();

            // passeia pela lista e ve se não está, se nao estiver insere
            // desta forma eu evito duplicatas
            while (iterator.hasNext()) {
                Ponto aux = (Ponto) iterator.next();
                if (!k.getLista().contains(aux)) {
                    this.insertS(k, aux); // ja calcula a hash
                }
            }

            return k;
        }
    }

    /**
     * Esta função faz a interseccao entre 2 conjuntos, ela cria um novo
     * conjunto e pega os pontos que estão presentes no subconjunto a e no
     * subconjunto b
     *
     * @param a: subconjunto que quero fazer a interseccao
     * @param b: segundo subconjunto que eu quero fazer a interseccao
     *
     * @return Retorna o subconjunto que os pontos que são comuns entre a e b
     * estão sendo armazenados
     */
    @Override
    public SubConjunto intersection(SubConjunto a, SubConjunto b) {
        if (a.hash_subconjunto == b.hash_subconjunto) {
            return a;
        } else {

            SubConjunto k;

            Iterator<Object> iterator;
            // iterador que vai passear por toda a lista do subconjunto a
            iterator = a.getLista().iterator();
            k = new SubConjunto(false);

            // k.ordenado = a.ordenado;
            while (iterator.hasNext()) {

                Poligono aux = (Poligono) iterator.next();
                // se o subconjunto b contem o ponto que está sendo verificado
                // no iterador que passeia por a então insere
                if (b.getLista().contains(aux)) {
                    this.insertS(k, aux); // ja calcula a hash
                }

            }
            return k;

        }
    }

    /**
     * Esta função calcula a diferenca entre os dois subconjuntos a e b;
     *
     * @param a: subconjunto que quero fazer a diferenca
     * @param b: segundo subconjunto que eu quero fazer a diferenca
     *
     * @return Retorna o subconjunto que os pontos que não são comuns entre a e
     * b estão sendo armazenados
     */
    @Override
    public SubConjunto difference(SubConjunto a, SubConjunto b) {
        if (a.hash_subconjunto == b.hash_subconjunto) {
            return null; // todos os pontos são iguais
        } else {

            SubConjunto k;
            //pegar o iterador que passeia por a
            Iterator<Object> iterator = a.getLista().iterator();
            k = new SubConjunto(false);

            //enquanto tiver lista			
            while (iterator.hasNext()) {
                Poligono aux = (Poligono) iterator.next();

                if (!b.getLista().contains(aux)) {
                    this.insertS(k, aux); // ja calcula a hash
                }

            }
            return k;
        }
    }
}
