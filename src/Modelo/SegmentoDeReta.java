/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author Vitor
 */
/*import com.panayotis.gnuplot.JavaPlot;
import com.panayotis.gnuplot.layout.StripeLayout;
import com.panayotis.gnuplot.plot.AbstractPlot;
import com.panayotis.gnuplot.plot.DataSetPlot;
import com.panayotis.gnuplot.style.NamedPlotColor;
import com.panayotis.gnuplot.style.PlotStyle;
import com.panayotis.gnuplot.style.Style;
 */
public class SegmentoDeReta implements Comparable<SegmentoDeReta> {

    public Ponto p1;
    public Ponto p2;
    public Ponto cor;
    public double hash_reta;

    public SegmentoDeReta() {

    }

    public SegmentoDeReta(Ponto p1, Ponto p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.hash_reta = this.geraHashReta(p1, p2);
    }

    /*public void desenhaSegmento() {
        JavaPlot p = new JavaPlot();
        double[][] x = {{p1.x, p1.y}, {p2.x, p2.y}};

        p.setTitle("Segmento de Reta");
        p.getAxis("x").setLabel("X", "Arial", 10);
        p.getAxis("y").setLabel("Y", "Arial", 10);

        p.getAxis("x").setBoundaries(0, 10);
        p.getAxis("y").setBoundaries(0, 10);

        p.setKey(JavaPlot.Key.TOP_RIGHT);

        DataSetPlot s = new DataSetPlot(x);
        s.setTitle("Ponto P1");
        p.addPlot(s);

        PlotStyle stl = ((AbstractPlot) p.getPlots().get(0)).getPlotStyle();
        stl.setStyle(Style.LINESPOINTS);
        stl.setLineType(NamedPlotColor.BLACK);
        stl.setPointType(7);
        stl.setPointSize(2);

        p.plot();
    }*/

    public void setPosicao(Ponto p1, Ponto p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public void setCor1(Ponto cor) {
        this.cor = cor;
    }

    public Ponto getP1() {
        return p1;
    }

    public void setP1(Ponto p1) {
        this.p1 = p1;
    }

    public Ponto getP2() {
        return p2;
    }

    public void setP2(Ponto p2) {
        this.p2 = p2;
    }

    public Ponto getCor() {
        return cor;
    }

    public void setCor(Ponto cor) {
        this.cor = cor;
    }

    public double getHash_reta() {
        return hash_reta;
    }

    public void setHash_reta(double hash_reta) {
        this.hash_reta = hash_reta;
    }

    public double geraHashReta(Ponto p1, Ponto p2) {

        double resultado = ((p1.x * 7) + (p1.y * 3)) + ((p2.x * 5) + (p2.y * 7));

        return resultado % 13;
    }

    public double distancia() {
        return Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
    }

    @Override
    public int compareTo(SegmentoDeReta o) {

        double reta1 = Math.sqrt(Math.pow((p2.x - p1.x), 2) + Math.pow((p2.y - p1.y), 2));
        double reta2 = Math.sqrt(Math.pow((o.p2.x - o.p1.x), 2) + Math.pow((o.p2.y - o.p1.y), 2));

        if (reta1 < reta2) {
            return -1;
        } else if (reta1 == reta2) {

            if (p1.x < o.p1.x) {
                return -1;
            } else if (p1.x == o.p1.x) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return 1;
        }

    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
        result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        SegmentoDeReta other = (SegmentoDeReta) obj;
        if (p1 == null) {
            if (other.p1 != null) {
                return false;
            }
        } else if (!p1.equals(other.p1)) {
            return false;
        }
        if (p2 == null) {
            if (other.p2 != null) {
                return false;
            }
        } else if (!p2.equals(other.p2)) {
            return false;
        }
        return true;
    }
}
