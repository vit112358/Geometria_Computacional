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
/* 
 * Smallest enclosing circle - Library (Java)
 * 
 * Copyright (c) 2017 Project Nayuki
 * https://www.nayuki.io/page/smallest-enclosing-circle
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program (see COPYING.txt and COPYING.LESSER.txt).
 * If not, see <http://www.gnu.org/licenses/>.
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import Modelo.Circulo;
import Modelo.Ponto;

public final class CirculoMinimo {

    /*
	 * Retorna o círculo menor que inclui todos os pontos dados. Executa em O
	 * (n) randomizado. Nota: se forem dados 0 pontos, nulo é
	 * retornado. Se for dado 1 ponto, retorna-se um círculo de raio 0.
     */
    // Inicialmente: nenhum ponto limite conhecido
    public static Circulo makeCircle(List<Ponto> Pontos) {
        // Clona os pontos para preservar os dados original, aleatorizar a ordem
        List<Ponto> shuffled = new ArrayList<>(Pontos);
        Collections.shuffle(shuffled, new Random());

        // Aumente progressivamente pontos para circular ou recomponer círculo
        Circulo c = null;
        for (int i = 0; i < shuffled.size(); i++) {
            Ponto p = shuffled.get(i);
            if (c == null || !c.contains(p)) {
                c = makeCircleOnePonto(shuffled.subList(0, i + 1), p);
            }
        }
        return c;
    }

    // One boundary Ponto known
    private static Circulo makeCircleOnePonto(List<Ponto> Pontos, Ponto p) {
        Circulo c = new Circulo(p, 0);
        for (int i = 0; i < Pontos.size(); i++) {
            Ponto q = Pontos.get(i);
            if (!c.contains(q)) {
                if (c.raio == 0) {
                    c = makeDiameter(p, q);
                } else {
                    c = makeCircleTwoPontos(Pontos.subList(0, i + 1), p, q);
                }
            }
        }
        return c;
    }

    // Two boundary Pontos known
    private static Circulo makeCircleTwoPontos(List<Ponto> Pontos, Ponto p, Ponto q) {
        Circulo circ = makeDiameter(p, q);
        Circulo left = null;
        Circulo right = null;

        // For each Ponto not in the two-Ponto circle
        Ponto pq = q.subtract(p);
        for (Ponto r : Pontos) {
            if (circ.contains(r)) {
                continue;
            }

            // Form a circumcircle and classify it on left or right side
            double cross = pq.cross(r.subtract(p));
            Circulo c = makeCircumcircle(p, q, r);
            if (c == null) {
                continue;
            } else if (cross > 0 && (left == null || pq.cross(c.centro.subtract(p)) > pq.cross(left.centro.subtract(p)))) {
                left = c;
            } else if (cross < 0 && (right == null || pq.cross(c.centro.subtract(p)) < pq.cross(right.centro.subtract(p)))) {
                right = c;
            }
        }

        // Select which circle to return
        if (left == null && right == null) {
            return circ;
        } else if (left == null) {
            return right;
        } else if (right == null) {
            return left;
        } else {
            return left.raio <= right.raio ? left : right;
        }
    }

    static Circulo makeDiameter(Ponto a, Ponto b) {
        Ponto c = new Ponto((a.x + b.x) / 2, (a.y + b.y) / 2);
        
        return new Circulo(c, Math.max(c.distance(a), c.distance(b)));
    }

    static Circulo makeCircumcircle(Ponto a, Ponto b, Ponto c) {
        // Mathematical algorithm from Wikipedia: Circumscribed circle
        double ox = (Math.min(Math.min(a.x, b.x), c.x) + Math.max(Math.min(a.x, b.x), c.x)) / 2;
        double oy = (Math.min(Math.min(a.y, b.y), c.y) + Math.max(Math.min(a.y, b.y), c.y)) / 2;
        double ax = a.x - ox, ay = a.y - oy;
        double bx = b.x - ox, by = b.y - oy;
        double cx = c.x - ox, cy = c.y - oy;
        double d = (ax * (by - cy) + bx * (cy - ay) + cx * (ay - by)) * 2;
        if (d == 0) {
            return null;
        }
        double x = ((ax * ax + ay * ay) * (by - cy) + (bx * bx + by * by) * (cy - ay) + (cx * cx + cy * cy) * (ay - by))
                / d;
        double y = ((ax * ax + ay * ay) * (cx - bx) + (bx * bx + by * by) * (ax - cx) + (cx * cx + cy * cy) * (bx - ax))
                / d;
        Ponto p = new Ponto(ox + x, oy + y);
        double r = Math.max(Math.max(p.distance(a), p.distance(b)), p.distance(c));
        return new Circulo(p, r);
    }

}
