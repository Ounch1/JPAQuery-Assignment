package se.yrgo.test;

import jakarta.persistence.*;

import se.yrgo.domain.Student;
import se.yrgo.domain.Subject;
import se.yrgo.domain.Tutor;

import java.util.List;
import java.util.Set;

public class HibernateTest
{
	public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");

	public static void main(String[] args){
		setUpData();

		System.out.printf("%n%n|||| QUERY ONE ||||%n");
		System.out.printf("Every student that is under a tutor that teaches science among other subjects: %n");
		QueryOne();
		System.out.printf("%n%n|||| QUERY TWO ||||%n");
		System.out.printf("Tutors and students under them: %n");
		QueryTwo();
		System.out.printf("%n%n|||| QUERY THREE ||||%n");
		System.out.printf("Average length of a semester: %n");
		QueryThree();
		System.out.printf("%n%n|||| QUERY FOUR ||||%n");
		System.out.printf("Max salary of a teacher: %n");
		QueryFour();
		System.out.printf("%n%n|||| QUERY FIVE ||||%n");
		System.out.printf("Teachers with a salary above 10k: %n");
		QueryFive();
	}

	static void QueryFive()
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		List<Tutor> tutors = em.createNamedQuery("findTutorsAboveSalary", Tutor.class)
				.setParameter("threshold", 10000)
				.getResultList();

		for (Tutor tutor : tutors)
		{
			System.out.println("Name: " + tutor.getName() + " | Salary: " + tutor.getSalary());
		}

		tx.commit();
	}
	static void QueryFour()
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Integer maxSalary = em.createQuery("select max(t.salary) from Tutor t", Integer.class).getSingleResult();
		System.out.printf(maxSalary.toString());

		tx.commit();
	}
	static void QueryThree()
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		Double avgSemesterLength = em.createQuery("select avg(s.numberOfSemesters) from Subject s", Double.class).getSingleResult();
		System.out.printf(avgSemesterLength.toString());

		tx.commit();
	}
	static void QueryTwo()
	{

		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();

		List<Object[]> reportQueryResults = em.createQuery("select t.name, tg.name from Tutor t join t.teachingGroup tg", Object[].class).getResultList();
		for (Object[] result : reportQueryResults) {
			System.out.printf("Tutor: %s | Student: %s%n", result[0], result[1]);
		}


		tx.commit();
	}
	static void QueryOne()
	{
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();


		TypedQuery<Subject> subjectQuery = em.createQuery("select s from Subject s where lower(s.subjectName) = :subjectName", Subject.class);
		subjectQuery.setParameter("subjectName", "science");
		Subject subject = subjectQuery.getSingleResult();

		var studentQuery = em.createQuery("select tg from Tutor t join t.teachingGroup tg where :subjectObject member of t.subjectsToTeach", Student.class);
		studentQuery.setParameter("subjectObject", subject);

		List<Student> students = studentQuery.getResultList();

		for (Student student : students) {
			System.out.println(student);
		}

		tx.commit();
	}

	public static void setUpData(){
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();


		Subject mathematics = new Subject("Mathematics", 2);
		Subject science = new Subject("Science", 2);
		Subject programming = new Subject("Programming", 3);
		em.persist(mathematics);
		em.persist(science);
		em.persist(programming);

		Tutor t1 = new Tutor("ABC123", "Johan Smith", 40000);
		t1.addSubjectsToTeach(mathematics);
		t1.addSubjectsToTeach(science);


		Tutor t2 = new Tutor("DEF456", "Sara Svensson", 20000);
		t2.addSubjectsToTeach(mathematics);
		t2.addSubjectsToTeach(science);

		// This tutor is the only tutor who can teach History
		Tutor t3 = new Tutor("GHI678", "Karin Lindberg", 0);
		t3.addSubjectsToTeach(programming);

		em.persist(t1);
		em.persist(t2);
		em.persist(t3);

		t1.createStudentAndAddtoTeachingGroup("Jimi Hendriks", "1-HEN-2019", "Street 1", "city 1", "1212");
		t1.createStudentAndAddtoTeachingGroup("Bruce Lee", "2-LEE-2019", "Street 2", "city 2", "2323");
		t3.createStudentAndAddtoTeachingGroup("Roger Waters", "3-WAT-2018", "Street 3", "city 3", "34343");

		tx.commit();
		em.close();
	}

}
