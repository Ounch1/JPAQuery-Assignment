package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Tutor {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    private String tutorId;
    private String name;
    private int salary;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="TUTOR_FK")
    private Set<Student> teachingGroup;

    @ManyToMany(mappedBy="tutors")
    private Set<Subject>subjectsToTeach;

    public Tutor() {}

    public Tutor(String tutorId, String name, int salary) {
        this.tutorId=tutorId;
        this.name= name;
        this.salary= salary;
        this.teachingGroup = new HashSet<Student>();
        this.subjectsToTeach = new HashSet<Subject>();
    }

    public String toString(){
        return "Name: " + name + "TutorId: " + tutorId ;
    }

    public String getTutorId() {
        return tutorId;
    }

    public String getName() {
        return name;
    }

    public int getSalary() {
        return salary;
    }

    public void addStudentToTeachingGroup(Student newStudent) {
        this.teachingGroup.add(newStudent);
    }

    public Set<Student> getTeachingGroup() {
        Set<Student>unmodifiable=
                Collections.unmodifiableSet(this.teachingGroup);
        return unmodifiable;
    }

    public void addSubjectsToTeach(Subject subject) {
        this.subjectsToTeach.add(subject);
        subject.getTutors().add(this);
    }

    public void createStudentAndAddtoTeachingGroup(String studentName,
                                                   String enrollmentID,String street, String city,
                                                   String zipcode) {
        Student student = new Student(studentName, enrollmentID,
                street,city,zipcode);
        this.addStudentToTeachingGroup(student);
    }
}
