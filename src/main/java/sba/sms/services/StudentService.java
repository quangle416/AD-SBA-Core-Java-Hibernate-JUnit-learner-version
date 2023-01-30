package sba.sms.services;

import java.util.LinkedList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;
import org.hibernate.query.Query;

import jakarta.persistence.NoResultException;
import sba.sms.dao.StudentI;
import sba.sms.models.Course;
import sba.sms.models.Student;
import sba.sms.utils.HibernateUtil;

public class StudentService implements StudentI {

	@Override
	public List<Student> getAllStudents() {
		
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Student> sList = new LinkedList<>();
		
		try {
			
			tx = session.beginTransaction();
			
			sList = session.createNamedQuery("getAllS", Student.class)
					.getResultList();
			
			tx.commit();
			
		}catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		
		}finally {
			session.close();
		}
		return sList;
	}

	@Override
	public void createStudent(Student student) {
	
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			tx = session.beginTransaction();
			
			session.merge(student);
			
			tx.commit();
			
		} catch(HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
			
		} finally {
			session.close();
		}
		
	}

	@Override
	public Student getStudentByEmail(String email) {
	
		Transaction tx = null;
		Student sBE = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			tx = session.beginTransaction();
			sBE = session.createNamedQuery("getByS", Student.class)
							.setParameter("email", email)
							.getSingleResult();
			
			tx.commit();
			
		
		} catch (HibernateException ex) {
			ex.printStackTrace();
		
			tx.rollback();
			
		} finally {
			session.close();
		}
		return sBE;
	}

	@Override
	public boolean validateStudent(String email, String password) {
		
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		Student sVS = null;
		boolean result = false;
		
		try {
			tx = session.beginTransaction();
			
			sVS = session.get(Student.class, email);
					
			if (sVS.getPassword().equals(password)) {
				result = true;
				
			}else result = false;
		
			tx.commit();
			
		}catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
			
		}catch (NullPointerException npe) {
			System.out.println("Wrong Credentials");
			tx.rollback();
//		}catch (StringIndexOutOfBoundsException sioobe) {
//			System.out.println("Please Enter Valid Email");
//			tx.rollback();
		}
				
		finally {
			session.close();
		}
		
		return result;
	}

	@Override
	public void registerStudentToCourse(String email, int courseId) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		try {
			Student s = session.get(Student.class, email);
			Course c = session.get(Course.class, courseId);
			
			
			tx = session.beginTransaction();
			List<Course> rSTC = s.getCourses();
			
			if (!rSTC.contains(c)) {
				s.addCourse(c);
				session.merge(s);
			
			}
			tx.commit();
			
		
		}catch
		(HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		
		}finally {
			session.close();}
		
	}

	@Override
	public List<Course> getStudentCourses(String email) {
		Transaction tx = null;
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Course> stCourses = new LinkedList<>();
		
		try {
			tx = session.beginTransaction();
			
			String q = "Select c.id, c.name, c.instructor From course c Join student_courses sc On c.id = sc.course_id Join student s On s.email = sc.student_email Where s.email = :email";
			stCourses = session.createNativeQuery(q, Course.class)
						.setParameter("email", email)
						.getResultList();

			tx.commit();
			
			
		}catch (HibernateException ex) {
			ex.printStackTrace();
			tx.rollback();
		
		}finally {
			session.close();
		}
		
	 return stCourses;
		
}
}