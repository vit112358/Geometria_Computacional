/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Visao;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.Set;

public class Interface_Voronoi extends JFrame {

    static class SubDivision {

        List<QuadEdge> quadEdges = new ArrayList<QuadEdge>();
        QuadEdge startingEdge;
        static final double tolerance = 0.001;
        Point.Double[] frameVertex = new Point.Double[3];
        private QuadEdge lastEdge = null;

        SubDivision(Collection<Point.Double> siteCoords) {
            double minX = Double.POSITIVE_INFINITY;
            double maxX = Double.NEGATIVE_INFINITY;
            double minY = Double.POSITIVE_INFINITY;
            double maxY = Double.NEGATIVE_INFINITY;
            for (Point.Double p : siteCoords) {
                minX = Math.min(minX, p.x);
                maxX = Math.max(maxX, p.x);
                minY = Math.min(minX, p.y);
                maxY = Math.max(maxX, p.y);
            }

            double offset = Math.max(maxX - minX, maxX - minX) * 10;
            frameVertex[0] = new Point.Double((maxX + minX) / 2, maxY + offset);
            frameVertex[1] = new Point.Double(minX - offset, minY - offset);
            frameVertex[2] = new Point.Double(maxX + offset, minY - offset);

            QuadEdge a = makeEdge(frameVertex[0], frameVertex[1]);
            QuadEdge b = makeEdge(frameVertex[1], frameVertex[2]);
            QuadEdge.splice(a.sym(), b);
            QuadEdge c = makeEdge(frameVertex[2], frameVertex[0]);
            QuadEdge.splice(b.sym(), c);
            QuadEdge.splice(c.sym(), a);

            startingEdge = a;
        }

        QuadEdge makeEdge(Point.Double o, Point.Double d) {
            QuadEdge q = QuadEdge.makeEdge(o, d);
            quadEdges.add(q);
            return q;
        }

        QuadEdge connect(QuadEdge a, QuadEdge b) {
            QuadEdge q = QuadEdge.connect(a, b);
            quadEdges.add(q);
            return q;
        }

        void delete(QuadEdge e) {
            QuadEdge.splice(e, e.oPrev());
            QuadEdge.splice(e.sym(), e.sym().oPrev());

            QuadEdge eSym = e.sym();
            QuadEdge eRot = e.rot();
            QuadEdge eRotSym = e.rot().sym();

            // this is inefficient on an ArrayList, but this method should be called infrequently
            quadEdges.remove(e);
            quadEdges.remove(eSym);
            quadEdges.remove(eRot);
            quadEdges.remove(eRotSym);

            e.delete();
            eSym.delete();
            eRot.delete();
            eRotSym.delete();
        }

        // The edge returned has the property that either v is on e, or e is an edge of a triangle containing v.
        QuadEdge locate(Point.Double v) {
            if (lastEdge == null || !lastEdge.isNotDeleted()) {
                lastEdge = quadEdges.iterator().next();
            }
            QuadEdge e = lastEdge;
            for (int iter = 0;; iter++) {
                if (iter > quadEdges.size()) {
                    throw new RuntimeException("Possible topology error");
                }
                if (v.equals(e.orig()) || v.equals(e.dest())) {
                    break;
                } else if (rightOf(v, e)) {
                    e = e.sym();
                } else if (!rightOf(v, e.oNext())) {
                    e = e.oNext();
                } else if (!rightOf(v, e.dPrev())) {
                    e = e.dPrev();
                } else {
                    // on edge or in triangle containing edge
//        System.out.println("Locate count: " + iter);
                    break;
                }
            }
            return e;
        }

        // Tests whether a QuadEdge is an edge incident on a frame triangle vertex
        boolean isFrameEdge(QuadEdge e) {
            return isFrameVertex(e.orig()) || isFrameVertex(e.dest());
        }

        boolean isFrameVertex(Point.Double v) {
            return v.equals(frameVertex[0]) || v.equals(frameVertex[1]) || v.equals(frameVertex[2]);
        }

        static double distancePointLine(Point.Double p, Point.Double A, Point.Double B) {
            if (A.x == B.x && A.y == B.y) {
                return p.distance(A);
            }
            double r = ((p.x - A.x) * (B.x - A.x) + (p.y - A.y) * (B.y - A.y)) / ((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));
            if (r <= 0.0) {
                return p.distance(A);
            }
            if (r >= 1.0) {
                return p.distance(B);
            }
            double s = ((A.y - p.y) * (B.x - A.x) - (A.x - p.x) * (B.y - A.y)) / ((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y));
            return Math.abs(s) * Math.sqrt(((B.x - A.x) * (B.x - A.x) + (B.y - A.y) * (B.y - A.y)));
        }

