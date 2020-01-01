package me.javirpo.puzzle.solver.helper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Line {
    private boolean horizontal;
    private Point from;
    private Point to;
}
