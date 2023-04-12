package me.javirpo.challenge;

/*
Write a function that will find “n” available seats on an airplane.
Design your function so that it accepts as input parameters:
  * the seating layout and current status for the plane
  * the number of seats to find

The function should return a list of the suggested seats by name so they can be
printed on a customer ticket. I.e. "3B, 3C, 3D" where rows are indicated by the
first number, and the seat letter within the row follows.

The seats should be selected using the following priority-ranked preferences where possible:

  (1) All seats for the party are directly next to each other (in the same row, not split by an aisle)
  (2) All seats for the party are next to each other but split by an aisle

All else being equal, the selected seating row is the nearest option to the front of the airplane.

If no solution exists that matches one of these cases, the function should return a result that clearly indicates that there is no solution.

HINTS:
  * Try to define a function that iterates through the seats in the plane only once
  * Different planes can have different seating layouts
  * Different rows in the plane can have different seating layouts
  * Each seat will have a "name" that your algorithm shouldn't try to predict.
      E.g. An exit row may have seats named "7A, 7B, 7E, 7F"
      You can design your input so it provides these details.
  * The layout of a plane's seats and aisles can be thought of as a 2D grid
*/


// seatStatus
// OXOAOOO
// OX
// A
// 0XXAXX0 no
// XX0A0XX yes
// XX0A000 yes



/*
 * To execute Java, please define "static void main" on a class
 * named Solution.
 *
 * If you need more classes, simply define them inline.
 */

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

enum SeatType {
    SEAT, AISLE
}

class Seat {
    String name;
    boolean status;
    SeatType type;
}

class AvailableSeats {
    public static void main(String[] args) {

    }

    private static List<Seat> func(List<List<Seat>> seats, int seatsToFind) {
        for(List<Seat> seatsRow : seats) {
            if (seatsRow.size() < seatsToFind) {
                continue;
            }

            List<Seat> solution1 = new ArrayList<>();
            List<Seat> solution2 = new ArrayList<>();
            for(Seat seat : seatsRow) {
                if (seat.type == SeatType.AISLE) {
                    if (solution1.size() != seatsToFind) {
                        solution1.clear();
                    }
                    continue;
                }
                if (!seat.status) { // not available
                    if (solution1.size() != seatsToFind) {
                        solution1.clear();
                    }
                    if (solution2.size() != seatsToFind) {
                        solution2.clear();
                    }

                    continue;
                }

                if (solution1.size() != seatsToFind) {
                    solution1.add(seat);
                }
                if (solution2.size() != seatsToFind) {
                    solution2.add(seat);
                }

                // Stop condition
                if (solution1.size() == seatsToFind && solution2.size() == seatsToFind) {
                    break;
                }
            }

            // need to check if find solution
            if (solution1.size() == seatsToFind) {
                return solution1;
            }
            if (solution2.size() == seatsToFind) {
                return solution2;
            }
        }

        return Collections.emptyList();
    }
}