        static boolean isOnEdge(QuadEdge e, Point.Double p) {
            double dist = distancePointLine(p, e.orig(), e.dest());
            double EDGE_COINCIDENCE_TOL_FACTOR = 1000;
            return dist < tolerance / EDGE_COINCIDENCE_TOL_FACTOR;
        }

        boolean isEndPointOfEdge(QuadEdge e, Point.Double v) {
            return equals(v, e.orig(), tolerance) || equals(v, e.dest(), tolerance);
        }

        // A TriangleVisitor which computes and sets the circumCenter as the origin of the dual edges originating in each triangle
        static class TriangleCircumcentreVisitor implements TriangleVisitor {

            public void visit(QuadEdge[] triEdges) {
                Point.Double cc = circumCenter(triEdges[0].orig(), triEdges[1].orig(), triEdges[2].orig());
                // save the circumCenter as the origin for the dual edges originating in this triangle
                triEdges[0].rot().setOrig(cc);
                triEdges[1].rot().setOrig(cc);
                triEdges[2].rot().setOrig(cc);
            }

            static Point.Double circumCenter(Point.Double a, Point.Double b, Point.Double c) {
                double Bx = b.x - a.x;
                double By = b.y - a.y;
                double Cx = c.x - a.x;
                double Cy = c.y - a.y;
                double d = 2 * (Bx * Cy - By * Cx);
                double z1 = Bx * Bx + By * By;
                double z2 = Cx * Cx + Cy * Cy;
                double cx = Cy * z1 - By * z2;
                double cy = Bx * z2 - Cx * z1;
                return new Point.Double(cx / d + a.x, cy / d + a.y);
            }
        }

        // Stores the edges for a visited triangle. Also pushes sym (neighbour) edges on stack to visit later.
        QuadEdge[] fetchTriangleToVisit(QuadEdge edge, Queue<QuadEdge> edgeStack, boolean includeFrame, Set<QuadEdge> visitedEdges) {
            QuadEdge[] triEdges = new QuadEdge[3];

            QuadEdge curr = edge;
            int edgeCount = 0;
            boolean isFrame = false;
            do {
                triEdges[edgeCount] = curr;

                if (isFrameEdge(curr)) {
                    isFrame = true;
                }

                // push sym edges to visit next
                QuadEdge sym = curr.sym();
                if (!visitedEdges.contains(sym)) {
                    edgeStack.add(sym);
                }

                // mark this edge as visited
                visitedEdges.add(curr);

                edgeCount++;
                curr = curr.lNext();
            } while (curr != edge);

            return isFrame && !includeFrame ? null : triEdges;
        }

        void visitTriangles(TriangleVisitor triVisitor, boolean includeFrame) {
            Queue<QuadEdge> q = new ArrayDeque<QuadEdge>();
            q.add(startingEdge);
            Set<QuadEdge> visitedEdges = Collections.newSetFromMap(new IdentityHashMap<QuadEdge, Boolean>());

            while (!q.isEmpty()) {
                QuadEdge edge = q.remove();
                if (!visitedEdges.contains(edge)) {
                    QuadEdge[] triEdges = fetchTriangleToVisit(edge, q, includeFrame, visitedEdges);
                    if (triEdges != null) {
                        triVisitor.visit(triEdges);
                    }
                }
            }
        }

