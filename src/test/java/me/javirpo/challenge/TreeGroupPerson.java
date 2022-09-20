package me.javirpo.challenge;

import java.util.*;

public class TreeGroupPerson {
    static class Person implements  Comparable<Person> {
        final int id;

        final String name;

        Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public int compareTo(Person o) {
            int nameCompare = this.name.compareTo(o.name);
            if(nameCompare != 0) {
                return nameCompare;
            }
            int idCompare = this.id - o.id;
            if(idCompare != 0) {
                return idCompare;
            }

            return 0;
        }

        @Override
        public String toString() {
            return id + " - " + name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return id == person.id && Objects.equals(name, person.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }
    }

    private static Person[] RAW_DATA = new Person[]{
            new Person(0, "Harry"),
            new Person(0, "Harry"),
            new Person(1, "Harry"),
            new Person(2, "Harry"),
            new Person(3, "Emily"),
            new Person(4, "Jack"),
            new Person(4, "Jack"),
            new Person(5, "Amelia"),
            new Person(5, "Amelia"),
            new Person(6, "Amelia"),
            new Person(7, "Amelia"),
            new Person(8, "Amelia"),
    };

    /*
        0 - Harry
        0 - Harry
        1 - Harry
        2 - Harry
        3 - Emily
        4 - Jack
        4 - Jack
        5 - Amelia
        5 - Amelia
        6 - Amelia
        7 - Amelia
        8 - Amelia

Please write code which will return unique sorted structure by name and id with number of record in group with the same name
        **************************************************

        5 - Amelia (1)
        6 - Amelia (2)
        7 - Amelia (3)
        8 - Amelia (4)
        3 - Emily (1)
        0 - Harry (1)
        1 - Harry (2)
        2 - Harry (3)
        4 - Jack (1)
     */
    public static void main(String[] args) {
        Set<Person> set = new TreeSet<>();
        for (Person person : RAW_DATA) {
            System.out.println(person.id + " - " + person.name);
            set.add(person);
        }

        System.out.println();
        System.out.println("**************************************************");
        System.out.println();

        // todo
        String previousName = "";
        int nameCounter = 1;
        for(Person p : set) {
            if (!previousName.equals(p.name)) {
                nameCounter = 1;
                previousName = p.name;
            }
            System.out.println(p + " ("+nameCounter+")");
            nameCounter++;
        }
        //System.out.println(set);
    }
}