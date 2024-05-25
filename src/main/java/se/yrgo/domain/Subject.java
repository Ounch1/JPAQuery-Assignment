package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Subject {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    private String subjectName;
    private int numberOfSemesters;

    @ManyToMany
    private Set<Tutor> tutors;


    public Subject() {}

    public Subject(String subjectName, int numberOfSemesters) {
        this.subjectName = subjectName;
        this.numberOfSemesters=numberOfSemesters;

        this.tutors = new HashSet<Tutor>();

    }

    public String subjectName() {
        return subjectName;
    }

    public void addTutorToSubject(Tutor tutor) {
        this.tutors.add(tutor);
    }

    public Set<Tutor> getTutors() {
        return this.tutors;
    }
}