        /**
         * Gets a collection of {@link QuadEdge}s whose origin vertices are a
         * unique set which includes all vertices in the subdivision. The frame
         * vertices can be included if required.
         * <p/>
         * This is useful for algorithms which require traversing the
         * subdivision starting at all vertices. Returning a quadedge for each
         * vertex is more efficient than the alternative of finding the actual
         * vertices quadedges attached to them.
         *
         * @param includeFrame true if the frame vertices should be included
         * @return a collection of QuadEdge with the vertices of the subdivision
         * as their origins
         */
        List<QuadEdge> getVertexUniqueEdges(boolean includeFrame) {
            List<QuadEdge> edges = new ArrayList<>();
            Set<Point.Double> visitedVertices = new HashSet<>();
            quadEdges.stream().map((qe) -> {
                Point.Double v = qe.orig();
                if (!visitedVertices.contains(v)) {
                    visitedVertices.add(v);
                    if (includeFrame || !isFrameVertex(v)) {
                        edges.add(qe);
                    }
                }
                /**
                 * Inspect the sym edge as well, since it is possible that a
                 * vertex is only at the dest of all tracked quadedges.
                 */
                return qe;
            }).map((qe) -> qe.sym()).forEachOrdered((qd) -> {
                Point.Double vd = qd.orig();
                if (!visitedVertices.contains(vd)) {
                    visitedVertices.add(vd);
                    if (includeFrame || !isFrameVertex(vd)) {
                        edges.add(qd);
                    }
                }
            });
            return edges;
        }

        // Gets the coordinates for each triangle in the subdivision as an array
        List<Point.Double[]> getTriangleCoordinates() {
            TriangleCoordinatesVisitor visitor = new TriangleCoordinatesVisitor();
            visitTriangles(visitor, false);
            return visitor.getTriangles();
        }

        static class TriangleCoordinatesVisitor implements TriangleVisitor {

            private List<Point.Double[]> triCoords = new ArrayList<>();

            public void visit(QuadEdge[] triEdges) {
                Point.Double[] coords = new Point.Double[4];
                for (int i = 0; i < 3; i++) {
                    coords[i] = triEdges[i].orig();
                }
                coords[3] = coords[0];
                triCoords.add(coords);
            }

            List<Point.Double[]> getTriangles() {
                return triCoords;
            }
        }

        /**
         * Gets a List of {@link Polygon}s for the Voronoi cells of this
         * triangulation.
         * <p/>
         * The userData of each polygon is set to be the {@link Point.Double)
         * of the cell site.  This allows easily associating external
         * data associated with the sites to the cells.
         *
         * @param geomFact a geometry factory
         * @return a List of Polygons
         */
        List<Point.Double[]> getVoronoiCellPolygons() {
            /*
               * Compute circumcentres of triangles as vertices for dual edges.
               * Precomputing the circumcentres is more efficient,
               * and more importantly ensures that the computed centres
               * are consistent across the Voronoi cells.
             */
            visitTriangles(new TriangleCircumcentreVisitor(), true);

            List<Point.Double[]> cells = new ArrayList<Point.Double[]>();
            for (QuadEdge qe : getVertexUniqueEdges(false)) {
                cells.add(getVoronoiCellPolygon(qe));
            }
            return cells;
        }

        /**
         * Gets the Voronoi cell around a site specified by the origin of a
         * QuadEdge.
         * <p/>
         * The userData of the polygon is set to be the {@link Point.Double)
         * of the site.  This allows attaching external
         * data associated with the site to this cell polygon.
         *
         * @param qe       a quadedge originating at the cell site
         * @param geomFact a factory for building the polygon
         * @return a polygon indicating the cell extent
         */
        Point.Double[] getVoronoiCellPolygon(QuadEdge qe) {
            List<Point.Double> coordList = new ArrayList<Point.Double>();
            QuadEdge startQE = qe;
            do {
                Point.Double cc = qe.rot().orig();
                coordList.add(cc);

                // move to next triangle CW around vertex
                qe = qe.oPrev();
            } while (qe != startQE);

            coordList.add(coordList.get(0));

            if (coordList.size() < 4) {
                System.out.println(coordList);
                coordList.add(coordList.get(coordList.size() - 1));
            }

            Point.Double v = startQE.orig();
            Point.Double[] pts = coordList.toArray(new Point.Double[0]);
            return pts;
        }

        interface TriangleVisitor {

            void visit(QuadEdge[] triEdges);
        }

