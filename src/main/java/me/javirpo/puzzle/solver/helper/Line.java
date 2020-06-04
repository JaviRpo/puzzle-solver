package me.javirpo.puzzle.solver.helper;

import java.util.Iterator;

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
}
