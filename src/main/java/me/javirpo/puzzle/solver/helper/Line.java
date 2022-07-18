package me.javirpo.puzzle.solver.helper;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
public class Line implements Iterable<Point> {
    private boolean horizontal;
    private Point from;
    private Point to;

    public int length() {
        if (horizontal) {
            return to.getCol() - from.getCol();
        }
        return to.getRow() - from.getRow();
    }

    @Override
    public Iterator<Point> iterator() {
        return new Itr();
    }

    private class Itr implements Iterator<Point> {
        private int hold;
        private int current;
        private int last;

        private Itr() {
            if (horizontal) {
                hold = from.getRow();
                current = from.getCol();
                last = to.getCol();
            } else {
                hold = from.getCol();
                current = from.getRow();
                last = to.getRow();
            }
        }

        @Override
        public boolean hasNext() {
            return current <= last;
        }

        @Override
        public Point next() {
            if (horizontal) {
                return new Point(hold, current++);
            }
            return new Point(current++, hold);
        }
    }
    /*
    public Map<Point, Line> intersect(List<Line> lines) {
        Map<Point, Line> map = new HashMap<>();
        List<Line> oppositeLines = lines.stream()
                .filter(l -> l.horizontal != this.horizontal)
                .collect(Collectors.toList());
        this.s
        for(Point p : this) {
            if (this.horizontal && intersectHorizontal(p, lines)) {
                map.put()
            } else {

            }
        }
        if (line.isHorizontal()) {
            for (Map.Entry<Line, String> entryCrossLine : crossCols.entrySet()) {
                Line crossLine = entryCrossLine.getKey();
                if (p.getCol() == crossLine.getFrom().getCol() &&
                        p.getRow() >= crossLine.getFrom().getRow() && p.getRow() <= crossLine.getTo().getRow()) {
                    return entryCrossLine;
                }
            }
        } else {
            for (Map.Entry<Line, String> entryCrossLine : crossRows.entrySet()) {
                Line crossLine = entryCrossLine.getKey();
                if (p.getRow() == crossLine.getFrom().getRow() &&
                        p.getCol() >= crossLine.getFrom().getCol() && p.getCol() <= crossLine.getTo().getCol()) {
                    return entryCrossLine;
                }
            }
        }
    }
    */
}