        /**
         * Inserts a new point into a subdivision representing a Delaunay
         * triangulation, and fixes the affected edges so that the result is
         * still a Delaunay triangulation
         *
         * @return a quadedge containing the inserted vertex
         */
        QuadEdge insertSite(Point.Double v) {
            /**
             * This code is based on Guibas and Stolfi (1985), with minor
             * modifications and a bug fix from Dani Lischinski (Graphic Gems
             * 1993). (The modification I believe is the test for the inserted
             * site falling exactly on an existing edge. Without this test
             * zero-width triangles have been observed to be created)
             */
            QuadEdge e = locate(v);

            if (isEndPointOfEdge(e, v)) {
                // point is already in subdivision.
                return e;
            } else if (isOnEdge(e, v)) {
                // the point lies exactly on an edge, so delete the edge
                // (it will be replaced by a pair of edges which have the point as a vertex)
                e = e.oPrev();
                delete(e.oNext());
            }

            // Connect the new point to the vertices of the containing triangle (or quadrilateral, if the new point fell on an existing edge.)
            QuadEdge base = makeEdge(e.orig(), v);
            QuadEdge.splice(base, e);
            QuadEdge startEdge = base;
            do {
                base = connect(e, base.sym());
                e = base.oPrev();
            } while (e.lNext() != startEdge);

            // Examine suspect edges to ensure that the Delaunay condition is satisfied.
            do {
                QuadEdge t = e.oPrev();
                if (rightOf(t.dest(), e) && isInCircle(e.orig(), t.dest(), e.dest(), v)) {
                    QuadEdge.swap(e);
                    e = e.oPrev();
                } else if (e.oNext() == startEdge) {
                    return base; // no more suspect edges.
                } else {
                    e = e.oNext().lPrev();
                }
            } while (true);
        }

        static boolean equals(Point.Double p1, Point.Double p2, double tolerance) {
            return p1.distance(p2) < tolerance;
        }

        static boolean isInCircle(Point.Double a, Point.Double b, Point.Double c, Point.Double p) {
            double adx = a.x - p.x;
            double ady = a.y - p.y;
            double bdx = b.x - p.x;
            double bdy = b.y - p.y;
            double cdx = c.x - p.x;
            double cdy = c.y - p.y;

            double abdet = adx * bdy - bdx * ady;
            double bcdet = bdx * cdy - cdx * bdy;
            double cadet = cdx * ady - adx * cdy;
            double alift = adx * adx + ady * ady;
            double blift = bdx * bdx + bdy * bdy;
            double clift = cdx * cdx + cdy * cdy;

            double disc = alift * bcdet + blift * cadet + clift * abdet;
            return disc > 0;
        }

        static boolean isCCW(Point.Double a, Point.Double b, Point.Double c) {
            return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x) > 0;
        }

