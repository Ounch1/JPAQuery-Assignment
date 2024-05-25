package se.yrgo.domain;



import jakarta.persistence.*;

import java.util.Objects;


@Entity

//@Table(name="TBL_STUDENT")
public class Student
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;

    @Column(unique=true, nullable=false)
    private String enrollmentID;
    private String name;

    @Embedded
    private Address address;



   @Column(name="NUM_COURSES")
    private Integer numberOfCourses;


    public Student() {}

    //public Student(String name, Tutor tutor)
    //{
      //  this.name = name;
        //this.tutor = tutor;
    //}


    public Student(String name)
    {
        this.name = name;
        this.numberOfCourses = 10;
    }

    public Student(String name, String enrollmentID, String street, String city,
                   String zipCode){
        this.name = name;
        this.enrollmentID= enrollmentID;
        this.address = new Address(street,city,zipCode);
    }

  //  public void allocateTutor(Tutor tutor) {
    //    this.tutor=tutor;
    //}

    //public String getTutorName() {
      //  return this.tutor.getName();
    //}

    public String toString() {
        return name + " lives at: " + this.address;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public void setEnrollmentID(String enrollmentID) {
        this.enrollmentID = enrollmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //public Tutor getTutor() {
      //  return tutor;
    //}

    public Integer getNumberOfCourses() {
        return numberOfCourses;
    }

    public void setNumberOfCourses(Integer numberOfCourses) {
        this.numberOfCourses = numberOfCourses;
    }

    @Transient
    public int getAge(int age) {
        return age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address newAddress) {
        this.address = newAddress;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(enrollmentID, student.enrollmentID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(enrollmentID);
    }
}
