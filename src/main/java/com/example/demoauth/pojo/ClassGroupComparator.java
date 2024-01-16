package com.example.demoauth.pojo;

import java.util.Comparator;

public class ClassGroupComparator implements Comparator<StudentsWithGroup> {

    @Override
    public Comparator<StudentsWithGroup> thenComparing(Comparator<? super StudentsWithGroup> other) {
        return Comparator.super.thenComparing(other);
    }

    @Override
    public int compare(StudentsWithGroup o1, StudentsWithGroup o2) {
        return o1.getNumberOfGroup() - o2.getNumberOfGroup();
    }
}