        static boolean rightOf(Point.Double p, QuadEdge e) {
            return isCCW(p, e.dest(), e.orig());
        }
    }

    // Guibas and Stolfi,"Primitives for the manipulation of general subdivisions and the computation of Voronoi diagrams"
    static class QuadEdge {

        QuadEdge next; // next CCW edge
        QuadEdge rot; // the dual of this edge, directed from right to left
        Point.Double vertex; // The vertex that this edge represents

        static QuadEdge makeEdge(Point.Double o, Point.Double d) {
            QuadEdge q0 = new QuadEdge();
            QuadEdge q1 = new QuadEdge();
            QuadEdge q2 = new QuadEdge();
            QuadEdge q3 = new QuadEdge();

            q0.rot = q1;
            q1.rot = q2;
            q2.rot = q3;
            q3.rot = q0;

            q0.setOnext(q0);
            q1.setOnext(q3);
            q2.setOnext(q2);
            q3.setOnext(q1);

            q0.setOrig(o);
            q0.setDest(d);
            return q0;
        }

        /**
         * Creates a new QuadEdge connecting the destination of a to the origin
         * of b, in such a way that all three have the same left face after the
         * connection is complete.
         */
        static QuadEdge connect(QuadEdge a, QuadEdge b) {
            QuadEdge e = makeEdge(a.dest(), b.orig());
            splice(e, a.lNext());
            splice(e.sym(), b);
            return e;
        }

        /**
         * Splices two edges together or apart. Splice affects the two edge
         * rings around the origins of a and b, and, independently, the two edge
         * rings around the left faces of a and b. In each case, (i) if the two
         * rings are distinct, Splice will combine them into one, or (ii) if the
         * two are the same ring, Splice will break it into two separate pieces.
         * Thus, Splice can be used both to attach the two edges together, and
         * to break them apart.
         */
        static void splice(QuadEdge a, QuadEdge b) {
            QuadEdge alpha = a.oNext().rot();
            QuadEdge beta = b.oNext().rot();

            QuadEdge t1 = b.oNext();
            QuadEdge t2 = a.oNext();
            QuadEdge t3 = beta.oNext();
            QuadEdge t4 = alpha.oNext();

            a.setOnext(t1);
            b.setOnext(t2);
            alpha.setOnext(t3);
            beta.setOnext(t4);
        }

        // Turns an edge counterclockwise inside its enclosing quadrilateral
        static void swap(QuadEdge e) {
            QuadEdge a = e.oPrev();
            QuadEdge b = e.sym().oPrev();
            splice(e, a);
            splice(e.sym(), b);
            splice(e, a.lNext());
            splice(e.sym(), b.lNext());
            e.setOrig(a.dest());
            e.setDest(b.dest());
        }

        void delete() {
            rot = null;
        }

        boolean isNotDeleted() {
            return rot != null;
        }

        void setOnext(QuadEdge next) {
            this.next = next;
        }

        QuadEdge rot() {
            return rot;
        }

        QuadEdge sym() {
            return rot.rot;
        }

        QuadEdge invRot() {
            return rot.rot.rot;
        }

        QuadEdge oNext() {
            return next;
        }

        QuadEdge oPrev() {
            return rot.next.rot;
        }

        QuadEdge dNext() {
            return sym().next.sym();
        }

        QuadEdge dPrev() {
            return invRot().next.invRot();
        }

        QuadEdge lNext() {
            return invRot().next.rot;
        }

        QuadEdge lPrev() {
            return next.sym();
        }

        QuadEdge rNext() {
            return rot.next.invRot();
        }

        QuadEdge rPrev() {
            return sym().next;
        }

        void setOrig(Point.Double o) {
            vertex = o;
        }

        void setDest(Point.Double d) {
            sym().setOrig(d);
        }

        Point.Double orig() {
            return vertex;
        }

        Point.Double dest() {
            return sym().orig();
        }
    }

    // visualization
    Random rnd = new Random(1);
    JPanel panel;
    List<Point.Double> points = new ArrayList<Point.Double>();
    List<Point2D.Double[]> tr = Collections.emptyList();
    List<Point2D.Double[]> voronoiCellPolygons = Collections.emptyList();

    public Interface_Voronoi() throws HeadlessException {
        int n = 300;
        for (int i = 0; i < n; i++) {
            int x = rnd.nextInt(950) + 1;
            int y = rnd.nextInt(700) + 1;
            Point.Double c = new Point.Double(x, y);
            points.add(c);
        }

        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = ((Graphics2D) g);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setStroke(new BasicStroke(1));
                g.setColor(Color.BLUE);
                tr.stream().map((o) -> (Point.Double[]) o).forEachOrdered((a) -> {
                    for (int i = 0; i + 1 < a.length; i++) {
                        g.drawLine((int) a[i].x, (int) a[i].y, (int) a[i + 1].x, (int) a[i + 1].y);
                    }
                });
                g2.setStroke(new BasicStroke(3));
                g.setColor(Color.BLACK);
                voronoiCellPolygons.stream().map((o) -> (Point.Double[]) o).forEachOrdered((a) -> {
                    for (int i = 0; i + 1 < a.length; i++) {
                        g.drawLine((int) a[i].x, (int) a[i].y, (int) a[i + 1].x, (int) a[i + 1].y);
                    }
                });
                g.setColor(Color.RED);
                for (int i = 0; i < points.size(); i++) {
                    g.drawOval((int) points.get(i).x - 2, (int) points.get(i).y - 2, 5, 5);
                }
            }
        };
        setContentPane(panel);
    }

    public static void main(String[] args) {
        System.out.println("entrou");
        final Interface_Voronoi dv = new Interface_Voronoi();
        dv.setSize(new Dimension(1024, 768));
        dv.setLocationRelativeTo(null);
        dv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dv.setVisible(true);
        new Thread() {
            public void run() {
                while (true) {
                    final Point2D.Double pivot = dv.points.get(0);
                    pivot.x += dv.rnd.nextInt(4);
                    pivot.y += dv.rnd.nextInt(2);
                    pivot.x %= 950;
                    pivot.y %= 700;

                    Set<Point.Double> uniquePoints = new HashSet<>(dv.points);
                    SubDivision subdivision = new SubDivision(uniquePoints);
                    uniquePoints.forEach((p) -> {
                        subdivision.insertSite(p);
                    });
                    dv.tr = subdivision.getTriangleCoordinates();
                    dv.voronoiCellPolygons = subdivision.getVoronoiCellPolygons();

                    dv.panel.repaint();
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
}
